package service;

import biojobs.BioJobDao;
import springconfiguration.AppProperties;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by vadim on 8/14/17.
 */
public interface BioUniverseService {

    String getWorkingDir();

    String getMultipleWorkingFilesLocation();

    String getBash();

    String getPython();

    String getPrefix();
    
    String getPostfix();

    AppProperties getProperties();

    StorageService getStorageService();

    String getPathToMainDirFromBioProgs();

    String getProgram(String programName);

}
