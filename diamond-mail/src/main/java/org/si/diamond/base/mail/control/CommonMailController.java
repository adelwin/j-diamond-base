/*
 * File Name       : CommonMailController.java
 * Class Name      : CommonMailController
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

package org.si.diamond.base.mail.control;

import org.si.diamond.base.mail.exception.BaseMailException;
import org.si.diamond.base.mail.thread.BaseMailListernerThread;
import org.si.diamond.base.mail.thread.MailListenerThread;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class CommonMailController {

	private static Logger				logger					= Logger.getLogger(CommonMailController.class);

	private static final String			configurationFileName	= "mail-service.properties";

	private Properties					mailServiceProperties	= new Properties();

	private List<MailListenerThread>	threadList				= new ArrayList<MailListenerThread>();

	/**
	 * This method is the default constructor. <br>
	 * it will load the main configuration file
	 * <code>mail-service.properties</code> and build an appropriate object.<br>
	 * The configuration file should consist of a list of all other specific
	 * configuration file for each SMTP Server.<br>
	 * This constructor will iterate the key and build all of the child
	 * MailListener object as a thread.<br>
	 * 
	 * @throws org.si.diamond.base.mail.exception.BaseMailException
	 * @author adelwin.handoyo
	 */
	public CommonMailController() throws BaseMailException {
		try {
			// init configuration file
			logger.info("CommonMailController(void) :: Creating Mail Controller Object");

			logger.info("CommonMailController(void) :: Opening Configuration File");
			URL configFileUrl = ClassLoader.getSystemResource(configurationFileName);
			this.mailServiceProperties.load(configFileUrl.openStream());
			logger.info("CommonMailController(void) :: Configuration File Loaded");

			// create thread objects for each config
			logger.info("CommonMailController(void) :: Iterating each key");
			Set<Object> keySet = mailServiceProperties.keySet();
			Iterator<Object> keySetIterator = keySet.iterator();
			while (keySetIterator.hasNext()) {
				String key = (String) keySetIterator.next();
				String value = mailServiceProperties.getProperty(key);

				logger.info("CommonMailController(void) :: " + key + " :: Loading specific mail configuration file = " + value);
				MailListenerThread thread = new BaseMailListernerThread();

				logger.info("CommonMailController(void) :: " + key + " :: Registering thread");
				this.threadList.add(thread);

				logger.info("CommonMailController(void) :: " + key + " :: Initializing thread");
				thread.load(value);
				thread.init();
				logger.info("CommonMailController(void) :: " + key + " :: Thread initialized");
			}
		} catch (IOException e) {
			logger.error("CommonMailController(void) :: IOException occured :: " + e.getMessage(), e);
			throw new BaseMailException(e.getMessage(), e);
		}
	}

	public void execute() {
		Iterator<MailListenerThread> threadListIterator = threadList.iterator();
		while (threadListIterator.hasNext()) {
			Thread thread = (Thread) threadListIterator.next();
			logger.info("CommonMailController.dispose(void) :: " + thread.getName() + " :: Thread Starting");
			thread.start();
		}
	}

	public void dispose() {
		Iterator<MailListenerThread> threadListIterator = threadList.iterator();
		while (threadListIterator.hasNext()) {
			MailListenerThread thread = threadListIterator.next();
			logger.info("CommonMailController.dispose(void) :: " + ((Thread) thread).getName() + " :: Disposing thread");
			try {
				thread.destroy();
			} catch (BaseMailException e) {
				logger.error("CommonMailController(void) :: GenericMailException occured :: " + e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * The default entry point to this library, if there's no other accessor
	 * class. <br>
	 * Mail Controller class is the default main class of this library.<br>
	 * If this library is not wrapped by another accessor class or method, then
	 * this "main" method will be the entry point providing the default
	 * mechanism to load the library and get it running.
	 * 
	 * @param args
	 *            the default java main method parameter
	 */
	public static void main(String args) {
		try {
			CommonMailController mailController = new CommonMailController();
			mailController.execute();
		} catch (BaseMailException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
