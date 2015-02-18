/*
 * File Name       : StreamLogger.java
 * Class Name      : StreamLogger
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

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by adelwin.handoyo on 2014-07-01.
 */
public class StreamLogger extends Thread {
	protected static Logger logger = Logger.getLogger(StreamLogger.class);
	private Logger outputLogger;
	private InputStream inputStream;
	private String loggerName;

	public Logger getOutputLogger() {
		return outputLogger;
	}

	public void setOutputLogger(Logger outputLogger) {
		this.outputLogger = outputLogger;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public StreamLogger(InputStream inputStream, Logger logger) {
		this.setLoggerName(loggerName);
		this.setOutputLogger(logger);
		this.setInputStream(inputStream);
	}

	public void run() {
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(this.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (this.getOutputLogger() == null) {
					throw new IllegalStateException("output logger not defined");
				}else {
					this.getOutputLogger().info(line);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
