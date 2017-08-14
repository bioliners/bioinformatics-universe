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
    private String  commandToBeProcessedBy;
    private List<String> filedsInfo = new LinkedList<>();
    private List<String> allThresholds = new LinkedList<>();

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

    public void setCommandToBeProcessedBy(String commandToBeProcessedBy) {
        this.commandToBeProcessedBy = commandToBeProcessedBy;
    }

    public void setFieldsInfo() {
        if (fileDelim != null) {
            allThresholds.add(fileDelim);
        }
        if (fileColumn != null) {
            allThresholds.add(fileColumn);
        }
    }
    public void setAllThresholds() {
        if (identityThreshold != null) {
            allThresholds.add(identityThreshold);
        }
        if (coverageThreshold != null) {
            allThresholds.add(coverageThreshold);
        }
        if (evalueThreshold != null) {
            allThresholds.add(evalueThreshold);
        }
    }

    public List<String> getAllThresholds() {
        return allThresholds;
    }


    public List<String> getFieldsInfo() {
        return filedsInfo;
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

    public String getCommandToBeProcessedBy() {
        return commandToBeProcessedBy;
    }
}
