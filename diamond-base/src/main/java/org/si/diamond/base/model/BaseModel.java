/*
 * File Name       : BaseModel.java
 * Class Name      : BaseModel
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

package org.si.diamond.base.model;

import java.util.List;

import org.apache.log4j.Logger;

import org.si.diamond.base.util.BeanUtil;

public abstract class BaseModel {

	protected static Logger	logger	= Logger.getLogger(BaseModel.class);

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		try {
			StringBuffer propertyString = new StringBuffer("\n");
			propertyString.append("Class Name = " + this.getClass()).append("\n");
			List<String> beanPropertyList = BeanUtil.extractBeanFields(this.getClass());
			for (String beanPropertyName : beanPropertyList) {
				Object realBeanPropertyValue = BeanUtil.getProperty(this, beanPropertyName);
				if (realBeanPropertyValue != null) {
					realBeanPropertyValue = (String) realBeanPropertyValue.toString();
					propertyString.append("-> [" + beanPropertyName + "=" + realBeanPropertyValue + "]").append("\n");
				}
			}
			return propertyString.toString();
		} catch (Exception e) {
			logger.error("failed invoking read method of property from class " + this.getClass());
			e.printStackTrace();
		}
		return super.toString();
	}

	public String toFlatString() {
		return this.toString().replaceAll("\\n-> ", " ");
	}
}
