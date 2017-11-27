package serviceimpl;

import biojobs.BioDrop;
import biojobs.BioDropDao;
import exceptions.IncorrectRequestException;
import model.internal.BioTaskRequestInternal;
import model.request.BioTaskRequest;
import org.springframework.stereotype.Service;
import service.BioTaskService;
import service.StorageService;
import springconfiguration.AppProperties;

import java.util.List;

@Service
public class BioTaskServiceImpl extends BioDropServiceImpl implements BioTaskService {

    public BioTaskServiceImpl(final BioDropDao bioDropDao, final StorageService storageService, final AppProperties properties) {
        super(bioDropDao, storageService, properties);
    }

    @Override
    public List<String> runProgram(final BioTaskRequest bioTaskRequest) throws IncorrectRequestException {
        BioDrop bioDrop = super.checkInputAndGetBioDrop(bioTaskRequest);
        BioTaskRequestInternal bioTaskRequestInternal = super.prepareCommandArguments(bioTaskRequest, bioDrop);
        super.launchProcess(bioTaskRequestInternal.getCommandArguments());
        return bioTaskRequestInternal.getOutputFileNames();
    }

}

