package model.internal;

public class SequenceInternal {
	private String firstFileName;
	private String secondFileName;
	private String firstFileDelim = "";
	private String firstFileColumn = "";
	private String secondFileDelim = "";
	private String secondFileColumn = "";
	
	public String getFirstFileName() {
		return firstFileName;
	}
	public void setFirstFileName(String firstFileName) {
		this.firstFileName = firstFileName;
	}
	public String getSecondFileName() {
		return secondFileName;
	}
	public void setSecondFileName(String secondFileName) {
		this.secondFileName = secondFileName;
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
