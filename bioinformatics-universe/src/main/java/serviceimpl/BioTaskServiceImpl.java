package serviceimpl;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import exceptions.IncorrectRequestException;
import model.request.BioTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BioTaskService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class BioTaskServiceImpl extends BioDropServiceImpl implements BioTaskService {
    //TODO: Maybe delete?
    private final static String precaution = " ";

    public BioTaskServiceImpl(final BioDropDao bioDropDao, final StorageService storageService, final AppProperties properties) {
        super(bioDropDao, storageService, properties);
    }

    //TODO: Maybe check the number of input and output files before entering loops?
    @Override
    public List<String> runProgram(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
        if (bioTaskRequest.getSubTab() == null ) {
            throw new IncorrectRequestException("Subtab of BioTaskRequest can not be empty.");
        } else if (bioTaskRequest.getParameters() == null) {
            throw new IncorrectRequestException("Parameters of BioTaskRequest can not be empty.");
        } else if (bioTaskRequest.getListOfFiles() == null) {
            throw new IncorrectRequestException("List of input files of BioTaskRequest can not be empty.");
        }
        BioDrop bioDrop = super.getBioDropDao().findBySubTab(bioTaskRequest.getSubTab());
        if (bioDrop.getNumberOfInputs() < bioTaskRequest.getListOfFiles().size()) {
            throw new IncorrectRequestException("Number of input files can not be less than " +  bioDrop.getNumberOfInputs());
        }

        List<String> inputFilePrefixes = bioTaskRequest.getListOfFilesParamPrefixes();
        List<String> iFileNames = super.storeFiles(bioTaskRequest);
        List<String> inputFileNames = new ArrayList<>();
        for (int i = 0; i < inputFilePrefixes.size(); i++) {
            inputFileNames.add(i, inputFilePrefixes.get(i) + precaution + iFileNames.get(i));
        }

        String[] outputFilePrefixes = bioDrop.getOutputFilePrefixes().split(";");
        List<String> oFileNames = new LinkedList<>();
        List<String> outputFileNames = new ArrayList<>();
        if (outputFilePrefixes.length > 1) {
            String name;
            for (int i = 0; i < outputFilePrefixes.length; i++) {
                name = super.getPrefix() + UUID.randomUUID().toString() + super.getPostfix();
                oFileNames.add(name);
                outputFileNames.add(outputFilePrefixes[i] + precaution + name);
            }
        }

        List<String> commandArguments = new LinkedList<>();
        commandArguments.add(bioDrop.getProgramLanguage());
        commandArguments.add(bioDrop.getProgramName());
        commandArguments.addAll(inputFileNames);
        commandArguments.addAll(outputFileNames);
        commandArguments.addAll(bioTaskRequest.getParameters().get(bioDrop.getProgramName()));

        super.launchProcess(commandArguments);
        return oFileNames;
    }

}

