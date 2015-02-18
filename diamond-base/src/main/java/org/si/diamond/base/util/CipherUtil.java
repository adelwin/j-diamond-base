/*
 * File Name       : CipherUtil.java
 * Class Name      : CipherUtil
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

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import org.si.diamond.base.exception.CipherException;

public class CipherUtil {

	private static final String	DEFAULT_METHOD	= "org.si.diamond.base.util.TripleDESCipher";
	private static final String DEFAULT_KEY = "p01ntbl4nk";

	public static final String encrypt(String method, String input, String key) throws CipherException {
		try {
			CipherWrapper cipherWrapper = CipherWrapper.getCipher(method);
			byte[] encrypted = cipherWrapper.encrypt(input.getBytes("utf-8"), key);
			return new String(Base64.encodeBase64(encrypted));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		}
	}

	public static final String decrypt(String method, String input, String key) throws CipherException {
		CipherWrapper cipherWrapper = CipherWrapper.getCipher(method);
		byte[] decodedInput = Base64.decodeBase64(input);
		byte[] decrypted = cipherWrapper.decrypt(decodedInput, key);
		return new String(decrypted);
	}

	/**
	 * the following two methods should be implemented to provide functionality to encrypt and decrypt simple algorithm used on client side with javascript
	 * @throws org.si.diamond.base.exception.CipherException
	 */
	public static final String encrypt(String input, String key) throws CipherException {
		return encrypt(DEFAULT_METHOD, input, key);
	}

	public static final String decrypt(String input, String key) throws CipherException {
		return decrypt(DEFAULT_METHOD, input, key);
	}
	
	/**
	 * the following two methods are implemented to provide basic functionality of encrypt and decrypt, 
	 * using a default key and a default algorithm.
	 * it is best not to use this method unless for demonstration purpose only
	 */
	public static final String encrypt(String input) throws CipherException {
		return encrypt(input, DEFAULT_KEY);
	}
	
	public static final String decrypt(String input) throws CipherException {
		return decrypt(input, DEFAULT_KEY);
	}
}
