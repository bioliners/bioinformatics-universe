package serviceimpl;

import biojobs.*;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
import model.internal.EvolutionInternal;
import model.request.BioTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import service.BioTaskLongRunnigService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class BioTaskLongRunnigServiceImpl extends BioDropServiceImpl implements BioTaskLongRunnigService {
    @Autowired
    final BioJobDao bioJobDao;
    @Autowired
    final BioJobResultDao bioJobResultDao;

    //TODO: Maybe delete?
    private final static String precaution = " ";
    private final int defaultLastJobId = 1;


    public BioTaskLongRunnigServiceImpl(final BioDropDao bioDropDao, final StorageService storageService,
                                        final AppProperties properties, final BioJobDao bioJobDao, final BioJobResultDao bioJobResultDao) {
        super(bioDropDao, storageService, properties);
        this.bioJobDao = bioJobDao;
        this.bioJobResultDao = bioJobResultDao;
    }


    public BioTaskRequestInternal storeFilesAndPrepareCommandArguments (final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
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

        Integer jobId = null;
        if (oFileNames.size() == 1) {
            jobId = saveBioJobToDB(bioDrop.getProgramName(), oFileNames.get(0));
        } else {
            jobId = saveBioJobToDB(bioDrop.getProgramName(), oFileNames);
        }
        return new BioTaskRequestInternal(commandArguments, oFileNames, jobId);
    }

    @Override
    @Async
    public void runProgram(final BioTaskRequestInternal bioTaskRequestInternal) throws IncorrectRequestException {
        super.launchProcess(bioTaskRequestInternal.getCommandArguments());
        if (bioTaskRequestInternal.getOutputFileNames().size() == 1) {
            saveResultFileToDB(bioTaskRequestInternal.getOutputFileNames().get(0), bioTaskRequestInternal.getJobId());
        } else {
            for (String fileName : bioTaskRequestInternal.getOutputFileNames()) {
                saveResultFileToDB(fileName, bioTaskRequestInternal.getJobId());
            }
        }
    }

    @Override
    public BioJob getBioJobIfFinished(int jobId) {
        BioJob bioJob = bioJobDao.findByJobId(jobId);
        return bioJob.isFinished() ? bioJob : null;
    }



    @Override
    public int saveBioJobToDB(String programName, String oFileName) {
        int jobId = getLastJobId();
        BioJob bioJob = new BioJob();
        bioJob.setProgramNameName(programName);
        bioJob.setJobId(jobId);
        bioJob.setJobDate(LocalDateTime.now());
        bioJob.setFinished(false);

        BioJobResult bioJobResult = new BioJobResult();
        bioJobResult.setResultFile("placeholder");
        bioJobResult.setResultFileName(oFileName);
        bioJobResult.setBiojob(bioJob);

        bioJob.addToBioJobResultList(bioJobResult);
        bioJobDao.save(bioJob);
        return jobId;
    }

    @Override
    public int saveBioJobToDB(String programName, List<String> oFileNames) {
        int jobId = getLastJobId();
        BioJob bioJob = new BioJob();
        bioJob.setProgramNameName(programName);
        bioJob.setJobId(jobId);
        bioJob.setJobDate(LocalDateTime.now());
        bioJob.setFinished(false);

        for (String oFileName : oFileNames) {
            BioJobResult bioJobResult = new BioJobResult();
            bioJobResult.setResultFile("placeholder");
            bioJobResult.setResultFileName(oFileName);
            bioJobResult.setBiojob(bioJob);
            bioJob.addToBioJobResultList(bioJobResult);
        }
        bioJobDao.save(bioJob);
        return jobId;
    }

    @Override
    public void saveResultFileToDB(String resultFilename, int jobId) {
        File file = null;
        try {
            file = getStorageService().loadAsResource(resultFilename).getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder fileAsStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileAsStringBuilder.append(line + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file " + file.toString());
        }

        BioJobResult bioJobResult = bioJobResultDao.findByResultFileName(resultFilename);
        bioJobResult.setResultFile(fileAsStringBuilder.toString());
        bioJobResultDao.save(bioJobResult);

        BioJob bioJob = bioJobDao.findByJobId(jobId);
        bioJob.setFinished(true);
        bioJobDao.save(bioJob);
    }

    private Integer getLastJobId() {
        Integer lastJobId = bioJobDao.getLastJobId();
        return lastJobId != null ? lastJobId + 1 : defaultLastJobId;
    }
}
