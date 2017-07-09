package model;

import org.springframework.web.multipart.MultipartFile;

public class Sequence {
	private MultipartFile file;
	private String firstFileDelim;
	private String firstFileColumn;
	private String secondFileDelim;	
	private String secondFileColumn;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
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
