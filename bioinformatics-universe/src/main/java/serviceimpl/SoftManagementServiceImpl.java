package serviceimpl;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import enums.DropOperationStatus;
import model.request.BioDropRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.SoftManagementService;
import service.StorageService;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SoftManagementServiceImpl implements SoftManagementService {

    private final String defaultTab = "otherPrograms";
    private final BioDropDao bioDropDao;
    private final StorageService storageService;

    @Autowired
    public SoftManagementServiceImpl(BioDropDao bioDropDao, StorageService storageService) {
        this.bioDropDao = bioDropDao;
        this.storageService = storageService;
    }


    @Override
    public String dropProgram(BioDropRequest bioDropRequest) throws IOException {
        DropOperationStatus status = saveProgram(bioDropRequest);

        /*if (status.equals(DropOperationStatus.OK)) {
            installDependencies(bioDropRequest);
            installInterpreter(bioDropRequest);
        }*/

        return status.getStatus();
    }


    private DropOperationStatus saveProgram(BioDropRequest bioDropRequest) throws IOException {
        BioDrop bioDrop;
        bioDrop = bioDropDao.findBySubTab(bioDropRequest.getSubTab());
        if (bioDrop != null) {
            return DropOperationStatus.SUBTAB_EXISTS;
        }

        StringBuilder programNameBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (bioDropRequest.getProgram().getInputStream(), Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                programNameBuilder.append((char) c);
            }
        }

        bioDrop = bioDropDao.findByProgram(programNameBuilder.toString());
        if (bioDrop != null) {
            return DropOperationStatus.PROGRAM_EXISTS;
        }

        BioDrop bioDropNew = new BioDrop();
        bioDropNew.setLongRunning(bioDropRequest.isLongRunning());
        bioDropNew.setProgram(programNameBuilder.toString());
        bioDropNew.setProgramName(!Objects.equals(bioDrop.getProgramName(), bioDropRequest.getProgramName()) ? bioDropRequest.getProgramName() : bioDropRequest.getProgramName()+"_"+UUID.randomUUID().toString());
        bioDropNew.setTab(bioDropRequest.getTab() != null ? bioDropRequest.getTab() : defaultTab);
        bioDropNew.setSubTab(bioDropRequest.getSubTab());
        bioDropNew.setProgramLanguage(bioDropRequest.getProgramLanguage());
        bioDropNew.setProgramParameters(bioDropRequest.getProgramParameters());
        bioDropNew.setProgramDependencies(bioDropRequest.getProgramDependencies());
        bioDropNew.setProgramInstallInstructs(bioDropRequest.getProgramInstallInstructs());

        storageService.storeProgram(bioDropRequest.getProgram(), bioDropNew.getProgramName());
        bioDropDao.save(bioDropNew);

        return DropOperationStatus.OK;
    }


    //TODO
    /*private void installDependencies(BioDropRequest bioDropRequest) {

    }

    private void installInterpreter(BioDropRequest bioDropRequest) {

    }*/
}
