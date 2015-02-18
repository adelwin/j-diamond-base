/*
 * File Name       : SerializationException.java
 * Class Name      : SerializationException
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

public class SerializationException extends BaseException {
	
	private static final long	serialVersionUID	= 4221532475871978427L;
	public SerializationException() {
		super();
	}
	public SerializationException(String msg) {
		super(msg);
	}
	public SerializationException(String msg, String msgKey) {
		super(msg, msgKey);
	}
	public SerializationException(String msg, String msgKey, Object[] parameterValues) {
		super(msg, msgKey, parameterValues);
	}
	public SerializationException(String msg, String errorCode, Throwable rootCause) {
		super(msg, errorCode, rootCause);
	}
	public SerializationException(String msg, String errorCode, Throwable rootCause, Object[] paramValues) {
		super(msg, errorCode, rootCause, paramValues);
	}
	public SerializationException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}
	public SerializationException(Throwable rootCause) {
		super(rootCause);
	}
}
