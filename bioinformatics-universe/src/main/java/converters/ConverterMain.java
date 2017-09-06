package converters;

import model.internal.EvolutionInternal;
import model.internal.SequenceInternal;
import model.request.EvolutionRequest;
import model.request.SequenceRequest;
import service.StorageService;
import enums.Delimeters;
import enums.ParamPrefixes;

import exceptions.IncorrectRequestException;

public class ConverterMain {

	public ConverterMain(StorageService storageService) {
	}

	public static SequenceInternal fromSeqRequestToSeqInternal(SequenceRequest sequenceRequest, String firstFileName,
															   String SecondFileName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = new SequenceInternal();

		sequenceInternal.setFirstFileName(firstFileName);
		sequenceInternal.setSecondFileName(SecondFileName);
		sequenceInternal.setCommandToBeProcessedBy(sequenceRequest.getCommandToBeProcessedBy());

		sequenceInternal.setFirstFileColumn(checkNumAndGetString(sequenceRequest.getFirstFileColumn()));
		sequenceInternal.setSecondFileColumn(checkNumAndGetString(sequenceRequest.getSecondFileColumn()));


		sequenceInternal.setFirstFileDelim(getInternalDelim(sequenceRequest.getFirstFileDelim()));
		sequenceInternal.setSecondFileDelim(getInternalDelim(sequenceRequest.getSecondFileDelim()));
		
		return sequenceInternal;
	}

	public static EvolutionInternal fromEvolRequestToEvolInternal(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = new EvolutionInternal();
		
		evolutionInternal.setFileColumn(ParamPrefixes.COLUMN + checkNumAndGetString(evolutionRequest.getFileColumn()));
		evolutionInternal.setCoverageThreshold(ParamPrefixes.COVERAGE_THRESH + checkNumAndGetString(evolutionRequest.getCoverageThreshold()));
		evolutionInternal.setIdentityThreshold(ParamPrefixes.IDENTITY_THRESH + checkNumAndGetString(evolutionRequest.getIdentityThreshold()));
		evolutionInternal.setEvalueThreshold(ParamPrefixes.EVAL_THRESH + checkNumAndGetString(evolutionRequest.getEvalueThreshold()));
		evolutionInternal.setDoMerge(ParamPrefixes.MERGE + evolutionRequest.getDoMerge());
		evolutionInternal.setFileDelim(ParamPrefixes.DELIM + getInternalDelim(evolutionRequest.getFileDelim()));

		return evolutionInternal;
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

	private static String checkNumAndGetString(Number num) {
		if (num != null) {
			return String.valueOf(num);
		}
		return null;
	}
}
