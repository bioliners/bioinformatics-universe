package converters;

import com.fasterxml.jackson.databind.JsonSerializer;
import exceptions.IncorrectRequestException;
import model.internal.SequenceInternal;
import model.request.SequenceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import service.Delimeters;
import service.StorageService;

public class ConverterMain {


	@Autowired
	private final StorageService storageService;

	public ConverterMain(StorageService storageService) {
		this.storageService = storageService;
	}


	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest, String firstFileName,
															   String SecondFileName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = new SequenceInternal();

		sequenceInternal.setFirstFileName(firstFileName);
		sequenceInternal.setSecondFileName(SecondFileName);


		if (sequenceRequest.getFirstFileColumn() != null) {

			try {
				Integer.parseInt(sequenceRequest.getFirstFileColumn());
			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in firstFileColumn field of SequenceRequest object.", ne);
			}
			sequenceInternal.setFirstFileColumn(sequenceRequest.getFirstFileColumn());
		}

		if (sequenceRequest.getSecondFileColumn() != null) {
			try {
				Integer.parseInt(sequenceRequest.getSecondFileColumn());

			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in secondFileColumn field of SequenceRequest object.", ne);
			}
			sequenceInternal.setSecondFileColumn(sequenceRequest.getSecondFileColumn());
		}

		if (sequenceRequest.getFirstFileDelim() != null){
			sequenceInternal.setFirstFileDelim(
					Delimeters.valueOf(sequenceRequest.getFirstFileDelim().toUpperCase()).toString());
		}
		if (sequenceRequest.getSecondFileDelim() != null) {
			sequenceInternal.setSecondFileDelim(
					Delimeters.valueOf(sequenceRequest.getSecondFileDelim().toUpperCase()).toString());
		}
		return sequenceInternal;
	}
}
