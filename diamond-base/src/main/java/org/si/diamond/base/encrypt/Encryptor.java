/*
 * File Name       : Encryptor.java
 * Class Name      : Encryptor
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

/**
 * 
 */
package org.si.diamond.base.encrypt;

/**
 * @author adelwin.handoyo
 *
 */
public abstract class Encryptor {
	public abstract String encrypt(String inputString);
	public abstract String decrypt(String inputString);
}
