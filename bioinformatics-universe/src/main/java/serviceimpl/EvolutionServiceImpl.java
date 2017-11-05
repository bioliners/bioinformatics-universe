package serviceimpl;

import static converters.ConverterMain.fromEvolRequestToEvolInternal;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import biojobs.BioJob;
import biojobs.BioJobDao;
import biojobs.BioJobResult;
import biojobs.BioJobResultDao;
import enums.ParamPrefixes;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.EvolutionService;
import service.StorageService;
import exceptions.IncorrectRequestException;
import springconfiguration.AppProperties;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EvolutionServiceImpl extends BioUniverseServiceImpl implements EvolutionService {
	private final String prepareNames;
	private final String blastAllVsAll;

	private final int defaultLastJobId = 1;
	@Autowired
	private final BioJobDao bioJobDao;
	@Autowired
	private final BioJobResultDao bioJobResultDao;



	public EvolutionServiceImpl(final StorageService storageService, final AppProperties properties, final BioJobDao bioJobDao, final BioJobResultDao bioJobResultDao) {
		super(storageService, properties);
		this.prepareNames = properties.getPrepareNamesProgram();
		this.blastAllVsAll = properties.getBlastAllVsAllProgram();
		this.bioJobDao = bioJobDao;
		this.bioJobResultDao = bioJobResultDao;
	}

	@Override
	public BioJob getBioJobIfFinished(int jobId) {
		BioJob bioJob = bioJobDao.findByJobId(jobId);
		return bioJob.isFinished() ? bioJob : null;
	}

	@Override
	@Async
	public CompletableFuture<Integer> createCogs(final EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		String inputFilesLocation1 = super.getProperties().getMultipleWorkingFilesLocation();
		String outputFilesLocation1 = super.getProperties().getMultipleWorkingFilesLocation();
		String inputFilesLocation2 = outputFilesLocation1;
		String outputFilesLocation2 = super.getProperties().getMultipleWorkingFilesLocation();
		String inputFilesLocation3 = outputFilesLocation2;
		super.getStorageService().createMultipleDirs(Arrays.asList(inputFilesLocation1, outputFilesLocation1, outputFilesLocation2));

		String resultFileName = UUID.randomUUID().toString() + super.getPostfix();

		EvolutionInternal evolutionInternal = storeFileAndGetInternalRepresentation(evolutionRequest, inputFilesLocation1);
		evolutionInternal.setFields();

		List<String> argsForPrepNames = new LinkedList<>();
		List<String> argsForBlast = new LinkedList<>();
		List<String> argsForCreateCogs = new LinkedList<>();
		argsForPrepNames.addAll(Arrays.asList(ParamPrefixes.INPUT.getPreifx()+inputFilesLocation1, ParamPrefixes.OUTPUT.getPreifx()+outputFilesLocation1));
		argsForPrepNames.addAll(evolutionInternal.getFieldsInfo());

		argsForBlast.add(ParamPrefixes.WDIR.getPreifx() + super.getPathToMainDirFromBioProgs() + super.getWorkingDir()+"/");
		argsForBlast.addAll(Arrays.asList(ParamPrefixes.INPUT.getPreifx() +inputFilesLocation2, ParamPrefixes.OUTPUT.getPreifx()+outputFilesLocation2));

		argsForCreateCogs.add(ParamPrefixes.INPUT.getPreifx()+inputFilesLocation3);
		argsForCreateCogs.add(ParamPrefixes.OUTPUT.getPreifx() + resultFileName);
		argsForCreateCogs.addAll(evolutionInternal.getAllFields());


		String[] arrayOfPrograms = {super.getBash(), super.getBash(), super.getPython()};
		String[] arrayOfCommands = {prepareNames, blastAllVsAll, super.getProgram(evolutionInternal.getCommandToBeProcessedBy())};
		List<List<String>> listOfArgumentLists = new LinkedList<>(Arrays.asList(argsForPrepNames, argsForBlast, argsForCreateCogs));

		List<List<String>> commandsAndArguments = new LinkedList<>();

		for (int i=0; i<arrayOfCommands.length; i++) {
			List<String> listOfCommandsAndArgs= new LinkedList<>();
			listOfCommandsAndArgs.add(arrayOfPrograms[i]);
			listOfCommandsAndArgs.add(arrayOfCommands[i]);
			listOfCommandsAndArgs.addAll(listOfArgumentLists.get(i));
			commandsAndArguments.add(listOfCommandsAndArgs);
		}

		int jobId = saveBioJobToDB(evolutionInternal, resultFileName);
		launchProcessAndGetResultFileName(commandsAndArguments);
		saveResultFileToDB(resultFileName, jobId);
		return CompletableFuture.completedFuture(jobId);
	}

	public int saveBioJobToDB(EvolutionInternal evolutionInternal, String resultFileName) {
		int jobId = getLastJobId();
		BioJob bioJob = new BioJob();
		bioJob.setProgramNameName(super.getProgram(evolutionInternal.getCommandToBeProcessedBy()));
		bioJob.setJobId(jobId);
		bioJob.setJobDate(LocalDateTime.now());
		bioJob.setFinished(false);

		BioJobResult bioJobResult = new BioJobResult();
		bioJobResult.setResultFile("placeholder");
		bioJobResult.setResultFileName(resultFileName);
        bioJobResult.setBiojob(bioJob);

        bioJob.addToBioJobResultList(bioJobResult);
		bioJobDao.save(bioJob);
		return jobId;
	}

	public void saveResultFileToDB(String resultFileName, int jobId) {
		File file = null;
		try {
			file = getStorageService().loadAsResource(resultFileName).getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuilder fileAsStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				fileAsStringBuilder.append(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Can't find file " + file.toString());
		} catch (IOException e) {
			System.out.println("Unable to read file " + file.toString());
		}

		BioJobResult bioJobResult = bioJobResultDao.findByResultFileName(resultFileName);
		bioJobResult.setResultFile(fileAsStringBuilder.toString());
		bioJobResultDao.save(bioJobResult);

		BioJob bioJob = bioJobDao.findByJobId(jobId);
		bioJob.setFinished(true);
		bioJobDao.save(bioJob);
	}

	public void launchProcessAndGetResultFileName(final List<List<String>> commandsAndArguments) throws IncorrectRequestException {
		Process process = null;
		List<ProcessBuilder> listOfProcessBuilders = new LinkedList<>();

		for (int i=0; i<commandsAndArguments.size(); i++) {
			listOfProcessBuilders.add(new ProcessBuilder(commandsAndArguments.get(i)));
			listOfProcessBuilders.get(i).directory(new File(super.getWorkingDir()));
		}

		try {
			for (ProcessBuilder processBuilder : listOfProcessBuilders) {
				System.out.println("processBuilder.directory() " + processBuilder.directory());
				System.out.println(processBuilder.command());
				process = processBuilder.start();
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = br.readLine()) != null) {
                	System.out.println(line);
                    System.out.println("\n");
				}
                br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Integer getLastJobId() {
		Integer lastJobId = bioJobDao.getLastJobId() + 1;
		return lastJobId != null ? lastJobId : defaultLastJobId;
	}

	public EvolutionInternal storeFileAndGetInternalRepresentation(final EvolutionRequest evolutionRequest, String inputFilesLocation1) throws IncorrectRequestException {
		super.getStorageService().storeMultipleFiles(evolutionRequest.getListOfFiles(), inputFilesLocation1);
		return fromEvolRequestToEvolInternal(evolutionRequest);
	}
}
