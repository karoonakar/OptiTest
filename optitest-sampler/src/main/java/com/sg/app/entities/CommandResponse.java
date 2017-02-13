package com.sg.app.entities;
/**
 * @author Karoonakar
 *
 */

public class CommandResponse {
	
	private int errorString;
	private String outputString;
	private String command;
	
	
	public int getErrorString() {
		return errorString;
	}
	public void setErrorString(int errorString) {
		this.errorString = errorString;
	}
	public String getOutputString() {
		return outputString;
	}
	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
}

