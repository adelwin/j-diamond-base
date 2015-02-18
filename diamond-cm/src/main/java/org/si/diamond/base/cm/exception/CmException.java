/*
 * File Name       : CmException.java
 * Class Name      : CmException
 * Module Name     : pacs-cm
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

package org.si.diamond.base.cm.exception;

import org.si.diamond.base.exception.BaseException;

/**
 * Created by adelwin on 26/07/2014.
 */
public class CmException extends BaseException{
	private static final long	serialVersionUID	= 9110903460415856059L;
	public CmException(String msg) {
		super(msg);
	}
	public CmException(Throwable rootCause) {
		super(rootCause);
	}
	public CmException() {
		super();
	}
	public CmException(String msg, Throwable rootCause) {
		super(msg, rootCause);
	}
	public CmException(String msg, String msgKey) {
		super(msg, msgKey);
	}
	public CmException(String msg, String msgKey, Object[] parameterValues) {
		super(msg, msgKey, parameterValues);
	}
	public CmException(String msg, String errorCode, Throwable rootCause) {
		super(msg, errorCode, rootCause);
	}
	public CmException(String msg, String errorCode, Throwable rootCause, Object[] paramValues) {
		super(msg, errorCode, rootCause, paramValues);
	}
}

