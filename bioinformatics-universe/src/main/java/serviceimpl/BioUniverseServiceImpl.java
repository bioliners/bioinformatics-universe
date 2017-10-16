package serviceimpl;


import enums.BioPrograms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BioUniverseService;
import service.StorageService;
import springconfiguration.AppProperties;
import biojobs.BioJobDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadim on 8/14/17.
 */
@Service
public class BioUniverseServiceImpl implements BioUniverseService {
    private final AppProperties properties;
    private final StorageService storageService;

    private final Map<String, String> programs = new HashMap<>();

    @Autowired
    public BioUniverseServiceImpl(StorageService storageService, AppProperties properties) {
        this.storageService = storageService;
        this.properties = properties;

        programs.put(BioPrograms.CREATE_COGS.getProgramName(), properties.getCreateCogs());
        programs.put(BioPrograms.MAKE_UNIQUE.getProgramName(), properties.getMakeUnique());
        programs.put(BioPrograms.GET_SEQ_BYNAME.getProgramName(), properties.getGetSeqByName());
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
        return programs.get(programName);
    }


}
