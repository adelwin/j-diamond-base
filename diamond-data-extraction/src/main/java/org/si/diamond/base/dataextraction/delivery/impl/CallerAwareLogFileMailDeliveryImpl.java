/*
 * File Name       : CallerAwareLogFileMailDeliveryImpl.java
 * Class Name      : CallerAwareLogFileMailDeliveryImpl
 * Module Name     : pacs-data-extraction
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-02-12 19:52:58
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-02-12 19.49 | 2.8.1   | Refactor classes for mail delivery to segregate the files being sent to mail
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.delivery.impl;

import org.si.diamond.base.dataextraction.dao.impl.BaseExtractionDaoImpl;
import org.si.diamond.base.dataextraction.delivery.ICallerAwareBaseDelivery;
import org.si.diamond.base.util.ExceptionUtil;

public class CallerAwareLogFileMailDeliveryImpl extends MailDeliveryImpl implements ICallerAwareBaseDelivery {

	@Override
	public void send(BaseExtractionDaoImpl caller) {
		this.setAttachment(caller.getFinalLogFileName());
		this.send();
	}

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageContent) {
		this.setAttachment(caller.getFinalLogFileName());
		this.setMessageBody(messageContent);
		this.send();
	}

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageSubject, String messageContent, Exception exception) {
		StringBuffer messageBody;
		if (messageContent == null) {
			messageBody = new StringBuffer("");
		} else {
			messageBody = new StringBuffer(messageContent);
		}

		messageBody.append("Exception Occurred.\nPlease see below for the stack trace of the exception\n");
		messageBody.append("\n");

		messageBody.append("Exception Message: " + exception.getMessage() + "\n");
		logger.debug("retrieving stack trace of the exception");
		String stackTrace = ExceptionUtil.getStackTraces(exception);
		logger.debug("stack trace of the exception is retrieved");

		logger.debug("setting stack trace of the exception as the message body");
		messageBody.append(stackTrace);

		if (this.getMessageSubject() == null || this.getMessageSubject().trim().length() == 0) {
			this.setMessageSubject(messageSubject);
		} else {
			this.setMessageSubject("[ERROR]" + this.getMessageSubject() + " - [" + messageSubject + "]");
		}

		this.sendError(caller, messageBody.toString());
	}
}
