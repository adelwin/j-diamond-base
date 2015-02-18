/*
 * File Name       : CipherException.java
 * Class Name      : CipherException
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:04
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

package org.si.diamond.base.exception;

public class CipherException extends BaseException {

	private static final long	serialVersionUID	= 9110903460415856059L;
	public CipherException(String msg) {
		super(msg);
	}
	public CipherException(Throwable rootCause) {
		super(rootCause);
	}
	public CipherException() {
		super();
	}
	public CipherException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}
	public CipherException(String msg, String msgKey) {
		super(msg, msgKey);
	}
	public CipherException(String msg, String msgKey, Object[] parameterValues) {
		super(msg, msgKey, parameterValues);
	}
	public CipherException(String msg, String errorCode, Throwable rootCause) {
		super(msg, errorCode, rootCause);
	}
	public CipherException(String msg, String errorCode, Throwable rootCause, Object[] paramValues) {
		super(msg, errorCode, rootCause, paramValues);
	}
}