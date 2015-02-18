/*
 * File Name       : StreamGobbler.java
 * Class Name      : StreamGobbler
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:04
 *
 * Copyright (C) 2014 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.gobbler;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author tmpadh
 * This class will basically just redirect a known stream to a parameterized file.
 * InputStream is the stream to redirect
 * gobblerName is an indentifier for the current gobbler.
 * outputFileName will be the result of the redirected stream
 */
public class StreamGobbler extends Thread {
	private InputStream inputStream;
	private String gobblerName;
	private String outputFileName;
	
	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	/**
	 * @return the name
	 */
	public String getGobblerName() {
		return gobblerName;
	}
	/**
	 * @param name the name to set
	 */
	public void setGobblerName(String gobblerName) {
		this.gobblerName = gobblerName;
	}
	/**
	 * @return the outputFileName
	 */
	public String getOutputFileName() {
		return outputFileName;
	}
	/**
	 * @param outputFileName the outputFileName to set
	 */
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	
	public StreamGobbler(InputStream inputStream, String gobblerName, String outputFileName) {
		this.setInputStream(inputStream);
		this.setGobblerName(gobblerName);
		this.setOutputFileName(outputFileName);
	}
	
	public void run() {
		try {
			PrintWriter printWriter = null;
			InputStreamReader inputStreamReader = new InputStreamReader(this.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (printWriter == null) {
					FileOutputStream fileOutputStream = new FileOutputStream(this.getOutputFileName());
					printWriter = new PrintWriter(fileOutputStream);
				}
				printWriter.println(line);
				printWriter.flush();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
