package service;

import biojobs.BioJob;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;

import java.util.List;

public interface BioTaskLongRunnigService {
    void runProgram(BioTaskRequestInternal bioTaskRequestInternal) throws IncorrectRequestException;
    BioJob getBioJobIfFinished(int jobId);
    int saveBioJobToDB(String programName, String oFileName);
    int saveBioJobToDB(String programName, List<String> oFileNames);
    void saveResultFileToDB(String resultFilename, int jobId);
}
