/*
 * File Name       : XmlException.java
 * Class Name      : XmlException
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:10:30
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.xml.exception;

import org.si.diamond.base.exception.BaseException;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class XmlException extends BaseException {
	private static final long serialVersionUID = -4913864731317867480L;
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
	public XmlException(String msg) {
		super(msg);
	}

	/**
	 * For internally use only, to force the developers include an error code
	 *
	 */
	public XmlException(Throwable rootCause) {
		super(rootCause.getMessage());
		this.rootCause = rootCause;
	}

	public XmlException() {
		super();
	}

	public XmlException(String msg, Throwable rootCause) {
		this(msg, null, rootCause);
	}

	/**
	 * Construct an exception with a message, error code
	 *
	 */
	public XmlException(String msg, String msgKey) {
		this(msg, msgKey, null);
	}

	/**
	 * Construct an exception with a message, error code and the root cause
	 * exception, if any
	 *
	 */
	public XmlException(String msg, String errorCode, Throwable rootCause) {
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
