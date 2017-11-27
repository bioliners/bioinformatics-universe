package serviceimpl;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import biojobs.BioJobDao;
import biojobs.BioJobResultDao;
import enums.BioPrograms;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
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
import java.util.*;

@Service
public class BioDropServiceImpl implements BioDropService {
    private final AppProperties properties;
    private final StorageService storageService;
    private final BioDropDao bioDropDao;
    //TODO: Maybe delete?
    private final static String precaution = " ";
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

    @Override
    public BioDrop checkInputAndGetBioDrop(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
        if (bioTaskRequest.getSubTab() == null ) {
            throw new IncorrectRequestException("Subtab of BioTaskRequest can not be empty.");
        } else if (bioTaskRequest.getParameters() == null) {
            throw new IncorrectRequestException("Parameters of BioTaskRequest can not be empty.");
        } else if (bioTaskRequest.getListOfFiles() == null) {
            throw new IncorrectRequestException("List of input files of BioTaskRequest can not be empty.");
        }
        BioDrop bioDrop = bioDropDao.findBySubTab(bioTaskRequest.getSubTab());
        if (bioDrop.getNumberOfInputs() < bioTaskRequest.getListOfFiles().size()) {
            throw new IncorrectRequestException("Number of input files can not be less than " +  bioDrop.getNumberOfInputs());
        }
        return bioDrop;
    }
    //TODO: Maybe check the number of input and output files before entering loops?
    @Override
    public BioTaskRequestInternal prepareCommandArguments(final BioTaskRequest bioTaskRequest, final BioDrop bioDrop) {
        List<String> inputFilePrefixes = bioTaskRequest.getListOfFilesParamPrefixes();
        List<String> inputFileNames = storeFiles(bioTaskRequest);
        for (int i = 0; i < inputFilePrefixes.size(); i++) {
            inputFileNames.set(i, inputFilePrefixes.get(i) + precaution + inputFileNames.get(i));
        }

        String[] outputFilePrefixes = bioDrop.getOutputFilePrefixes().split(";");
        List<String> oFileNames = new LinkedList<>();
        List<String> outputFileNames = new ArrayList<>();
        String name;
        for (int i = 0; i < outputFilePrefixes.length; i++) {
            name = getPrefix() + UUID.randomUUID().toString() + getPostfix();
            oFileNames.add(name);
            outputFileNames.add(outputFilePrefixes[i] + precaution + name);
        }

        List<String> commandArguments = new LinkedList<>();
        commandArguments.add(bioDrop.getProgramLanguage());
        commandArguments.add(bioDrop.getProgramName());
        commandArguments.addAll(inputFileNames);
        commandArguments.addAll(outputFileNames);
        commandArguments.addAll(bioTaskRequest.getParameters().get(bioDrop.getProgramName()));

        return new BioTaskRequestInternal(commandArguments, oFileNames);
    }

}
