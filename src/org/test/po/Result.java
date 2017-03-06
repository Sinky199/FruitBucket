package org.test.po;

public class Result {
	private int code;
	private String message;
	public Result(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
