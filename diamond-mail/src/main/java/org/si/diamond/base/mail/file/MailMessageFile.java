/*
 * File Name       : MailMessageFile.java
 * Class Name      : MailMessageFile
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

public interface MailMessageFile {
	public void load(String messageFileName) throws BaseMailException;
	public boolean hasNextMessage() throws BaseMailException;
	public String nextMessage() throws BaseMailException;
}
