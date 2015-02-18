/*
 * File Name       : CipherWrapper.java
 * Class Name      : CipherWrapper
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

import org.si.diamond.base.exception.CipherException;

public abstract class CipherWrapper {
	
	/**
	 * not designed as singleton
	 */
	public static final CipherWrapper getCipher(String cipherClass) throws CipherException {
		try {
			Class<?> clazz = Class.forName(cipherClass);
			return (CipherWrapper) clazz.newInstance();
		} catch (Exception e) {
			throw new CipherException(e.getMessage(), e);
		}
	}
	
	public abstract byte[] encrypt(byte[] input, String key) throws CipherException;
	public abstract byte[] decrypt(byte[] input, String key) throws CipherException;
}
