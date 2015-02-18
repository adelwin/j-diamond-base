/*
 * File Name       : SftpException.java
 * Class Name      : SftpException
 * Module Name     : pacs-sftp
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

package org.si.diamond.base.sftp.exception;

import org.si.diamond.base.exception.BaseException;

public class SftpException extends BaseException {

	private static final long	serialVersionUID	= 9110903460415856059L;
	public SftpException(String msg) {
		super(msg);
	}
	public SftpException(Throwable rootCause) {
		super(rootCause);
	}
	public SftpException() {
		super();
	}
	public SftpException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}
	public SftpException(String msg, String msgKey) {
		super(msg, msgKey);
	}
	public SftpException(String msg, String msgKey, Object[] parameterValues) {
		super(msg, msgKey, parameterValues);
	}
	public SftpException(String msg, String errorCode, Throwable rootCause) {
		super(msg, errorCode, rootCause);
	}
	public SftpException(String msg, String errorCode, Throwable rootCause, Object[] paramValues) {
		super(msg, errorCode, rootCause, paramValues);
	}
}
