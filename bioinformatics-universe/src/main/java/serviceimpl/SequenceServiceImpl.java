package serviceimpl;

import static com.google.common.base.Strings.isNullOrEmpty;
import static converters.ConverterMain.fromSeqRequestToSeqInternal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	 private final String workingDir = "bioinformatics-programs-workingDir";
	 private final String bioProgramsDir =  "../bioinformatics-programs";
	 private final String getSeqByName = bioProgramsDir + "/getSequencesByNames.py";
	
	@Autowired
    private final StorageService storageService;
	
	public SequenceServiceImpl(StorageService storageService) {
		this.storageService = storageService;
	}

	public SequenceInternal validateRequestAndGetInternalRepr(SequenceRequest sequence) throws IncorrectRequestException {
		String firstFileName = "";
		String secondFileName = "";

		if (sequence.getFirstFile() != null) {
			if (!isNullOrEmpty(sequence.getFirstFileTextArea().trim())) {
				throw new IncorrectRequestException("firstFileTextArea and firstFileName are both not empty");
			} else {
				storageService.store(sequence.getFirstFile());
			}

		} else if (!isNullOrEmpty(sequence.getFirstFileTextArea().trim())) {
			firstFileName = storageService.createAndStore(sequence.getFirstFileTextArea());
		}

		if (sequence.getSecondFile() != null) {
			if (!isNullOrEmpty(sequence.getSecondFileTextArea().trim())) {
				throw new IncorrectRequestException("secondFileTextArea and firstFileName are both not empty");
			} else {
				storageService.store(sequence.getSecondFile());
			}

		} else if (!isNullOrEmpty(sequence.getSecondFileTextArea().trim())) {
			secondFileName = storageService.createAndStore(sequence.getSecondFileTextArea());
		}


		SequenceInternal sequenceInternal = fromSeqRequestToSeqInternal(sequence);
		if (!firstFileName.isEmpty()) {
			System.out.println("firstFileName " + firstFileName);
			sequenceInternal.setFirstFileName(firstFileName);
		}
		if (!secondFileName.isEmpty()) {
			System.out.println("secondFileName " + secondFileName);
			sequenceInternal.setSecondFileName(secondFileName);
		}

		return sequenceInternal;
	}

	public String getByName(SequenceRequest sequence) throws IncorrectRequestException {

		SequenceInternal sequenceInternal = validateRequestAndGetInternalRepr(sequence);


		File outputFile = new File(workingDir + "/results.txt");
        ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/python", getSeqByName, sequenceInternal.getFirstFileName(), sequenceInternal.getSecondFileName());
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
