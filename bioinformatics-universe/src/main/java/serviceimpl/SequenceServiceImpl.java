package serviceimpl;

import static converters.ConverterMain.fromSeqRequestToSeqInternal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;
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
	
	public String getByName(SequenceRequest sequence) {
        storageService.store(sequence.getFirstFile());
        storageService.store(sequence.getSecondFile());
        
        SequenceInternal sequenceInternal = fromSeqRequestToSeqInternal(sequence);

        File outputFile = new File(workingDir + "/results.txt");
        ProcessBuilder processBuilder = new ProcessBuilder("/usr/bin/python", getSeqByName, sequenceInternal.getFirstFile(), sequenceInternal.getSecondFile());
        
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
