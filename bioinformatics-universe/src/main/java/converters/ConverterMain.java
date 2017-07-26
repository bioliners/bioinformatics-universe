package converters;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import service.StorageService;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ConverterMain {

	@Autowired
	private final StorageService storageService;

	public ConverterMain(StorageService storageService) {
		this.storageService = storageService;
	}



	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest) {

		
		SequenceInternal sequenceInternal = new SequenceInternal();

		if (sequenceRequest.getFirstFile() != null) {
			sequenceInternal.setFirstFileName(sequenceRequest.getFirstFile().getOriginalFilename());
		}
		if (sequenceRequest.getSecondFile() != null) {
			sequenceInternal.setSecondFileName(sequenceRequest.getSecondFile().getOriginalFilename());
		}

		sequenceInternal.setFirstFileColumn(sequenceRequest.getFirstFileColumn());
		sequenceInternal.setSecondFileColumn(sequenceRequest.getSecondFileColumn());
		
		sequenceInternal.setFirstFileDelim(sequenceRequest.getFirstFileDelim());
		sequenceInternal.setSecondFileDelim(sequenceRequest.getSecondFileDelim());
			
		
		return sequenceInternal;
	}

}
