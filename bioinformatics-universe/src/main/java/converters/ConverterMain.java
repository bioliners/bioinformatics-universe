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
															   String secondFileName) throws IncorrectRequestException {
		SequenceInternal sequenceInternal = new SequenceInternal();
		sequenceInternal.setFirstFileName(checkForNullAndGet(ParamPrefixes.INPUT.getPrefix(), firstFileName));
		sequenceInternal.setSecondFileName(checkForNullAndGet(ParamPrefixes.INPUT_SECOND.getPrefix(), secondFileName));
		sequenceInternal.setCommandToBeProcessedBy(sequenceRequest.getCommandToBeProcessedBy());
		sequenceInternal.setFirstFileColumn(checkForNullAndGet(ParamPrefixes.COLUMN.getPrefix(), checkClmnAndGetString(sequenceRequest.getFirstFileColumn())));
        sequenceInternal.setSecondFileColumn(checkForNullAndGet(ParamPrefixes.COLUMN_SECOND.getPrefix(), checkClmnAndGetString(sequenceRequest.getSecondFileColumn())));
        sequenceInternal.setFirstFileDelim(checkForNullAndGet(ParamPrefixes.DELIM.getPrefix(), getInternalDelim(sequenceInternal.getFirstFileDelim())));
        sequenceInternal.setSecondFileColumn(checkForNullAndGet(ParamPrefixes.DELIM_SECOND.getPrefix(), getInternalDelim(sequenceInternal.getSecondFileDelim())));
        return sequenceInternal;
	}

	public static EvolutionInternal fromEvolRequestToEvolInternal(EvolutionRequest evolutionRequest) throws IncorrectRequestException {
		EvolutionInternal evolutionInternal = new EvolutionInternal();
		evolutionInternal.setFileColumn(checkForNullAndGet(ParamPrefixes.COLUMN.getPrefix(), checkClmnAndGetString(evolutionRequest.getFileColumn())));
		evolutionInternal.setCoverageThreshold(checkForNullAndGet(ParamPrefixes.COVERAGE_THRESH.getPrefix(), checkNumAndGetString(evolutionRequest.getCoverageThreshold())));
		evolutionInternal.setIdentityThreshold(checkForNullAndGet(ParamPrefixes.IDENTITY_THRESH.getPrefix(), checkNumAndGetString(evolutionRequest.getIdentityThreshold())));
		evolutionInternal.setEvalueThreshold(checkForNullAndGet(ParamPrefixes.EVAL_THRESH.getPrefix(), checkNumAndGetString(evolutionRequest.getEvalueThreshold())));
		evolutionInternal.setDoMerge(checkForNullAndGet(ParamPrefixes.MERGE.getPrefix(), evolutionRequest.getDoMerge()));
		evolutionInternal.setFileDelim(checkForNullAndGet(ParamPrefixes.DELIM.getPrefix(), getInternalDelim(evolutionRequest.getFileDelim())));
		evolutionInternal.setCommandToBeProcessedBy(evolutionRequest.getCommandToBeProcessedBy());

		return evolutionInternal;
	}
	
	private static String getInternalDelim(String field) throws IncorrectRequestException {
		String internalDelim = null;
		if (field != null) {
			try {
				internalDelim = Delimeters.valueOf(field.toUpperCase()).toString();
			} catch (Exception e) {
				throw new IncorrectRequestException("Delimiter of Request object is incorrect.", e);
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
