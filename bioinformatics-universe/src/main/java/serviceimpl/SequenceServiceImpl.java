package serviceimpl;

import static com.google.common.base.Strings.isNullOrEmpty;
import static converters.ConverterMain.fromSeqRequestToSeqInternal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

import exceptions.IncorrectRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;
import service.SequenceService;
import service.StorageService;

@Service
public class SequenceServiceImpl implements SequenceService {
	private final String workingDir;
	private final String getSeqByName;
	private final String makeUnique;
	private final String python;
	private final String prefix;
    private final StorageService storageService;
	private final AppProperties properties;

	@Autowired
	public SequenceServiceImpl(StorageService storageService, AppProperties properties) {
		this.storageService = storageService;
		this.properties = properties;
		this.workingDir = properties.getWorkingDirLocation();
		this.getSeqByName = properties.getGetSeqByNameProgram();
		this.python = properties.getPythonLocation();
		this.prefix = properties.getResultFilePrefix();
		this.makeUnique = properties.getMakeUniqueProgram();
	}

	@Override
	public String getByName(SequenceRequest sequenceRequest) throws IncorrectRequestException {
		return launchProcessAndGetResultFileName(sequenceRequest, getSeqByName);
	}

	@Override
	public String makeUnique(SequenceRequest sequenceRequest) throws IncorrectRequestException {
		return launchProcessAndGetResultFileName(sequenceRequest, makeUnique);
	}

	@Override
	public String extract() {
		return "";
	}

	public String launchProcessAndGetResultFileName(SequenceRequest sequenceRequest, String commandName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = storeFileAndGetInternalRepresentation(sequenceRequest);
		sequenceInternal.setAllFields();

		String resultFileName = prefix + UUID.randomUUID().toString() + ".txt";
		File outputFile = new File(workingDir + resultFileName);


		List<String> commandArguments = sequenceInternal.getAllFields();
		commandArguments.add(0, python);
		commandArguments.add(1, commandName);

		for (String arg : commandArguments) {
			System.out.println("arg " + arg);
		}

		ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
		processBuilder.directory(new File(workingDir));
		try {
			Process process = processBuilder.start();

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

	public SequenceInternal storeFileAndGetInternalRepresentation(SequenceRequest sequenceRequest) throws IncorrectRequestException {
		String firstFileName = null;
		String secondFileName = null;

		if (sequenceRequest.getFirstFile() != null) {
			if (!isNullOrEmpty(sequenceRequest.getFirstFileTextArea())) {
				throw new IncorrectRequestException("firstFileTextArea and firstFileName are both not empty");
			} else {
				firstFileName = storageService.store(sequenceRequest.getFirstFile());
			}
		} else if (!isNullOrEmpty(sequenceRequest.getFirstFileTextArea())) {
			firstFileName = storageService.createAndStore(sequenceRequest.getFirstFileTextArea());
		}

		if (sequenceRequest.getSecondFile() != null) {
			if (!isNullOrEmpty(sequenceRequest.getSecondFileTextArea())) {
				throw new IncorrectRequestException("secondFileTextArea and firstFileName are both not empty");
			} else {
				secondFileName = storageService.store(sequenceRequest.getSecondFile());
			}
		} else if (!isNullOrEmpty(sequenceRequest.getSecondFileTextArea())) {
			secondFileName = storageService.createAndStore(sequenceRequest.getSecondFileTextArea());
		}
		SequenceInternal sequenceInternal = fromSeqRequestToSeqInternal(sequenceRequest, firstFileName, secondFileName);
		return sequenceInternal;
	}
}
