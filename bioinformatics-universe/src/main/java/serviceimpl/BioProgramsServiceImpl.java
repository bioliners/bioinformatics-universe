package serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.BioProgramsService;
import springconfiguration.AppProperties;
import enums.BioPrograms;

import java.util.HashMap;
import java.util.Map;

@Service
public class BioProgramsServiceImpl implements BioProgramsService {

    private final AppProperties properties;
    private final Map<String, String> programs = new HashMap<>();

    @Autowired
    public BioProgramsServiceImpl(AppProperties properties) {
        this.properties = properties;

        programs.put(BioPrograms.CREATE_COGS.getProgramName(), properties.getCreateCogsProgram());
        programs.put(BioPrograms.MAKE_UNIQUE.getProgramName(), properties.getMakeUniqueProgram());
        programs.put(BioPrograms.GET_SEQ_BYNAME.getProgramName(), properties.getGetSeqByNameProgram());
    }

    @Override
    public String getProgram(String programName) {
        return programs.get(programName);
    }


}
