/*
 * File Name       : MailSendingStatusReporterTask.java
 * Class Name      : MailSendingStatusReporterTask
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

package org.si.diamond.base.mail.task;

import org.si.diamond.base.mail.exception.BaseMailException;
import org.si.diamond.base.mail.session.MailSession;
import org.si.diamond.base.task.BaseTask;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Map;

/**
 * Created by adelwin.handoyo on 2014-07-10.
 */
public abstract class MailSendingStatusReporterTask implements BaseTask {
	protected static Logger logger = Logger.getLogger(MailSendingStatusReporterTask.class);

	private MailSession mailSession;

	private String messageSubject;
	private String messageSender;
	private Map<String, String> recipientList;

	public MailSession getMailSession() {
		return mailSession;
	}

	public void setMailSession(MailSession mailSession) {
		this.mailSession = mailSession;
	}

	public String getMessageSubject() {
		return messageSubject;
	}

	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}

	public String getMessageSender() {
		return messageSender;
	}

	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}

	public Map<String, String> getRecipientList() {
		return recipientList;
	}

	public void setRecipientList(Map<String, String> recipientList) {
		this.recipientList = recipientList;
	}

	public final void sendStatus(String messageContent) {
		try {
			logger.debug("Refreshing mail session connection");
			this.getMailSession().connect();

			logger.debug("Creating Mime Message object");
			MimeMessage message = new MimeMessage(this.getMailSession().getMailSession());

			logger.debug("Setting message properties");
			message.setSubject(this.getMessageSubject());
			message.setSender(new InternetAddress(this.getMessageSender()));

			logger.debug("setting message recipient");
			for (String key : this.recipientList.keySet()) {
				if (key.equalsIgnoreCase("to")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("cc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("bcc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(emailAddr));
					}
				}
			}

			logger.debug("setting text bodypart");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(messageContent);

			logger.debug("creating and setting multipart");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			message.setContent(multipart);

			logger.debug("sending");
			this.getMailSession().send(message);
			logger.debug("sent");
			logger.debug("disconnecting");
			this.getMailSession().disconnect();
			logger.debug("disconnected");
		} catch (BaseMailException e) {
			logger.error("error during mail sending");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (MessagingException e) {
			logger.error("error during mail sending");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public final void sendStatus(Exception e) {
		StringBuffer stackTraces = new StringBuffer();
		for (StackTraceElement element : e.getStackTrace()) {
			stackTraces.append(element.toString());
		}
		this.sendStatus(stackTraces.toString());
	}
}
