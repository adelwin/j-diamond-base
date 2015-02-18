/*
 * File Name       : CallerAwareMailDeliveryImpl.java
 * Class Name      : CallerAwareMailDeliveryImpl
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
 * | Adelwin Handoyo | 2015-02-12 19.49 | 2.8.1   | deprecated class after implementation of Caller Aware segregation of deliverable files
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.delivery.impl;

import org.si.diamond.base.dataextraction.dao.impl.BaseExtractionDaoImpl;
import org.si.diamond.base.dataextraction.delivery.ICallerAwareBaseDelivery;
import org.si.diamond.base.dataextraction.delivery.IMailDelivery;
import org.si.diamond.base.mail.exception.BaseMailException;
import org.si.diamond.base.mail.session.MailSession;
import org.si.diamond.base.util.ExceptionUtil;
import org.si.diamond.base.dataextraction.exception.BaseExtractionRuntimeException;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Map;

@Deprecated
public class CallerAwareMailDeliveryImpl implements IMailDelivery, ICallerAwareBaseDelivery {
	
	protected static Logger logger = Logger.getLogger(CallerAwareMailDeliveryImpl.class);

	private MailSession mailSession;
	
	private String messageSubject;
	private String messageBody;
	private String messageSender;
	private Map<String, String> recipientList;
	private String attachment;
	/**
	 * @return the mailSession
	 */
	public MailSession getMailSession() {
		return mailSession;
	}
	/**
	 * @param mailSession the mailSession to set
	 */
	public void setMailSession(MailSession mailSession) {
		this.mailSession = mailSession;
	}
	/**
	 * @return the messageSubject
	 */
	public String getMessageSubject() {
		return messageSubject;
	}
	/**
	 * @param messageSubject the messageSubject to set
	 */
	public void setMessageSubject(String messageSubject) {
		this.messageSubject = messageSubject;
	}
	/**
	 * @return the messageSender
	 */
	public String getMessageSender() {
		return messageSender;
	}
	/**
	 * @param messageSender the messageSender to set
	 */
	public void setMessageSender(String messageSender) {
		this.messageSender = messageSender;
	}
	/**
	 * @return the messageBody
	 */
	public String getMessageBody() {
		return messageBody;
	}
	/**
	 * @param messageBody the messageBody to set
	 */
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	/**
	 * @return the recipientList
	 */
	public Map<String, String> getRecipientList() {
		return recipientList;
	}
	/**
	 * @param recipientList the recipientList to set
	 */
	public void setRecipientList(Map<String, String> recipientList) {
		this.recipientList = recipientList;
	}
	/**
	 * @return the attachment
	 */
	public String getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void send() {
		throw new BaseExtractionRuntimeException("Method send() not implemented");
	}

	/* (non-Javadoc)
	 * @see com.pru.pacs.base.delivery.IBaseDelivery#send()
	 */
	public void send(BaseExtractionDaoImpl caller) {
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
						message.addRecipient(RecipientType.TO, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("cc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(RecipientType.CC, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("bcc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(RecipientType.BCC, new InternetAddress(emailAddr));
					}
				}
			}

			logger.debug("creating text bodypart");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(this.getMessageBody());

			logger.debug("creating and setting multipart");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			if (this.getAttachment() != null && this.getAttachment().trim().length() != 0) {
				logger.debug("creating attachment bodypart");
				logger.debug("attachment is [" + this.getAttachment() + "]");
				MimeBodyPart attachmentBodyPart = new MimeBodyPart();

				File fileAttachment = new File(this.getAttachment());

				DataSource dataSource = new FileDataSource(fileAttachment);
				attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
				attachmentBodyPart.setFileName(fileAttachment.getName());

				multipart.addBodyPart(attachmentBodyPart);
			}

			message.setContent(multipart);

			logger.debug("sending");
			this.mailSession.send(message);
			logger.debug("sent");
			logger.debug("disconnecting");
			this.mailSession.disconnect();
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

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageContent) {
		try {
			logger.debug("Refreshing mail session connection");
			this.getMailSession().connect();

			logger.debug("Creating Mime Message object");
			MimeMessage message = new MimeMessage(this.getMailSession().getMailSession());

			logger.debug("Setting message properties");
			message.setSubject("[ERROR] " + this.getMessageSubject());
			message.setSender(new InternetAddress(this.getMessageSender()));

			logger.debug("setting message recipient");
			for (String key : this.recipientList.keySet()) {
				if (key.equalsIgnoreCase("to")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(RecipientType.TO, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("cc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(RecipientType.CC, new InternetAddress(emailAddr));
					}
				}
				if (key.equalsIgnoreCase("bcc")) {
					String value = this.recipientList.get(key);
					String values[] = value.split(";");
					for (String emailAddr : values) {
						message.addRecipient(RecipientType.BCC, new InternetAddress(emailAddr));
					}
				}
			}

			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(messageContent);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			message.setContent(multipart);

			this.mailSession.send(message);
			this.mailSession.disconnect();
		} catch (BaseMailException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageSubject, String messageContent, Exception exception) {
		StringBuffer messageBody = new StringBuffer(messageContent);
		messageBody.append("Exception Occurred.\nPlease see below for the stack trace of the exception\n");
		messageBody.append("\n");

		logger.debug("retrieving stack trace of the exception");
		String stackTrace = ExceptionUtil.getStackTraces(exception);
		logger.debug("stack trace of the exception is retrieved");

		logger.debug("setting stack trace of the exception as the message body");
		messageBody.append(stackTrace);

		if (this.getMessageSubject() == null || this.getMessageSubject().trim().length() == 0) {
			this.setMessageSubject(messageSubject);
		} else {
			this.setMessageSubject(this.getMessageSubject() + " - [" + messageSubject + "]");
		}

		this.sendError(caller, messageBody.toString());
	}
}
