package springconfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.UUID;

@ConfigurationProperties("program")
@PropertySource("classpath:application.properties")
public class AppProperties {

    private String workingDirLocation = "bioinformatics-programs-workingDir";
    private String bioProgramsDir = "../bioinformatics-programs";
    private String pathToMainDirFromBioProgs = "../";
    private String getSeqByNameProgram = bioProgramsDir + "/getSequencesByNames.py";
    private String makeUniqueProgram = bioProgramsDir + "/getUniqueSeqs.py";
    private String prepareNamesProgram = bioProgramsDir + "/prepareNames.sh";
    private String blastAllVsAllProgram = bioProgramsDir + "/blast-all-vs-all.sh";
    private String createCogsProgram = bioProgramsDir + "/createCOGs.py";
    private String pythonLocation = "/usr/bin/python";
    private String bashLocation = "/bin/bash";
    private String blastpLocation = "/usr/bin/blastp";
    private String resultFilePrefix = "/bio-universe-";
    private String postfix = ".txt";


    public String getWorkingDirLocation() {
        return workingDirLocation;
    }

    public void setWorkingDirLocation(String workingDirLocation) {
        this.workingDirLocation = workingDirLocation;
    }

    public String getMultipleWorkingFilesLocation() {
        return "files-" + UUID.randomUUID().toString();
    }

    public String getBioProgramsDir() {
        return bioProgramsDir;
    }

    public void setBioProgramsDir(String bioProgramsDir) {
        this.bioProgramsDir = bioProgramsDir;
    }

    public String getPathToMainDirFromBioProgs() {
        return pathToMainDirFromBioProgs;
    }

    public void setPathToMainDirFromBioProgs(String pathToMainDirFromBioProgs) {
        this.pathToMainDirFromBioProgs = pathToMainDirFromBioProgs;
    }

    public String getGetSeqByNameProgram() {
        return getSeqByNameProgram;
    }

    public void setGetSeqByNameProgram(String getSeqByNameProgram) {
        this.getSeqByNameProgram = getSeqByNameProgram;
    }

    public String getPythonLocation() {
        return pythonLocation;
    }

    public void setPythonLocation(String pythonLocation) {
        this.pythonLocation = pythonLocation;
    }

    public String getResultFilePrefix() {
        return resultFilePrefix;
    }

    public void setResultFilePrefix(String resultFilePrefix) {
        this.resultFilePrefix = resultFilePrefix;
    }

    public String getMakeUniqueProgram() {
        return makeUniqueProgram;
    }

    public void setMakeUniqueProgram(String makeUniqueProgram) {
        this.makeUniqueProgram = makeUniqueProgram;
    }

    public String getBashLocation() {
        return bashLocation;
    }

    public void setBashLocation(String bashLocation) {
        this.bashLocation = bashLocation;
    }

    public String getBlastpLocation() {
        return blastpLocation;
    }

    public void setBlastpLocation(String blastpLocation) {
        this.blastpLocation = blastpLocation;
    }

    public String getPrepareNamesProgram() {
        return prepareNamesProgram;
    }

    public void setPrepareNamesProgram(String prepareNamesProgram) {
        this.prepareNamesProgram = prepareNamesProgram;
    }

    public String getBlastAllVsAllProgram() {
        return blastAllVsAllProgram;
    }

    public void setBlastAllVsAllProgram(String blastAllVsAllProgram) {
        this.blastAllVsAllProgram = blastAllVsAllProgram;
    }

    public String getCreateCogsProgram() {
        return createCogsProgram;
    }

    public void setCreateCogsProgram(String createCogsProgram) {
        this.createCogsProgram = createCogsProgram;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postFix) {
        this.postfix = postFix;
    }

}
