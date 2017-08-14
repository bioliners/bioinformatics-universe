package model.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class EvolutionRequest {
	private List<MultipartFile> listOfFiles;
	private String fileDelim;
	private String fileColumn;
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
	

}
