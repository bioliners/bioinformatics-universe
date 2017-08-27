package converters;

import model.internal.EvolutionInternal;
import model.internal.SequenceInternal;
import model.request.EvolutionRequest;
import model.request.SequenceRequest;
import service.Delimeters;
import service.StorageService;
import exceptions.IncorrectRequestException;

public class ConverterMain {

	public ConverterMain(StorageService storageService) {
	}

	private static String getInternalDelim(String field) throws IncorrectRequestException {
		String internalDelim = null;
		if (field != null) {
			try {
				internalDelim = Delimeters.valueOf(field.toUpperCase()).toString();
			} catch (Exception e) {
				throw new IncorrectRequestException("Delimeter of Request object is incorrect.", e);
			}
		}
		return internalDelim;
	}
	
	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest, String firstFileName,
															   String SecondFileName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = new SequenceInternal();

		sequenceInternal.setFirstFileName(firstFileName);
		sequenceInternal.setSecondFileName(SecondFileName);
		sequenceInternal.setCommandToBeProcessedBy(sequenceRequest.getCommandToBeProcessedBy());

		sequenceInternal.setFirstFileColumn(Integer.toString(sequenceRequest.getFirstFileColumn()));
		sequenceInternal.setSecondFileColumn(Integer.toString(sequenceRequest.getSecondFileColumn()));
		
		sequenceInternal.setFirstFileDelim(getInternalDelim(sequenceRequest.getFirstFileDelim()));
		sequenceInternal.setSecondFileDelim(getInternalDelim(sequenceRequest.getSecondFileDelim()));
		
		return sequenceInternal;
	}

	public static EvolutionInternal fromEvolRequestToEvolInternal(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = new EvolutionInternal();
		if (evolutionRequest.getFileColumn() != null) {
			try {
				Integer.parseInt(evolutionRequest.getFileColumn());
			} catch (Exception e) {
				throw new IncorrectRequestException("fileColumn field of EvolutionRequest object is incorrect.", e);
			}
			evolutionInternal.setFileColumn(evolutionInternal.getFileColumn());
		}

		if (evolutionRequest.getFileDelim() != null) {
			try {
				evolutionInternal.setFileDelim(Delimeters.valueOf(evolutionRequest.getFileDelim().toUpperCase()).toString());
			} catch (Exception e) {
				throw new IncorrectRequestException("Delimeter of EvolutionRequest object is incorrect.", e);
			}
		}

		if (evolutionRequest.getCoverageThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getCoverageThreshold());
			} catch (Exception e) {
				throw new IncorrectRequestException("coverageThreshold field of EvolutionRequest object is incorrect.", e);
			}
			evolutionInternal.setCoverageThreshold(evolutionInternal.getCoverageThreshold());
		}

		if (evolutionRequest.getIdentityThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getIdentityThreshold());
			} catch (Exception e) {
				throw new IncorrectRequestException("identityThreshold field of EvolutionRequest object is incorrect.", e);
			}
			evolutionInternal.setIdentityThreshold(evolutionInternal.getIdentityThreshold());
		}

		if (evolutionRequest.getEvalueThreshold() != null) {
			try {
				Integer.parseInt(evolutionRequest.getEvalueThreshold());
			} catch (Exception e) {
				throw new IncorrectRequestException("evalueThreshold field of EvolutionRequest object is incorrect.", e);
			}
			evolutionInternal.setEvalueThreshold(evolutionInternal.getEvalueThreshold());
		}

		return evolutionInternal;
	}
}
