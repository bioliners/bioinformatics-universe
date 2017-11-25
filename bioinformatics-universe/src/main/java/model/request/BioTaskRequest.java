package model.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class BioTaskRequest {
    private List<MultipartFile> listOfFiles;
    private List<String> listOfFilesParamPrefixes;
    private Map<String, List<String>> parameters;
    private String subTab;
    private String isBatch;

    public List<MultipartFile> getListOfFiles() {
        return listOfFiles;
    }

    public void setListOfFiles(List<MultipartFile> listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

    public Map<String, List<String>> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, List<String>> parameters) {
        this.parameters = parameters;
    }

    public String getSubTab() {
        return subTab;
    }

    public void setSubTab(String subTab) {
        this.subTab = subTab;
    }

    public String getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(String isBatch) {
        this.isBatch = isBatch;
    }

    public List<String> getListOfFilesParamPrefixes() {
        return listOfFilesParamPrefixes;
    }

    public void setListOfFilesParamPrefixes(List<String> listOfFilesParamPrefixes) {
        this.listOfFilesParamPrefixes = listOfFilesParamPrefixes;
    }
}
