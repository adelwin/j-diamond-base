/*
 * File Name       : RemotingException.java
 * Class Name      : com.pru.pacs.base.remoting.RemotingException
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 12:07:24
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-01-16 12:08 | 2.7.1   | - Initial Version
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

/**
 * File Name    : BaseDaoException.java
 * Author       : adelwin
 * Created Date : Dec 29, 2010 3:11:33 PM
 */
package com.pru.pacs.base.remoting.exception;

import com.pru.pacs.base.exception.BaseException;

import java.io.PrintStream;
import java.io.PrintWriter;

public class RemotingException extends BaseException {
	private static final long serialVersionUID = 7500979249575642484L;

	/**
	 * the root of the exception (the exception that cause the error).
	 */
	private Throwable rootCause = null;

	/**
	 * The error code in the format of ExxxYYYY
	 * 
	 * @see Error code spreadsheet
	 */
	private String errorCode = null;

	/**
	 * For internally use only, to force the developers include an error code
	 * 
	 */
	public RemotingException(String msg) {
		super(msg);
	}

	/**
	 * For internally use only, to force the developers include an error code
	 * 
	 */
	public RemotingException(Throwable rootCause) {
		super(rootCause.getMessage());
		this.rootCause = rootCause;
	}

	public RemotingException() {
		super();
	}

	public RemotingException(String msg, Throwable rootCause) {
		this(msg, null, rootCause);
	}

	/**
	 * Construct an exception with a message, error code
	 * 
	 */
	public RemotingException(String msg, String msgKey) {
		this(msg, msgKey, null);
	}

	/**
	 * Construct an exception with a message, error code and the root cause
	 * exception, if any
	 * 
	 */
	public RemotingException(String msg, String errorCode, Throwable rootCause) {
		super(msg);
		this.rootCause = rootCause;
		this.errorCode = errorCode;
	}

	public void printStackTrace() {
		super.printStackTrace();
		if (rootCause != null) {
			System.err.println("==============Root Cause===============");
			rootCause.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		if (rootCause != null) {
			s.println("==============Root Cause===============");
			rootCause.printStackTrace(s);
		}
	}

	public void printStackTrace(PrintWriter s) {
		super.printStackTrace(s);
		if (rootCause != null) {
			s.println("==============Root Cause===============");
			rootCause.printStackTrace(s);
		}
	}

	/**
	 * Returns the errorCode.
	 * 
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Returns the rootCause.
	 * 
	 * @return Throwable
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * Sets the errorCode.
	 * 
	 * @param errorCode
	 *            The errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the rootCause.
	 * 
	 * @param rootCause
	 *            The rootCause to set
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}


}
