package model.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by vadim on 11/22/17.
 */

public class BioDropRequest {
    private MultipartFile program;
    private String programName;
    private String scriptName;
    private Integer numberOfInputs;
    private String tab;
    private String subTab;
    private String programParameters;
    private boolean isLongRunning;
    private String programLanguage;
    private String programDependencies;
    private String programInstallInstructs;

    public MultipartFile getProgram() {
        return program;
    }

    public void setProgram(MultipartFile program) {
        this.program = program;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getSubTab() {
        return subTab;
    }

    public void setSubTab(String subTab) {
        this.subTab = subTab;
    }

    public String getProgramParameters() {
        return programParameters;
    }

    public void setProgramParameters(String programParameters) {
        this.programParameters = programParameters;
    }

    public boolean isLongRunning() {
        return isLongRunning;
    }

    public void setLongRunning(boolean longRunning) {
        isLongRunning = longRunning;
    }

    public String getProgramLanguage() {
        return programLanguage;
    }

    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    public String getProgramDependencies() {
        return programDependencies;
    }

    public void setProgramDependencies(String programDependencies) {
        this.programDependencies = programDependencies;
    }

    public String getProgramInstallInstructs() {
        return programInstallInstructs;
    }

    public void setProgramInstallInstructs(String programInstallInstructs) {
        this.programInstallInstructs = programInstallInstructs;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Integer getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(Integer numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }
}
