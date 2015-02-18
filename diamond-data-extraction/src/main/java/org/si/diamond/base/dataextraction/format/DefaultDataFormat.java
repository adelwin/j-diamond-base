/*
 * File Name       : DefaultDataFormat.java
 * Class Name      : DefaultDataFormat
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

package org.si.diamond.base.dataextraction.format;

public abstract class DefaultDataFormat {
	protected String	columnName;
	protected String	propertyName;
	protected String	dataType;
	protected int		length;

	//this is for generation of fixed-in-length format
	protected String	pad;
	protected String	position;

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public abstract String format(Object object);

	/**
	 * Get the <code>pad</code>.
	 * 
	 * @return the pad
	 */
	public String getPad() {
		return pad;
	}

	/**
	 * Set the <code>pad</code>.
	 * 
	 * @param pad
	 *            the pad to set
	 */
	public void setPad(String pad) {
		this.pad = pad;
	}

	/**
	 * Get the <code>position</code>.
	 * 
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Set the <code>position</code>.
	 * 
	 * @param position
	 *            the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}
}