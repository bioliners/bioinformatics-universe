package model.request;

import org.springframework.web.multipart.MultipartFile;

public class SequenceRequest {
	private MultipartFile firstFile;
	private String firstFileDelim;
	private int firstFileColumn;
	
	private MultipartFile secondFile;
	private String secondFileDelim;	
	private int secondFileColumn;
	
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
	public int getFirstFileColumn() {
		return firstFileColumn;
	}
	public void setFirstFileColumn(int firstFileColumn) {
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
	public int getSecondFileColumn() {
		return secondFileColumn;
	}
	public void setSecondFileColumn(int secondFileColumn) {
		this.secondFileColumn = secondFileColumn;
	}

}
