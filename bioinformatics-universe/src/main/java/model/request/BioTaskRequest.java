package model.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class BioTaskRequest {
    private List<String> listOfFilesParamPrefixes;
    private List<MultipartFile> listOfFiles;
    private Map<String, List<String>> parameters;
    private String subTab;

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

    public List<String> getListOfFilesParamPrefixes() {
        return listOfFilesParamPrefixes;
    }

    public void setListOfFilesParamPrefixes(List<String> listOfFilesParamPrefixes) {
        this.listOfFilesParamPrefixes = listOfFilesParamPrefixes;
    }
}
