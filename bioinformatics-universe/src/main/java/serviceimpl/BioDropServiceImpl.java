package serviceimpl;

import biojobs.BioDropDao;
import biojobs.BioJobDao;
import biojobs.BioJobResultDao;
import enums.BioPrograms;
import model.request.BioTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BioDropService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BioDropServiceImpl implements BioDropService {
    private final AppProperties properties;
    private final StorageService storageService;
    private final BioDropDao bioDropDao;

    @Autowired
    public BioDropServiceImpl(BioDropDao bioDropDao, StorageService storageService, AppProperties properties) {
        this.storageService = storageService;
        this.properties = properties;
        this.bioDropDao = bioDropDao;
    }

    @Override
    public String getMultipleWorkingFilesLocation() {
        return properties.getMultipleWorkingFilesLocation();
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
    public BioDropDao getBioDropDao() {
        return bioDropDao;
    }
    @Override
    public List<String> storeFiles(final BioTaskRequest bioTaskRequest) {
        return storageService.storeMultipleFilesBio(bioTaskRequest.getListOfFiles());
    }
    @Override
    public void launchProcess(List<String> commandArguments) {
        ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
        processBuilder.directory(new File(properties.getWorkingDirLocation()));
        try {
            System.out.println("processBuilder.directory() " + processBuilder.directory());
            System.out.println(processBuilder.command());

            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.println("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
