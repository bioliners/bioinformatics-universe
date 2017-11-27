package service;

import biojobs.BioJob;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
import model.request.BioTaskRequest;

import java.util.List;

public interface BioTaskLongRunnigService {
    BioTaskRequestInternal storeFilesAndPrepareCommandArguments (final BioTaskRequest bioTaskRequest) throws IncorrectRequestException;
    void runProgram(BioTaskRequestInternal bioTaskRequestInternal) throws IncorrectRequestException;
    BioJob getBioJobIfFinished(int jobId);
    int saveBioJobToDB(String programName, List<String> oFileNames);
    void saveResultFileToDB(String resultFilename, int jobId);
}
