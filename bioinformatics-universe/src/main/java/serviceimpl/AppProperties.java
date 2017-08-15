package serviceimpl;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@ConfigurationProperties("serviceimpl")
public class AppProperties {

    /**
     * Folders locations
     */
    private String workingDirLocation = "bioinformatics-programs-workingDir";
    private String bioProgramsDir = "../bioinformatics-programs";
    private String getSeqByNameProgram = bioProgramsDir + "/getSequencesByNames.py";
    private String makeUniqueProgram = bioProgramsDir + "/getUniqueSeqs.py";
    private String prepareNamesProgram = bioProgramsDir + "/prepareNamesProgram.sh";
    private String blastAllVsAllProgram = bioProgramsDir + "/blast-all-vs-all.sh";
    private String createCogsProgram = bioProgramsDir + "/createCOGs.py";
    private String pythonLocation = "/usr/bin/python";
    private String bashLocation = "/bin/bash";
    private String blastpLocation = "/home/vadim/Soft/blast/ncbi-blast-2.2.31+/bin/blastpLocation";
    private String resultFilePrefix = "/bio-universe-";

    public String getWorkingDirLocation() {
        return workingDirLocation;
    }

    public void setWorkingDirLocation(String workingDirLocation) {
        this.workingDirLocation = workingDirLocation;
    }

    public String getMultipleWorkingFilesLocation() {
        return workingDirLocation + "/files-" + UUID.randomUUID().toString();
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

}