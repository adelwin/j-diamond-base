/*
 * File Name       : GenericCodeValue.java
 * Class Name      : GenericCodeValue
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

package org.si.diamond.base.dataextraction.model;

import org.si.diamond.base.model.BaseModel;

public class GenericCodeValue extends BaseModel {
	private String	code;
	private String	value;

	/**
	 * Get the <code>code</code>.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set the <code>code</code>.
	 * 
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get the <code>value</code>.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set the <code>value</code>.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}