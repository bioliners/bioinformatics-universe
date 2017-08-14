package service;

import serviceimpl.AppProperties;

/**
 * Created by vadim on 8/14/17.
 */
public interface BioUniverseService {

    String getWorkingDir();

    String getMultipleWorkingFilesLocation();

    String getBash();

    String getPython();

    String getPrefix();

    AppProperties getProperties();

    StorageService getStorageService();

}
