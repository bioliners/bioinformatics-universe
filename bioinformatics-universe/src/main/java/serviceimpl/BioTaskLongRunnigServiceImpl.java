package serviceimpl;

import biojobs.*;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
import model.request.BioTaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import service.BioTaskLongRunnigService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class BioTaskLongRunnigServiceImpl extends BioDropServiceImpl implements BioTaskLongRunnigService {
    @Autowired
    final BioJobDao bioJobDao;
    @Autowired
    final BioJobResultDao bioJobResultDao;

    private final int defaultLastJobId = 1;


    public BioTaskLongRunnigServiceImpl(final BioDropDao bioDropDao, final StorageService storageService,
                                        final AppProperties properties, final BioJobDao bioJobDao, final BioJobResultDao bioJobResultDao) {
        super(bioDropDao, storageService, properties);
        this.bioJobDao = bioJobDao;
        this.bioJobResultDao = bioJobResultDao;
    }


    @Override
    public BioTaskRequestInternal storeFilesAndPrepareCommandArguments (final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
        BioDrop bioDrop = super.checkInputAndGetBioDrop(bioTaskRequest);
        BioTaskRequestInternal bioTaskRequestInternal = super.prepareCommandArguments(bioTaskRequest, bioDrop);
        Integer jobId = saveBioJobToDB(bioDrop.getProgramName(), bioTaskRequestInternal.getOutputFileNames());
        bioTaskRequestInternal.setJobId(jobId);
        return bioTaskRequestInternal;
    }

    @Override
    @Async
    public void runProgram(final BioTaskRequestInternal bioTaskRequestInternal) throws IncorrectRequestException {
        super.launchProcess(bioTaskRequestInternal.getCommandArguments());
        for (String fileName : bioTaskRequestInternal.getOutputFileNames()) {
            saveResultFileToDB(fileName, bioTaskRequestInternal.getJobId());
        }
    }

    @Override
    public BioJob getBioJobIfFinished(int jobId) {
        BioJob bioJob = bioJobDao.findByJobId(jobId);
        return bioJob.isFinished() ? bioJob : null;
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
