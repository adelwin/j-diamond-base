/*
 * File Name       : DateDataFormat.java
 * Class Name      : DateDataFormat
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDataFormat extends DefaultDataFormat {
	
	protected String dataFormat;

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	
	public String format(Object object) {
		if (object == null) return null;
		
		SimpleDateFormat sdf = new SimpleDateFormat(this.dataFormat);
		Date inputData = (Date) object;
		return sdf.format(inputData);
	}
}
