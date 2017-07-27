package converters;

import com.fasterxml.jackson.databind.JsonSerializer;
import model.internal.SequenceInternal;
import model.request.SequenceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import service.Delimeters;
import service.StorageService;

import static com.google.common.base.Strings.isNullOrEmpty;

public class ConverterMain {


	@Autowired
	private final StorageService storageService;

	public ConverterMain(StorageService storageService) {
		this.storageService = storageService;
	}



	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest, String firstFileName, String SecondFileName) {
		SequenceInternal sequenceInternal = new SequenceInternal();

		sequenceInternal.setFirstFileName(firstFileName);
		sequenceInternal.setSecondFileName(SecondFileName);

		sequenceInternal.setFirstFileColumn(sequenceRequest.getFirstFileColumn());
		sequenceInternal.setSecondFileColumn(sequenceRequest.getSecondFileColumn());


		if (!sequenceRequest.getFirstFileDelim().equals("null")){
			System.out.println("sequenceRequest.getFirstFileDelim().getClass() " + sequenceRequest.getFirstFileDelim().getClass());
			sequenceInternal.setFirstFileDelim(Delimeters.valueOf(sequenceRequest.getFirstFileDelim().toUpperCase()).toString());
		}
		if (!sequenceRequest.getSecondFileDelim().equals("null")) {
			sequenceInternal.setSecondFileDelim(Delimeters.valueOf(sequenceRequest.getSecondFileDelim().toUpperCase()).toString());
		}
		
		return sequenceInternal;
	}

}
