package converters;

import model.internal.SequenceInternal;
import model.request.SequenceRequest;

public class ConverterMain {
	
	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest) {
		
		
		SequenceInternal sequenceInternal = new SequenceInternal();
		sequenceInternal.setFirstFile(sequenceRequest.getFirstFile().getOriginalFilename());
		sequenceInternal.setSecondFile(sequenceRequest.getSecondFile().getOriginalFilename());
		
		sequenceInternal.setFirstFileColumn(sequenceRequest.getFirstFileColumn());
		sequenceInternal.setSecondFileColumn(sequenceRequest.getSecondFileColumn());
		
		sequenceInternal.setFirstFileDelim(sequenceRequest.getFirstFileDelim());
		sequenceInternal.setSecondFileDelim(sequenceRequest.getSecondFileDelim());
			
		
		return sequenceInternal;
	}

}
