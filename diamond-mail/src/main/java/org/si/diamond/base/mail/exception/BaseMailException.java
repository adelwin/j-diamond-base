/*
 * File Name       : BaseMailException.java
 * Class Name      : BaseMailException
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

package org.si.diamond.base.mail.exception;

import org.si.diamond.base.exception.BaseException;

public class BaseMailException extends BaseException {

	private static final long	serialVersionUID	= 6046927819414834641L;

	public BaseMailException() {
		super();
	}

	public BaseMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseMailException(String message) {
		super(message);
	}

	public BaseMailException(Throwable cause) {
		super(cause);
	}

}
