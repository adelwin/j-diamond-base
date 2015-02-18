/*
 * File Name       : MailListenerThread.java
 * Class Name      : MailListenerThread
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

package org.si.diamond.base.mail.thread;

import org.si.diamond.base.mail.exception.BaseMailException;

import java.io.InputStream;


/**
 * This Interface will govern the way a thread in this module works.<br>
 * this interface will force any implementing object to be threads, <br>
 * 
 * Type Name : MailListenerThread Package : COTSMailProject Author :
 * adelwin.handoyo Created : AM 10:55:29
 */
public interface MailListenerThread {

	/**
	 * Method Name : load(String) this method will load the specific
	 * configuration file for this thread <BR>
	 * one mail session instance for one thread.
	 * 
	 * @param messageFileName
	 * @return void
	 * @author adelwin.handoyo
	 */
	public void load(String configurationFileName) throws BaseMailException;

	/**
	 * Method Name : load(File) this method is a more specific method that takes
	 * a parameter of a file object<BR>
	 * 
	 * @param messageFile
	 * @return void
	 * @author adelwin.handoyo
	 */
	public void load(InputStream configurationFile) throws BaseMailException;

	/**
	 * Method Name : init(void) this method is to initialise this thread, set
	 * the listening directory, authenticate mail session or anything else
	 * 
	 * @param void
	 * @return void
	 * @author adelwin.handoyo
	 */
	public void init() throws BaseMailException;

	/**
	 * Method Name : destroy(void) this method is to finalise the thread after
	 * use, kill the file object that listens to a directory, disconnect mail
	 * session or anything else
	 * 
	 * @param void
	 * @return void
	 * @author adelwin.handoyo
	 */
	public void destroy() throws BaseMailException;

	/**
	 * Method Name : start(void) this method is to start the thread after init,
	 * 
	 * @param void
	 * @return void
	 * @author adelwin.handoyo
	 */
	public void start() throws BaseMailException;
}
