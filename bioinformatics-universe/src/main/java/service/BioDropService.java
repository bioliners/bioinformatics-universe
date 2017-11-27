package service;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import biojobs.BioJobDao;
import biojobs.BioJobResultDao;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
import model.request.BioTaskRequest;
import springconfiguration.AppProperties;

import java.util.List;

public interface BioDropService {
    String getMultipleWorkingFilesLocation();

    String getPrefix();

    String getPostfix();

    AppProperties getProperties();

    StorageService getStorageService();

    BioDropDao getBioDropDao();

    String getPathToMainDirFromBioProgs();

    List<String> storeFiles(BioTaskRequest bioTaskRequest);

    void launchProcess(List<String> commandArguments);

    BioDrop checkInputAndGetBioDrop(BioTaskRequest bioTaskRequest) throws IncorrectRequestException;

    BioTaskRequestInternal prepareCommandArguments(BioTaskRequest bioTaskRequest, BioDrop bioDrop);
}
