package serviceimpl;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import biojobs.BioJobDao;
import biojobs.BioJobResultDao;
import exceptions.IncorrectRequestException;
import model.request.BioTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.BioTaskService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BioTaskServiceImpl extends BioUniverseServiceImpl implements BioTaskService {

    @Autowired
    private final BioDropDao bioDropDao;

    public BioTaskServiceImpl(final BioDropDao bioDropDao, final StorageService storageService, final AppProperties properties, final BioJobDao bioJobDao, final BioJobResultDao bioJobResultDao) {
        super(storageService, properties, bioJobResultDao, bioJobDao);
        this.bioDropDao = bioDropDao;
    }

    public void storeFilesAndPrepareCommandArguments(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
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

        String resultFileName = super.getPrefix() + UUID.randomUUID().toString() + super.getPostfix();

        List<String> filesParamPrefixes = bioTaskRequest.getListOfFilesParamPrefixes();
        List<String> inputFileNames = storeFile(bioTaskRequest);

        for (int i = 0; i < filesParamPrefixes.size(); i++) {
            inputFileNames.add(i, filesParamPrefixes.get(i) + inputFileNames.get(i));
        }

        List<String> commandArguments = new LinkedList<>();
        commandArguments.add(bioDrop.getProgramLanguage());
        commandArguments.add(bioDrop.getProgramName());
        commandArguments.addAll(inputFileNames);
        commandArguments.add(bioDrop.getOutputFilePrefix() + " " + resultFileName);
        commandArguments.addAll(bioTaskRequest.getParameters().get(bioDrop.getProgramName()));
        launchProcess(commandArguments);
    }


    private void launchProcess(List<String> commandArguments) {
        ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
        processBuilder.directory(new File(super.getWorkingDir()));
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



    public List<String> storeFile(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
        return super.getStorageService().storeMultipleFilesBio(bioTaskRequest.getListOfFiles());
    }

}

