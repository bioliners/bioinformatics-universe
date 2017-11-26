package biojobs;
/**
 * Created by vadim on 11/22/17.
 */

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="biodrop")
public class BioDrop {
    @Id
    @SequenceGenerator(name="pk_sequence_biodrop", sequenceName="biodrop_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pk_sequence_biodrop")
    @Column(name="DROP_ID")
    private int dropId;

    @NotNull
    @Column(name="PROGRAM")
    private String program;

    @NotNull
    @Column(name="PROGRAM_NAME")
    private String programName;

    @NotNull
    @Column(name="SCRIPT_NAME")
    private String scriptName;

    @NotNull
    @Column(name="NUMBER_OF_INPUTS")
    private Integer numberOfInputs;

    @NotNull
    @Column(name="INPUT_PARAM_PREFIXES")
    private String inputFilePrefixes;

    @NotNull
    @Column(name="OUTPUT_PARAM_PREFIXES")
    private String outputFilePrefixes;

    @NotNull
    @Column(name="TAB")
    private String tab;

    @NotNull
    @Column(name="SUB_TAB")
    private String subTab;

    @NotNull
    @Column(name="PROGRAM_PARAMETERS")
    private String programParameters;

    @NotNull
    @Column(name="IS_LONG_RUNNING")
    private boolean isLongRunning;

    @NotNull
    @Column(name="PROGRAM_LANGUAGE")
    private String programLanguage;

    @Column(name="PROGRAM_DEPENDENCIES")
    private String programDependencies;

    @Column(name="PROGRAM_INSTALL_INSTRUCTIONS")
    private String programInstallInstructs;

    public int getDropId() {
        return dropId;
    }

    public void setDropId(int dropId) {
        this.dropId = dropId;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
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

    public int getNumberOfInputs() {
        return numberOfInputs;
    }

    public void setNumberOfInputs(int numberOfInputs) {
        this.numberOfInputs = numberOfInputs;
    }

    public String getOutputFilePrefixes() {
        return outputFilePrefixes;
    }

    public void setOutputFilePrefixes(String outputFilePrefixes) {
        this.outputFilePrefixes = outputFilePrefixes;
    }

    public String getInputFilePrefixes() {
        return inputFilePrefixes;
    }

    public void setInputFilePrefixes(String inputFilePrefixes) {
        this.inputFilePrefixes = inputFilePrefixes;
    }
}
