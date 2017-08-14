package model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EvolutionRequest {
	private List<MultipartFile> listOfFiles;
	private String fileDelim;
	private String fileColumn;
	private String identityThreshold;
	private String coverageThreshold;
	private String evalueThreshold;
	private String commandToBeProcessedBy;

	
	public List<MultipartFile> getListOfFiles() {
		return listOfFiles;
	}

	public void setListOfFiles(List<MultipartFile> listOfFiles) {
		this.listOfFiles = listOfFiles;
	}

	public String getFileDelim() {
		return fileDelim;
	}

	public void setFileDelim(String fileDelim) {
		this.fileDelim = fileDelim;
	}

	public String getFileColumn() {
		return fileColumn;
	}

	public void setFileColumn(String fileColumn) {
		this.fileColumn = fileColumn;
	}

	public String getCommandToBeProcessedBy() {
		return commandToBeProcessedBy;
	}

	public void setCommandToBeProcessedBy(String commandToBeProcessedBy) {
		this.commandToBeProcessedBy = commandToBeProcessedBy;
	}

	public String getIdentityThreshold() {
		return identityThreshold;
	}

	public void setIdentityThreshold(String identityThreshold) {
		this.identityThreshold = identityThreshold;
	}

	public String getCoverageThreshold() {
		return coverageThreshold;
	}

	public void setCoverageThreshold(String coverageThreshold) {
		this.coverageThreshold = coverageThreshold;
	}

	public String getEvalueThreshold() {
		return evalueThreshold;
	}

	public void setEvalueThreshold(String evalueThreshold) {
		this.evalueThreshold = evalueThreshold;
	}
}
