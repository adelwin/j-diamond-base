/*
 * File Name       : UTF8MailMessageFile.java
 * Class Name      : UTF8MailMessageFile
 * Module Name     : pacs-mail
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:05
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

package org.si.diamond.base.mail.file;

import org.si.diamond.base.mail.exception.BaseMailException;
import org.apache.log4j.Logger;

import java.io.*;


/**
 * this class will hold the mail message file this class will load the file and
 * parse it line by line as a UTF 8 file and pass the read line into MailMessage
 * class to process it into an email. <code>
 * 		MailMessageFile messageFile = new UTF8MessageFile("input/inputFile.msg");
 * 		while (messageFile.hasNextMessage()) {
 * 			MimeMessage message = new PipeSeparatedCOTSMailMessage();
 * 			String messageLine = messageFile.nextMessage();
 * 			message.load(messageLine);
 * 		}  
 * </code>
 * 
 * @author ahandoyo
 */
public class UTF8MailMessageFile implements MailMessageFile {

	private static final Logger	logger	= Logger.getLogger(UTF8MailMessageFile.class);

	private static final String	CHARSET	= "UTF-8";

	private BufferedReader		bufferedReader;

	private String				nextLine;

	public void load(String messageFileName) throws BaseMailException {
		try {
			File messageFile = new File(messageFileName);
			InputStream fileInputStream = new FileInputStream(messageFile);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, CHARSET);
			this.bufferedReader = new BufferedReader(inputStreamReader);
		} catch (FileNotFoundException e) {
			logger.error("UTF8MailMessageFile.load() :: FileNotFoundException occured :: " + e.getMessage(), e);
			throw new BaseMailException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error("UTF8MailMessageFile.load() :: UnsupportedEncodingException occured :: " + e.getMessage(), e);
			throw new BaseMailException(e.getMessage(), e);
		}
	}

	public boolean hasNextMessage() throws BaseMailException {
		if (this.nextLine == null || this.nextLine.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public String nextMessage() throws BaseMailException {
		try {
			String tmpNextLine = this.bufferedReader.readLine();
			String returnVal = this.nextLine;
			this.nextLine = tmpNextLine;
			return returnVal;
		} catch (IOException e) {
			logger.error("UTF8MailMessageFile.load() :: IOException occured :: " + e.getMessage(), e);
			throw new BaseMailException(e.getMessage(), e);
		}
	}

}
