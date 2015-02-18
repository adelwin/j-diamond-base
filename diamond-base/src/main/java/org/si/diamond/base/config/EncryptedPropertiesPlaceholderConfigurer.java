/*
 * File Name       : EncryptedPropertiesPlaceholderConfigurer.java
 * Class Name      : EncryptedPropertiesPlaceholderConfigurer
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

package org.si.diamond.base.config;

import org.apache.log4j.Logger;
import org.si.diamond.base.encrypt.Encryptor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptedPropertiesPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private String encryptedPrefix = "$";
	private Encryptor encryptor;
	protected Logger logger = Logger.getLogger(EncryptedPropertiesPlaceholderConfigurer.class);

	public Encryptor getEncryptor() {
		return encryptor;
	}

	public void setEncryptor(Encryptor encryptor) {
		this.encryptor = encryptor;
	}

	public String getEncryptedPrefix() {
		return encryptedPrefix;
	}

	public void setEncryptedPrefix(String encryptedPrefix) {
		this.encryptedPrefix = encryptedPrefix;
	}

	protected String convertPropertyValue(String originalValue) {
		if ((originalValue != null) && (originalValue.startsWith(encryptedPrefix))) {
			String substring = originalValue.substring(1);
			String decrypt = encryptor.decrypt(substring);
			logger.info("Decrypted property [" + originalValue + "] [" + decrypt + "]");
			return decrypt;
		}
		return super.convertPropertyValue(originalValue);
	}
}
