package converters;

import com.fasterxml.jackson.databind.JsonSerializer;
import exceptions.IncorrectRequestException;
import model.internal.EvolutionInternal;
import model.internal.SequenceInternal;
import model.request.EvolutionRequest;
import model.request.SequenceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import service.Delimeters;
import service.StorageService;

public class ConverterMain {

	public ConverterMain(StorageService storageService) {
	}


	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest, String firstFileName,
															   String SecondFileName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = new SequenceInternal();

		sequenceInternal.setFirstFileName(firstFileName);
		sequenceInternal.setSecondFileName(SecondFileName);
		sequenceInternal.setCommandToBeProcessedBy(sequenceRequest.getCommandToBeProcessedBy());

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

	public static EvolutionInternal fromEvolRequestToEvolInternal(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = new EvolutionInternal();

		if (evolutionRequest.getFileColumn() != null) {
			try {
				Integer.parseInt(evolutionRequest.getFileColumn());

			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in fileColumn field of EvolutionRequest object.", ne);
			}
			evolutionInternal.setFileColumn(evolutionInternal.getFileColumn());
		}

		if (evolutionRequest.getFileDelim() != null) {
			evolutionInternal.setFileDelim(
					Delimeters.valueOf(evolutionRequest.getFileDelim().toUpperCase()).toString());
		}

		if (evolutionRequest.getCoverageThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getCoverageThreshold());

			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in coverageThreshold field of EvolutionRequest object.", ne);
			}
			evolutionInternal.setCoverageThreshold(evolutionInternal.getCoverageThreshold());
		}

		if (evolutionRequest.getIdentityThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getIdentityThreshold());

			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in identityThreshold field of EvolutionRequest object.", ne);
			}
			evolutionInternal.setIdentityThreshold(evolutionInternal.getIdentityThreshold());
		}

		if (evolutionRequest.getEvalueThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getEvalueThreshold());

			} catch (Exception ne) {
				throw new IncorrectRequestException("String instead of integer in evalueThreshold field of EvolutionRequest object.", ne);
			}
			evolutionInternal.setEvalueThreshold(evolutionInternal.getEvalueThreshold());
		}

		return evolutionInternal;
	}
}
