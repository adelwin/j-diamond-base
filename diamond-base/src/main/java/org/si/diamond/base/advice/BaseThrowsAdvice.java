/*
 * File Name       : BaseThrowsAdvice.java
 * Class Name      : BaseThrowsAdvice
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
package org.si.diamond.base.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import org.si.diamond.base.exception.BaseException;

public class BaseThrowsAdvice implements ThrowsAdvice {
	protected static Logger logger = Logger.getLogger(BaseThrowsAdvice.class);
	
	/**
	 * Minimum implementation.
	 * should be overriden to implement exception report collection
	 * @param method
	 * @param args
	 * @param target
	 * @param t
	 */
	public void afterThrowing(Method method, Object[] args, Object target, BaseException e) {
	}

}
