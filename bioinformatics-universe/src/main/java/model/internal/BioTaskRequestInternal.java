package model.internal;

import java.util.List;

public class BioTaskRequestInternal {
    private List<String> commandArguments;
    private List<String> outputFileNames;
    private Integer jobId;

    public BioTaskRequestInternal(List<String> commandArguments, List<String> outputFileNames) {
        this.commandArguments = commandArguments;
        this.outputFileNames = outputFileNames;
    }
    public List<String> getCommandArguments() {
        return commandArguments;
    }

    public void setCommandArguments(List<String> commandArguments) {
        this.commandArguments = commandArguments;
    }

    public List<String> getOutputFileNames() {
        return outputFileNames;
    }

    public void setOutputFileNames(List<String> outputFileNames) {
        this.outputFileNames = outputFileNames;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
