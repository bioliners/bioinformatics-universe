package model.request;

import org.springframework.web.multipart.MultipartFile;

public class SequenceRequest {
	private MultipartFile firstFile;
	private String firstFileDelim;
	private String firstFileColumn;
	
	private MultipartFile secondFile;
	private String secondFileDelim;	
	private String secondFileColumn;
	
	public MultipartFile getFirstFile() {
		return firstFile;
	}
	public void setFirstFile(MultipartFile file) {
		this.firstFile = file;
	}
	public String getFirstFileDelim() {
		return firstFileDelim;
	}
	public void setFirstFileDelim(String firstFileDelim) {
		this.firstFileDelim = firstFileDelim;
	}
	public String getFirstFileColumn() {
		return firstFileColumn;
	}
	public void setFirstFileColumn(String firstFileColumn) {
		this.firstFileColumn = firstFileColumn;
	}
	
	public MultipartFile getSecondFile() {
		return secondFile;
	}
	public void setSecondFile(MultipartFile secondFile) {
		this.secondFile = secondFile;
	}
	public String getSecondFileDelim() {
		return secondFileDelim;
	}
	public void setSecondFileDelim(String secondFileDelim) {
		this.secondFileDelim = secondFileDelim;
	}
	public String getSecondFileColumn() {
		return secondFileColumn;
	}
	public void setSecondFileColumn(String secondFileColumn) {
		this.secondFileColumn = secondFileColumn;
	}

}
