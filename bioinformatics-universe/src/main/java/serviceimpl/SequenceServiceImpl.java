package serviceimpl;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.SequenceService;
import service.StorageService;
import converters.ConverterMain;

@Service
public class SequenceServiceImpl implements SequenceService {
	
	@Autowired
    private final StorageService storageService;
	private final ConverterMain converterMain;
	
	public SequenceServiceImpl(StorageService storageService) {
		this.storageService = storageService;
		this.converterMain = new ConverterMain();
	}
	
	public String getByName(SequenceRequest sequence) {
        storageService.store(sequence.getFirstFile());
        storageService.store(sequence.getSecondFile());
        
        SequenceInternal sequenceInternal = converterMain.fromSeqRequestToSeqInternal(sequence);
        
		return "";
	}
	public String makeUnique() {
		return "";
		
	}
	public String extract() {
		return "";
		
	}

}
