package serviceimpl;

import static converters.ConverterMain.fromEvolRequestToEvolInternal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import enums.ParamPrefixes;
import model.internal.EvolutionInternal;
import model.request.EvolutionRequest;

import org.springframework.stereotype.Service;

import service.EvolutionService;
import service.StorageService;
import exceptions.IncorrectRequestException;

@Service
public class EvolutionServiceImpl extends BioUniverseServiceImpl implements EvolutionService {
	private final String prepareNames;
	private final String blastAllVsAll;
	private final String createCogs;

	public EvolutionServiceImpl(final StorageService storageService, final AppProperties properties) {
		super(storageService, properties);
		this.prepareNames = properties.getPrepareNamesProgram();
		this.blastAllVsAll = properties.getBlastAllVsAllProgram();
		this.createCogs = properties.getCreateCogsProgram();
	}

	@Override
	public String createCogs(final EvolutionRequest evolutionRequest) throws IncorrectRequestException {
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
		String[] arrayOfCommands = {prepareNames, blastAllVsAll, createCogs};
		List<List<String>> listOfArgumentLists = new LinkedList<>(Arrays.asList(argsForPrepNames, argsForBlast, argsForCreateCogs));

		List<List<String>> commandsAndArguments = new LinkedList<>();

		for (int i=0; i<arrayOfCommands.length; i++) {
			List<String> listOfCommandsAndArgs= new LinkedList<>();
			listOfCommandsAndArgs.add(arrayOfPrograms[i]);
			listOfCommandsAndArgs.add(arrayOfCommands[i]);
			listOfCommandsAndArgs.addAll(listOfArgumentLists.get(i));
			commandsAndArguments.add(listOfCommandsAndArgs);
		}
		launchProcessAndGetResultFileName(commandsAndArguments);

		return resultFileName;
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

	public EvolutionInternal storeFileAndGetInternalRepresentation(final EvolutionRequest evolutionRequest, String inputFilesLocation1) throws IncorrectRequestException {
		super.getStorageService().storeMultipleFiles(evolutionRequest.getListOfFiles(), inputFilesLocation1);
		return fromEvolRequestToEvolInternal(evolutionRequest);
	}
}
