/*
 * File Name       : ContextUtil.java
 * Class Name      : ContextUtil
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

import org.si.diamond.base.context.ApplicationContext;
import org.si.diamond.base.exception.BaseException;

public abstract class ContextUtil {
	private static org.springframework.context.ApplicationContext springContext = null;
	private static ApplicationContext diamondContext = null;
	
	public static org.springframework.context.ApplicationContext getSpringContext() {
		return springContext;
	}
	
	public static void setSpringContext(org.springframework.context.ApplicationContext springContext) throws BaseException {
		if (ContextUtil.springContext == null) {
			ContextUtil.springContext = springContext;
		} else {
			throw new BaseException("context already loaded");
		}
	}
	
	public static ApplicationContext getDiamondContext() {
		return diamondContext;
	}
	
	public static void setDiamondContext(ApplicationContext diamondConfigMap) throws BaseException {
		if (ContextUtil.diamondContext == null) {
			ContextUtil.diamondContext = diamondConfigMap;
		} else {
			throw new BaseException("config map already exist");
		}
	}
}
