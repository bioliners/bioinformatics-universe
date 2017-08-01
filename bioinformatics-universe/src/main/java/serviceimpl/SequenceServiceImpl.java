package serviceimpl;

import static com.google.common.base.Strings.isNullOrEmpty;
import static converters.ConverterMain.fromSeqRequestToSeqInternal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import exceptions.IncorrectRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;
import org.springframework.web.multipart.MultipartFile;
import service.SequenceService;
import service.StorageService;

@Service
public class SequenceServiceImpl implements SequenceService {
	
	 private final String workingDir;
	 private final String getSeqByName;
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
	}

	public SequenceInternal storeFileAndGetInternalRepr(SequenceRequest sequenceRequest) throws IncorrectRequestException {
		String firstFileName = "";
		String secondFileName = "";

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

	public String getByName(SequenceRequest sequenceRequest) throws IncorrectRequestException {

		SequenceInternal sequenceInternal = storeFileAndGetInternalRepr(sequenceRequest);

		String resultFileName = prefix + UUID.randomUUID().toString() + ".txt";

		File outputFile = new File(workingDir + resultFileName);


        ProcessBuilder processBuilder = new ProcessBuilder(python, getSeqByName, sequenceInternal.getFirstFileName(), sequenceInternal.getSecondFileName(),
				sequenceInternal.getFirstFileDelim(), sequenceInternal.getFirstFileColumn(), sequenceInternal.getSecondFileDelim(), sequenceInternal.getSecondFileColumn());
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

	
	public String makeUnique() {
		return "";
	}
	
	public String extract() {
		return "";
	}
}
