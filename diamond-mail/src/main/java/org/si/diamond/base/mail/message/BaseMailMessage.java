/*
 * File Name       : BaseMailMessage.java
 * Class Name      : BaseMailMessage
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

package org.si.diamond.base.mail.message;

import org.si.diamond.base.mail.exception.BaseMailException;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * This class will be the base class of all COTS Mail Message. This class will
 * have the most common
 * 
 * @author ahandoyo
 */
public abstract class BaseMailMessage extends MimeMessage {

	public BaseMailMessage(Session _session) {
		super(_session);
	}

	public abstract void load(String messageFileName) throws BaseMailException;

	public abstract void load(File messageFile) throws BaseMailException;
}
