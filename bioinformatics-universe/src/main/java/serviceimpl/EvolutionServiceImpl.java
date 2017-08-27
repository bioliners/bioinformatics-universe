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
		super.getStorageService().createMultipleDirs(Arrays.asList(new String[] {inputFilesLocation1, outputFilesLocation1, outputFilesLocation2}));

		EvolutionInternal evolutionInternal = storeFileAndGetInternalRepresentation(evolutionRequest, inputFilesLocation1);
		evolutionInternal.setFields();

		List<String> argsForPrepNames = new LinkedList<>();
		List<String> argsForBlast = new LinkedList<>();
		List<String> argsForCreateCogs = new LinkedList<>();
		argsForPrepNames.addAll(Arrays.asList(new String[] {inputFilesLocation1, outputFilesLocation1}));
		argsForPrepNames.addAll(evolutionInternal.getFieldsInfo());
		argsForBlast.addAll(Arrays.asList(new String[] {inputFilesLocation2, outputFilesLocation2}));
		argsForCreateCogs.add(inputFilesLocation3);
		argsForCreateCogs.addAll(evolutionInternal.getAllFields());


		String[] arrayOfPrograms = {super.getBash(), super.getBash(), super.getPython()};
		String[] arrayOfCommands = {prepareNames, blastAllVsAll, createCogs};
		List[] arrayOfArgumentLists = {argsForPrepNames, argsForBlast, argsForCreateCogs};

		List<String>[] commandsAndArguments = new LinkedList [arrayOfCommands.length];
		for (int i=0; i<=arrayOfCommands.length; i++) {
			commandsAndArguments[i].add(arrayOfPrograms[i]);
			commandsAndArguments[i].add(arrayOfCommands[i]);
			commandsAndArguments[i].addAll(arrayOfArgumentLists[i]);
		}
		return launchProcessAndGetResultFileName(commandsAndArguments);
	}


	public String launchProcessAndGetResultFileName(final List<String>[] commandsAndArguments) throws IncorrectRequestException {
		String resultFileName = super.getPrefix() + UUID.randomUUID().toString() + super.getPostfix();
		File outputFile = new File(super.getWorkingDir() + resultFileName);
		Process process = null;
		List<ProcessBuilder> listOfProcessBuilders = new LinkedList<>();

		for (int i=0; i<=commandsAndArguments.length; i++) {
			listOfProcessBuilders.add(new ProcessBuilder(commandsAndArguments[i]));
			listOfProcessBuilders.get(i).directory(new File(super.getWorkingDir()));
		}

		try {
			for (ProcessBuilder processBuilder : listOfProcessBuilders) {
				process = processBuilder.start();
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;

			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.write("\n");
			}
			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputFile.getName();
	}

	public EvolutionInternal storeFileAndGetInternalRepresentation(final EvolutionRequest evolutionRequest, String inputFilesLocation1) throws IncorrectRequestException {
		super.getStorageService().storeMultipleFiles(evolutionRequest.getListOfFiles(), inputFilesLocation1);
		return fromEvolRequestToEvolInternal(evolutionRequest);
	}
}
