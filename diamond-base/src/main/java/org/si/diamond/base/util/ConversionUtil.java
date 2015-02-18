/*
 * File Name       : ConversionUtil.java
 * Class Name      : ConversionUtil
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

package org.si.diamond.base.util;

import org.si.diamond.base.exception.BaseException;

public class ConversionUtil {
	public boolean booleanValue(String boolStr) throws BaseException {
		if ("TRUE".equalsIgnoreCase(boolStr)) {
			return true;
		} else if ("Y".equalsIgnoreCase(boolStr)) {
			return true;
		} else if ("1".equalsIgnoreCase(boolStr)) {
			return true;
		} else if ("YES".equalsIgnoreCase(boolStr)) {
			return true;
		} else if (Long.parseLong(boolStr) > 0) {
			return true;
		} else if ("FALSE".equalsIgnoreCase(boolStr)) {
			return false;
		} else if ("N".equalsIgnoreCase(boolStr)) {
			return false;
		} else if ("0".equalsIgnoreCase(boolStr)) {
			return false;
		} else if ("NO".equalsIgnoreCase(boolStr)) {
			return false;
		} else if (Long.parseLong(boolStr) <= 0) {
			return false;
		} else {
			throw new BaseException("Conversion Error, unable to find a matching pattern");
		}
	}
}
 