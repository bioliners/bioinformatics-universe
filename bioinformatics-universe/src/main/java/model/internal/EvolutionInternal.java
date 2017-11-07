package model.internal;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vadim on 8/14/17.
 */
public class EvolutionInternal {
    private String fileDelim;
    private String fileColumn;
    private String identityThreshold;
    private String coverageThreshold;
    private String evalueThreshold;
    private String commandToBeProcessedBy;
    private String doMerge;
    private String resultFileName;
    private int jobId;

    private List<List<String>> commandsAndArguments;

	private List<String> fieldsInfo = new LinkedList<>();
    private List<String> allFields = new LinkedList<>();

    public String getFileDelim() {
        return fileDelim;
    }

    public void setFileDelim(String fileDelim) {
        this.fileDelim = fileDelim;
    }

    public String getFileColumn() {
        return fileColumn;
    }

    public void setFileColumn(String fileColumn) {
        this.fileColumn = fileColumn;
    }

    public List<String> getFieldsInfo() {
        return fieldsInfo;
    }

    public String getIdentityThreshold() {
        return identityThreshold;
    }

    public void setIdentityThreshold(String identityThreshold) {
        this.identityThreshold = identityThreshold;
    }

    public String getCoverageThreshold() {
        return coverageThreshold;
    }

    public void setCoverageThreshold(String coverageThreshold) {
        this.coverageThreshold = coverageThreshold;
    }

    public String getEvalueThreshold() {
        return evalueThreshold;
    }

    public void setEvalueThreshold(String evalueThreshold) {
        this.evalueThreshold = evalueThreshold;
    }

    public void setCommandToBeProcessedBy(String commandToBeProcessedBy) {
        this.commandToBeProcessedBy = commandToBeProcessedBy;
    }
    
    public String getCommandToBeProcessedBy() {
        return commandToBeProcessedBy;
    }
    
    public String getDoMerge() {
		return doMerge;
	}

	public void setDoMerge(String doMerge) {
		this.doMerge = doMerge;
	}
	
    public List<String> getAllFields() {
        return allFields;
    }

    public void setFields() {
        if (fileDelim != null) {
            fieldsInfo.add(fileDelim);
        }
        if (fileColumn != null) {
            fieldsInfo.add(fileColumn);
        }
        allFields.addAll(fieldsInfo);

        if (identityThreshold != null) {
            allFields.add(identityThreshold);
        }
        if (coverageThreshold != null) {
            allFields.add(coverageThreshold);
        }
        if (evalueThreshold != null) {
            allFields.add(evalueThreshold);
        }
    }

    public String getResultFileName() {
        return resultFileName;
    }

    public void setResultFileName(String resultFileName) {
        this.resultFileName = resultFileName;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public List<List<String>> getCommandsAndArguments() {
        return commandsAndArguments;
    }

    public void setCommandsAndArguments(List<List<String>> commandsAndArguments) {
        this.commandsAndArguments = commandsAndArguments;
    }
}
