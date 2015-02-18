/*
 * File Name       : IMailStore.java
 * Class Name      : IMailStore
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

package org.si.diamond.base.mail.store;

import org.si.diamond.base.mail.exception.BaseMailException;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.search.SearchTerm;
import java.util.Properties;

public interface IMailStore {
	/**
	 * the common configuration key in the properties file
	 */
	public static final String MAIL_SMTP_HOST_KEY = "mail.smtp.host";

	public static final String MAIL_SMTP_USER_KEY = "mail.smtp.user";

	public static final String MAIL_SMTP_PORT_KEY = "mail.smtp.port";

	public static final String MAIL_SMTP_CONNECTIONTIMEOUT_KEY = "mail.smtp.connectiontimeout";

	public static final String MAIL_SMTP_FROM_KEY = "mail.smtp.from";

	public static final String MAIL_DEBUG_KEY = "mail.debug";

	public static final String MAIL_IMAP_HOST = "mail.imap.host";

	public static final String MAIL_IMAP_PORT = "mail.imap.port";

	public static final String MAIL_IMAP_USER = "mail.imap.user";
	
	public static final String MAIL_IMAP_STORE_PROTOCOL = "mail.store.protocol";

	public static final String MAIL_IMAP_SOCKETFACTORY_CLASS = "mail.imap.socketFactory.class";

	public static final String MAIL_IMAP_SOCKETFACTORY_FALLBACK = "mail.imap.socketFactory.fallback";

	public static final String DEFAULT_MAIL_IMAP_PORT = "993";

	public static final String DEFAULT_MAIL_IMAP_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";

	public static final String DEFAULT_MAIL_IMAP_SOCKETFACTORY_FALLBACK = "false";
	
	public static final String DEFAULT_MAIL_MAP_STORE_PROTOCOL = "imap";

	public abstract String getIdentifier();

	public abstract Properties getMailConfigurationProperties();

	public abstract Session getMailSession();

	/**
	 * Method Name : connect
	 * 
	 * @return void
	 * @author ahandoyo
	 */
	public abstract void connect() throws BaseMailException;

	/**
	 * Method Name : connect(String, String)
	 * 
	 * @return void
	 * @author ahandoyo
	 * @param smtpUser
	 * @param smtpPassword
	 */
	public abstract void connect(String smtpUser, String smtpPassword) throws BaseMailException;

	/**
	 * Method Name : disconnect
	 * 
	 * @return void
	 * @author ahandoyo
	 */
	public abstract void disconnect() throws BaseMailException;

	/**
	 * Method Name : reconnect(void)
	 * 
	 * @author ahandoyo
	 * @param mailMessage
	 * @return void
	 */
	public abstract void reconnect() throws BaseMailException;
	
	public abstract Message[] getMessages(int mode, String folderName) throws BaseMailException;
	public abstract Message[] getMessages(int mode, String folderName, SearchTerm searchTerm) throws BaseMailException;
	
}
