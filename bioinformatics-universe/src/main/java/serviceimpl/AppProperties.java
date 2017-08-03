package serviceimpl;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("serviceimpl")
public class AppProperties {

    /**
     * Folders locations
     */
    private String location = "bioinformatics-programs-workingDir";
    private String bioProgramsDir = "../bioinformatics-programs";
    private String getSeqByNameProgram = bioProgramsDir + "/getSequencesByNames.py";
    private String makeUniqueProgram = bioProgramsDir + "/getUniqueSeqs.py";
    private String pythonLocation = "/usr/bin/python";
    private String resultFilePrefix = "/bio-universe-";

    public String getWorkingDirLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBioProgramsDir() {
        return bioProgramsDir;
    }

    public void setBioProgramsDir(String bioProgramsDir) {
        this.bioProgramsDir = bioProgramsDir;
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
}