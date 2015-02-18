/*
 * File Name       : MailUtil.java
 * Class Name      : MailUtil
 * Module Name     : pacs-mail
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-07-22 21:32:45
 *
 * Copyright (C) 2014 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2014-07-22 16:39 | 2.0.1   | Add new feature to send email of exception
 * |                 |                  |         | - add new class exception util to mine the stack traces and/or manipulation of it
 * |                 |                  |         | - minor fix on sendErrorMail method during initialization of message body buffer
 * |                 |                  | 2.1.0   | Add new feature to send email with attachment
 * |                 | 2014-10-13 22:05 | 2.3.0   | Add new feature to toggle if mail util is enabled or not
 * |                 | 2014-12-01 20:11 | 2.6.0   | Add levels or types to email to preserve the originally set subject
 * |                 |                  |         | To set the input mail body to append the originally set mail body
 * |                 |                  |         | Removing attachment inner member, this value needs to always be supplied by the caller
 * |                 |                  |         | Attachment parameter needs to be set as file, not string of filename
 * |                 | 2015-02-10 16:38 | 2.8.0   | fix print parameter for to, cc and bcc, line 384
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.mail.util;

import org.si.diamond.base.mail.exception.BaseMailException;
import org.si.diamond.base.mail.session.IMailSession;
import org.si.diamond.base.util.ExceptionUtil;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adelwin.handoyo on 2014-07-10.
 */
public class MailUtil {
	protected static Logger logger = Logger.getLogger(MailUtil.class);
	private String sender;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	@Deprecated
	private String attachment;
	private IMailSession mailSession;
	private boolean enabled;

	public enum Level {
		INFO	("INFO"),
		WARNING	("WARNING"),
		ERROR	("ERROR"),
		FATAL	("FATAL");

		private String level;

		private Level(String level) {
			this.level = level;
		}

		@Override
		public String toString() {
			return "[" + this.level + "]";
		}
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Deprecated
	public String getAttachment() {
		return attachment;
	}

	@Deprecated
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public IMailSession getMailSession() {
		return mailSession;
	}

	public void setMailSession(IMailSession mailSession) {
		this.mailSession = mailSession;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Deprecated
	public void sendMail(String messageContent) throws BaseMailException {
		try {
			if (!this.isEnabled()) return;

			logger.debug("starting mail sending");
			logger.debug("Parameters [Sender=" + sender + "]");
			if (to != null && to.length > 0) logger.debug("Parameters [To=" + Arrays.asList(to) + "]");
			if (cc != null && to.length > 0) logger.debug("Parameters [Cc=" + Arrays.asList(cc) + "]");
			if (bcc != null && to.length > 0) logger.debug("Parameters [Bcc=" + Arrays.asList(bcc) + "]");
			logger.debug("Parameters [Subject=" + subject + "]");
			logger.debug("Parameters [Message=" + messageContent + "]");

			logger.debug("Refreshing mail session connection");
			mailSession.connect();

			logger.debug("Creating Mime Message object");
			MimeMessage message = new MimeMessage(mailSession.getMailSession());

			logger.debug("Setting message properties");
			message.setSubject(subject);
			message.setSender(new InternetAddress(sender));

			logger.debug("setting message recipient");
			if (this.getTo() != null && this.getTo().length > 0) {
				for (String recipient : to) {
					logger.debug("adding [" + recipient+ "]");
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				}
			}

			if (this.getCc() != null && this.getCc().length > 0) {
				for (String recipient : cc) {
					logger.debug("adding [" + recipient + "]");
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
				}
			}

			if (this.getBcc() != null && this.getBcc().length > 0) {
				for (String recipient : bcc) {
					logger.debug("adding [" + recipient + "]");
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient));
				}
			}

			logger.debug("creating text bodypart");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(messageContent);

			logger.debug("creating multipart");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			logger.debug("creating attachment if available");
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
			mailSession.send(message);
			logger.debug("sent");
			logger.debug("disconnecting");
			mailSession.disconnect();
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

	@Deprecated
	public void sendMail(String subject, String messageContent) throws BaseMailException {
		this.setSubject(subject);
		this.sendMail(messageContent);
	}

	@Deprecated
	public void sendMail(String subject, String messageContent, String attachment) throws BaseMailException {
		this.setAttachment(attachment);
		this.sendMail(subject, messageContent);
	}

	@Deprecated
	public void sendErrorMail(String messageSubject, String messageContent, Exception exception) throws BaseMailException {
		StringBuffer messageBody = null;
		if (messageContent == null) {
			messageBody = new StringBuffer();
		} else {
			messageBody = new StringBuffer(messageContent);
		}
		messageBody.append("Exception Occurred.\nPlease see below for the stack trace of the exception\n");
		messageBody.append("\n");

		logger.debug("retrieving stack trace of the exception");
		String stackTrace = ExceptionUtil.getStackTraces(exception);
		logger.debug("stack trace of the exception is retrieved");

		logger.debug("setting stack trace of the exception as the message body");
		messageBody.append(stackTrace);

		if (this.getSubject() == null || this.getSubject().trim().length() == 0) {
			this.setSubject(messageSubject);
		} else {
			this.setSubject(this.getSubject() + " - [" + messageSubject + "]");
		}

		this.sendMail(this.getSubject(), messageBody.toString());
	}

	@Deprecated
	public void sendErrorMail(String subject, Exception e) throws BaseMailException {
		this.sendErrorMail(subject, null, e);
	}

	@Deprecated
	public void sendErrorMail(String messageSubject, String messageContent, Exception exception, String attachment) throws BaseMailException {
		this.setAttachment(attachment);
		this.sendErrorMail(messageSubject, messageContent, exception);
	}

	@Deprecated
	public void sendErrorMail(Exception e) throws BaseMailException {
		this.sendErrorMail(null, e);
	}

	public void sendMail(Level level, String messageContent) throws BaseMailException {
		this.sendMail(level, null, messageContent, null, null);
	}

	public void sendMail(Level level, String messageContent, File attachment) throws BaseMailException {
		List<File> attachments = new ArrayList<File>();
		attachments.add(attachment);
		this.sendMail(level, null, messageContent, attachments, null);
	}

	public void sendMail(Level level, String subject, String messageContent) throws BaseMailException {
		this.sendMail(level, subject, messageContent, null, null);
	}

	public void sendMail(Level level, String subject, String messageContent, Exception exception) throws BaseMailException {
		this.sendMail(level, subject, messageContent, null, exception);
	}

	public void sendMail(Level level, String subject, String messageContent, File attachment) throws BaseMailException {
		List<File> attachments = new ArrayList<File>();
		attachments.add(attachment);
		this.sendMail(level, subject, messageContent, attachments, null);
	}

	public void sendMail(Level level, String subject, String messageContent, List<File> attachment, Exception exception) throws BaseMailException {
		/**
		 * Subject
		 */
		logger.debug("this subject = " + this.getSubject() + " || " + subject);
		if (subject != null) {
			subject = level + " " + this.getSubject() + " - " + subject;
		} else {
			subject = level + " " + this.getSubject();
		}

		/**
		 * Message Body
		 */
		StringBuffer finalMessageContent = new StringBuffer();
		finalMessageContent.append(messageContent).append("\n\n");

		/**
		 * Exception
		 */
		logger.debug("checking exception if any");
		if (exception != null) {
			finalMessageContent.append("** Error Trace **").append("\n");
			finalMessageContent.append(ExceptionUtil.getStackTraces(exception)).append("\n");
			finalMessageContent.append("** End Error Trace **").append("\n");
		}

		/**
		 * Attachment
		 */
		logger.debug("checking attachment file");
		if (attachment != null && attachment.size() > 0) {
			boolean attachmentError = false;
			StringBuffer fileCheckMessage = new StringBuffer();
			for (File attachmentItem : attachment) {
				if (!attachmentItem.exists()) {
					fileCheckMessage.append("trying to attach file [" + attachmentItem.getAbsoluteFile() + "] but file does not exist");
					attachmentError = true;
				} else if (!attachmentItem.canRead()) {
					fileCheckMessage.append("trying to attach file [" + attachmentItem.getAbsoluteFile() + "] but file cannot be read");
					attachmentError = true;
				} else if (!attachmentItem.isFile()) {
					fileCheckMessage.append("trying to attach file [" + attachmentItem.getAbsoluteFile() + "] but is not a file");
					attachmentError = true;
				} else {
					fileCheckMessage.append("file [" + attachmentItem.getName() + "] is attached");
				}
				fileCheckMessage.append("\n");
			}

			finalMessageContent.append("** Attachment **").append("\n");
			finalMessageContent.append(fileCheckMessage).append("\n");
			finalMessageContent.append("** End Attachment **").append("\n");

			if (attachmentError) {
				this.send(subject, finalMessageContent.toString(), null);
			} else {
				this.send(subject, finalMessageContent.toString(), attachment);
			}
		} else {
			this.send(subject, finalMessageContent.toString(), null);
		}
	}

	private void send(String subject, String messageContent, List<File> attachments) throws BaseMailException {
		try {
			if (!this.isEnabled()) return;

			logger.debug("starting mail sending");

			logger.debug("Parameters [Sender=" + sender + "]");
			if (to != null && to.length > 0) logger.debug("Parameters [To=" + Arrays.asList(to) + "]");
			if (cc != null && cc.length > 0) logger.debug("Parameters [Cc=" + Arrays.asList(cc) + "]");
			if (bcc != null && bcc.length > 0) logger.debug("Parameters [Bcc=" + Arrays.asList(bcc) + "]");
			logger.debug("Parameters [Subject=" + subject + "]");
			logger.debug("Parameters [Message=" + messageContent + "]");

			logger.debug("Refreshing mail session connection");
			mailSession.connect();

			logger.debug("Creating Mime Message object");
			MimeMessage message = new MimeMessage(mailSession.getMailSession());

			logger.debug("Setting message properties");
			message.setSubject(subject);
			message.setSender(new InternetAddress(sender));

			logger.debug("setting message recipient");
			if (this.getTo() != null && this.getTo().length > 0) {
				for (String recipient : to) {
					logger.debug("adding [" + recipient+ "]");
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
				}
			}

			if (this.getCc() != null && this.getCc().length > 0) {
				for (String recipient : cc) {
					logger.debug("adding [" + recipient + "]");
					message.addRecipient(Message.RecipientType.CC, new InternetAddress(recipient));
				}
			}

			if (this.getBcc() != null && this.getBcc().length > 0) {
				for (String recipient : bcc) {
					logger.debug("adding [" + recipient + "]");
					message.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient));
				}
			}

			logger.debug("creating text bodypart");
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText(messageContent);

			logger.debug("creating multipart");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textBodyPart);

			logger.debug("creating attachment if available");
			if (attachments != null && attachments.size() > 0) {
				for (File attachmentItem : attachments) {
					logger.debug("creating attachment bodypart");
					logger.debug("attachment is [" + attachmentItem + "]");
					MimeBodyPart attachmentBodyPart = new MimeBodyPart();

					DataSource dataSource = new FileDataSource(attachmentItem);
					attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
					attachmentBodyPart.setFileName(attachmentItem.getName());

					multipart.addBodyPart(attachmentBodyPart);
				}
			}

			message.setContent(multipart);

			logger.debug("sending");
			mailSession.send(message);
			logger.debug("sent");
			logger.debug("disconnecting");
			mailSession.disconnect();
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
}
