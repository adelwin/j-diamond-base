/*
 * File Name       : CommandExecutorException.java
 * Class Name      : CommandExecutorException
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

public class CommandExecutorException extends BaseException {

	private static final long	serialVersionUID	= 4221532475871978427L;
	public CommandExecutorException() {
		super();
	}
	public CommandExecutorException(String msg) {
		super(msg);
	}
	public CommandExecutorException(String msg, String msgKey) {
		super(msg, msgKey);
	}
	public CommandExecutorException(String msg, String msgKey, Object[] parameterValues) {
		super(msg, msgKey, parameterValues);
	}
	public CommandExecutorException(String msg, String errorCode, Throwable rootCause) {
		super(msg, errorCode, rootCause);
	}
	public CommandExecutorException(String msg, String errorCode, Throwable rootCause, Object[] paramValues) {
		super(msg, errorCode, rootCause, paramValues);
	}
	public CommandExecutorException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}
	public CommandExecutorException(Throwable rootCause) {
		super(rootCause);
	}
}
