/*
 * File Name       : DecimalDataFormat.java
 * Class Name      : DecimalDataFormat
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

import java.text.DecimalFormat;
import java.text.ParseException;

import org.si.diamond.base.dataextraction.exception.BaseExtractionRuntimeException;

public class DecimalDataFormat extends NumberDataFormat {

	protected String dataFormat;

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String format(Object object) {
		if (object == null) return null;
		try {
			DecimalFormat formatter = new DecimalFormat(dataFormat);
			Number number = formatter.parse(object.toString());
			String result = formatter.format(number);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BaseExtractionRuntimeException("Unable to format data");
		}
	}
	
	public String format(double input) {
		try {
			DecimalFormat formatter = new DecimalFormat(dataFormat);
			Double numberDouble = new Double(input);
			Number number = formatter.parse(numberDouble.toString());
			String result = formatter.format(number);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new BaseExtractionRuntimeException("Unable to format data");
		}
	}
}
