package serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import service.BioUniverseService;
import service.StorageService;

/**
 * Created by vadim on 8/14/17.
 */
public class BioUniverseServiceImpl implements BioUniverseService {
    private final String workingDir;
    private final String multipleWorkingFilesLocation;
    private final String python;
    private final String bash;
    private final String prefix;
    private final String postfix;
    private final AppProperties properties;
    private final StorageService storageService;


    @Autowired
    public BioUniverseServiceImpl(StorageService storageService, AppProperties properties) {
        this.storageService = storageService;
        this.properties = properties;
        this.workingDir = properties.getWorkingDirLocation();
        this.multipleWorkingFilesLocation = properties.getMultipleWorkingFilesLocation();
        this.bash = properties.getBashLocation();
        this.python = properties.getPythonLocation();
        this.prefix = properties.getResultFilePrefix();
        this.postfix = properties.getPostfix();
    }

    @Override
    public String getWorkingDir() {
        return workingDir;
    }
    @Override
    public String getMultipleWorkingFilesLocation() {
        return multipleWorkingFilesLocation;
    }
    @Override
    public String getBash() {
        return bash;
    }
    @Override
    public String getPython() {
        return python;
    }
    @Override
    public String getPrefix() {
        return prefix;
    }
    @Override
    public String getPostfix() {
        return prefix;
    }
    @Override
    public AppProperties getProperties() {
        return properties;
    }
    @Override
    public StorageService getStorageService() {
        return storageService;
    }

}
