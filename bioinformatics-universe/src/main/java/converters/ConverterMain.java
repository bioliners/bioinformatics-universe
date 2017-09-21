package converters;

import enums.Delimeters;
import enums.ParamPrefixes;
import exceptions.IncorrectRequestException;
import model.internal.EvolutionInternal;
import model.internal.SequenceInternal;
import model.request.EvolutionRequest;
import model.request.SequenceRequest;
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

		sequenceInternal.setFirstFileColumn(checkNumAndGetString(sequenceRequest.getFirstFileColumn()));
		sequenceInternal.setSecondFileColumn(checkNumAndGetString(sequenceRequest.getSecondFileColumn()));


		sequenceInternal.setFirstFileDelim(getInternalDelim(sequenceRequest.getFirstFileDelim()));
		sequenceInternal.setSecondFileDelim(getInternalDelim(sequenceRequest.getSecondFileDelim()));
		
		return sequenceInternal;
	}

	public static EvolutionInternal fromEvolRequestToEvolInternal(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = new EvolutionInternal();
		
		evolutionInternal.setFileColumn(checkForNullAndGet(ParamPrefixes.COLUMN.getPreifx(), checkClmnAndGetString(evolutionRequest.getFileColumn())));
		evolutionInternal.setCoverageThreshold(checkForNullAndGet(ParamPrefixes.COVERAGE_THRESH.getPreifx(), checkNumAndGetString(evolutionRequest.getCoverageThreshold())));
		evolutionInternal.setIdentityThreshold(checkForNullAndGet(ParamPrefixes.IDENTITY_THRESH.getPreifx(), checkNumAndGetString(evolutionRequest.getIdentityThreshold())));
		evolutionInternal.setEvalueThreshold(checkForNullAndGet(ParamPrefixes.EVAL_THRESH.getPreifx(), checkNumAndGetString(evolutionRequest.getEvalueThreshold())));
		evolutionInternal.setDoMerge(checkForNullAndGet(ParamPrefixes.MERGE.getPreifx(), evolutionRequest.getDoMerge()));
		evolutionInternal.setFileDelim(checkForNullAndGet(ParamPrefixes.DELIM.getPreifx(), getInternalDelim(evolutionRequest.getFileDelim())));

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

	private static String checkForNullAndGet(String paramPrefix, String param) {
		if (param != null) {
			return paramPrefix + param;
		}
		return null;
	}


	private static String checkClmnAndGetString(Number num) {
		if (num != null) {
			return String.valueOf(num.intValue()-1);
		}
		return null;

	}
	private static String checkNumAndGetString(Number num) {
		if (num != null) {
			return String.valueOf(num);
		}
		return null;
	}
}
