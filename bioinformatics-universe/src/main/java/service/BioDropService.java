package service;

import biojobs.BioDropDao;
import biojobs.BioJobDao;
import biojobs.BioJobResultDao;
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

    List<String> storeFiles(final BioTaskRequest bioTaskRequest);

    void launchProcess(List<String> commandArguments);
}
