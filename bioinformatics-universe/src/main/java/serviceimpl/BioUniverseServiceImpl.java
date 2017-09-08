package serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import service.BioUniverseService;
import service.StorageService;

/**
 * Created by vadim on 8/14/17.
 */
public class BioUniverseServiceImpl implements BioUniverseService {
    private final AppProperties properties;
    private final StorageService storageService;


    @Autowired
    public BioUniverseServiceImpl(StorageService storageService, AppProperties properties) {
        this.storageService = storageService;
        this.properties = properties;
    }

    @Override
    public String getWorkingDir() {
        return properties.getWorkingDirLocation();
    }
    @Override
    public String getMultipleWorkingFilesLocation() {
        return properties.getMultipleWorkingFilesLocation();
    }
    @Override
    public String getBash() {
        return properties.getBashLocation();
    }
    @Override
    public String getPython() {
        return properties.getPythonLocation();
    }
    @Override
    public String getPrefix() {
        return properties.getResultFilePrefix();
    }
    @Override
    public String getPostfix() {
        return properties.getPostfix();
    }
    @Override
    public AppProperties getProperties() {
        return properties;
    }
    @Override
    public StorageService getStorageService() {
        return storageService;
    }
    @Override
    public String getPathToMainDirFromBioProgs() {
        return properties.getPathToMainDirFromBioProgs();
    }

}
