package serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BioUniverseService;
import service.StorageService;
import springconfiguration.AppProperties;
import biojobs.BioJobDao;

/**
 * Created by vadim on 8/14/17.
 */
@Service
public class BioUniverseServiceImpl implements BioUniverseService {
    private final AppProperties properties;
    private final StorageService storageService;
    private final BioJobDao bioJobDao;
    private final BioProgramsServiceImpl bioProgramsServiceImpl;



    @Autowired
    public BioUniverseServiceImpl(StorageService storageService, AppProperties properties, BioJobDao bioJobDao, BioProgramsServiceImpl bioProgramsServiceImpl) {
        this.storageService = storageService;
        this.properties = properties;
        this.bioJobDao = bioJobDao;
        this.bioProgramsServiceImpl = bioProgramsServiceImpl;
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
    @Override
    public String getProgram(String programName) {
        return bioProgramsServiceImpl.getProgram(programName);
    }

    @Override
    public Integer getMaxJobId() {
        Integer maxJobId = bioJobDao.getMaxIdOfJob();
        return maxJobId != null ? maxJobId : 0;
    }



}
