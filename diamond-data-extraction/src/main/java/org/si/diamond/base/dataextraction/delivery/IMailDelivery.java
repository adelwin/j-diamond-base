/*
 * File Name       : IMailDelivery.java
 * Class Name      : IMailDelivery
 * Module Name     : pacs-data-extraction
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

package org.si.diamond.base.dataextraction.delivery;

import org.si.diamond.base.mail.session.MailSession;

import java.util.Map;


public interface IMailDelivery extends IBaseDelivery {
	public void setMailSession(MailSession mailSession);
	public void setMessageSubject(String messageSubject);
	public void setMessageBody(String messageBody);
	public void setRecipientList(Map<String, String> recipientList);
	public void setAttachment(String attachment);
}
