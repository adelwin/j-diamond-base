/*
 * File Name       : BaseExtractionRuntimeException.java
 * Class Name      : BaseExtractionRuntimeException
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
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.si.diamond.base.exception.BaseRuntimeException;

public class BaseExtractionRuntimeException extends BaseRuntimeException {

	private static final long serialVersionUID = -7821654635688658850L;
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
	 * parameterValues are used to insert some runtime generated values into the
	 * message, e.g. if the localized message is "The user {0} does not have the
	 * access right to page {1}". During runtime, {0} and {1} can be replaced by
	 * "Raymond" and "useradmin.jsp" respectively
	 */
	private Object[] parameterValues = null;

	/**
	 * For internally use only, to force the developers include an error code
	 * 
	 */
	public BaseExtractionRuntimeException(String msg) {
		super(msg);
	}

	/**
	 * For internally use only, to force the developers include an error code
	 * 
	 */
	public BaseExtractionRuntimeException(Throwable rootCause) {
		super(rootCause.getMessage());
		this.rootCause = rootCause;
	}

	public BaseExtractionRuntimeException() {
		super();
	}

	public BaseExtractionRuntimeException(String msg, Throwable rootCause) {
		this(msg, null, rootCause, null);
	}

	/**
	 * Construct an exception with a message, error code
	 * 
	 */
	public BaseExtractionRuntimeException(String msg, String msgKey) {
		this(msg, msgKey, null, null);
	}

	/**
	 * Construct an exception with a message, error code
	 * 
	 */
	public BaseExtractionRuntimeException(String msg, String msgKey, Object[] parameterValues) {
		this(msg, msgKey, null, parameterValues);
	}

	/**
	 * Construct an exception with a message, error code and the root cause
	 * exception, if any
	 * 
	 */
	public BaseExtractionRuntimeException(String msg, String errorCode, Throwable rootCause) {
		this(msg, errorCode, rootCause, null);
	}

	/**
	 * Construct an exception with a message, error code and the root cause
	 * exception, if any
	 * 
	 */
	public BaseExtractionRuntimeException(String msg, String errorCode, Throwable rootCause,
			Object[] paramValues) {
		super(msg);
		this.rootCause = rootCause;
		this.errorCode = errorCode;
		this.parameterValues = paramValues;
	}

	/**
	 * @see Throwable#printStackTrace()
	 */
	public void printStackTrace() {
		super.printStackTrace();
		if (rootCause != null) {
			System.err.println("==============Root Cause===============");
			rootCause.printStackTrace();
		}
	}

	/**
	 * @see Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		if (rootCause != null) {
			s.println("==============Root Cause===============");
			rootCause.printStackTrace(s);
		}
	}

	/**
	 * @see Throwable#printStackTrace(java.io.PrintWriter)
	 */
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

	/**
	 * Returns the parameterValues.
	 * 
	 * @return Object[]
	 */
	public Object[] getParameterValues() {
		return parameterValues;
	}

	/**
	 * Sets the parameterValues.
	 * 
	 * @param parameterValues
	 *            The parameterValues to set
	 */
	public void setParameterValues(Object[] parameterValues) {
		this.parameterValues = parameterValues;
	}


}
