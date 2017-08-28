package model.request;

import java.util.List;

import javafx.beans.binding.DoubleExpression;
import org.springframework.web.multipart.MultipartFile;

public class EvolutionRequest {
	private List<MultipartFile> listOfFiles;
	private String fileDelim;
	private Integer fileColumn;
	private Double identityThreshold;
	private Double coverageThreshold;
	private Double evalueThreshold;
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

	public Integer getFileColumn() {
		return fileColumn;
	}

	public void setFileColumn(Integer fileColumn) {
		this.fileColumn = fileColumn;
	}

	public String getCommandToBeProcessedBy() {
		return commandToBeProcessedBy;
	}

	public void setCommandToBeProcessedBy(String commandToBeProcessedBy) {
		this.commandToBeProcessedBy = commandToBeProcessedBy;
	}

	public Double getIdentityThreshold() {
		return identityThreshold;
	}

	public void setIdentityThreshold(Double identityThreshold) {
		this.identityThreshold = identityThreshold;
	}

	public Double getCoverageThreshold() {
		return coverageThreshold;
	}

	public void setCoverageThreshold(Double coverageThreshold) {
		this.coverageThreshold = coverageThreshold;
	}

	public Double getEvalueThreshold() {
		return evalueThreshold;
	}

	public void setEvalueThreshold(Double evalueThreshold) {
		this.evalueThreshold = evalueThreshold;
	}
}
