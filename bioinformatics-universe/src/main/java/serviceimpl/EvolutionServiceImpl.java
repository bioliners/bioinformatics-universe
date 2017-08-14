package serviceimpl;

import model.internal.EvolutionInternal;
import org.springframework.stereotype.Service;

import model.request.EvolutionRequest;
import exceptions.IncorrectRequestException;
import service.EvolutionService;
import service.StorageService;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static converters.ConverterMain.fromEvolRequestToEvolInternal;

@Service
public class EvolutionServiceImpl extends BioUniverseServiceImpl implements EvolutionService {
	private final String prepareNames;
	private final String blastAllVsAll;
	private final String createCogs;

	public EvolutionServiceImpl(StorageService storageService, AppProperties properties) {
		super(storageService, properties);
		this.prepareNames = properties.getPrepareNamesProgram();
		this.blastAllVsAll = properties.getBlastAllVsAllProgram();
		this.createCogs = properties.getCreateCogsProgram();

	}

	@Override
	public String createCogs(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		return null;
	}


	public String launchProcessAndGetResultFileName(EvolutionRequest evolutionRequest, String commandName) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = storeFileAndGetInternalRepresentation(evolutionRequest);
		List<String> commandArgumentsFiledsInfo = evolutionInternal.getFieldsInfo();
		List<String> commandArgumentsAll = new LinkedList<>();
		List<String> commandArgumentsProgramOnly = new LinkedList<>();

		evolutionInternal.setFieldsInfo();
		evolutionInternal.setAllThresholds();

		String resultFileName = super.getPrefix() + UUID.randomUUID().toString() + ".txt";
		File outputFile = new File(super.getWorkingDir() + resultFileName);



		commandArgumentsFiledsInfo.add(0, super.getBash());
		commandArgumentsFiledsInfo.add(1, prepareNames);

		commandArgumentsProgramOnly.add(0, super.getBash());
		commandArgumentsProgramOnly.add(1, blastAllVsAll);

		commandArgumentsAll.addAll(evolutionInternal.getFieldsInfo());
		commandArgumentsAll.addAll(evolutionInternal.getAllThresholds());
		commandArgumentsAll.add(0, super.getPython());
		commandArgumentsAll.add(1, createCogs);



		for (String arg : commandArgumentsFiledsInfo) {
			System.out.println("arg " + arg);
		}


		ProcessBuilder processBuilderPrepareNames = new ProcessBuilder(commandArgumentsFiledsInfo);
		processBuilderPrepareNames.directory(new File(super.getWorkingDir()));

		ProcessBuilder processBuilderBlast = new ProcessBuilder(commandArgumentsProgramOnly);
		processBuilderBlast.directory(new File(super.getWorkingDir()));

		ProcessBuilder processBuilderCreateCogs = new ProcessBuilder(commandArgumentsAll);
		processBuilderCreateCogs.directory(new File(super.getWorkingDir()));



		try {
			Process process = processBuilderPrepareNames.start();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return null;
	}

	public EvolutionInternal storeFileAndGetInternalRepresentation(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		super.getStorageService().storeMultipleFiles(evolutionRequest.getListOfFiles());
		EvolutionInternal evolutionInternal = fromEvolRequestToEvolInternal(evolutionRequest);

		return evolutionInternal;
	}
}
