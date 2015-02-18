/*
 * File Name       : PropertyFieldEncryptor.java
 * Class Name      : PropertyFieldEncryptor
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

package org.si.diamond.base.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.si.diamond.base.util.ClassLoaderUtil;

public class PropertyFieldEncryptor {
	protected Logger logger = Logger.getLogger(PropertyFieldEncryptor.class);
	
	private String location;
	private String[] autoEncryptProperties;
	private Encryptor encryptor;
	private String encryptedPrefix;

	public String[] getAutoEncryptProperties() {
		return autoEncryptProperties;
	}

	public void setAutoEncryptProperties(String[] autoEncryptProperties) {
		this.autoEncryptProperties = autoEncryptProperties;
	}
	public String getLocation() {
		if (location.startsWith("classpath*:")) {
			return ClassLoaderUtil.getResource(location.substring("classpath*:".length()), getClass()).getPath();
		}
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

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
	
	@SuppressWarnings("deprecation")
	public void encryptFields() throws IOException {
		Properties prop = new Properties();

		prop.load(new FileInputStream(new File(getLocation())));
		boolean hasBeeenEncrypted = false;

		for (int i = 0; i < autoEncryptProperties.length; i++) {
			if (prop.containsKey(autoEncryptProperties[i])) {
				String toEncrypt = (String) prop.get(autoEncryptProperties[i]);
				if (!toEncrypt.startsWith(encryptedPrefix)) {
					String encrypted = encryptor.encrypt(toEncrypt);
					logger.info("Auto Encrypted property [" + autoEncryptProperties[i] + "]");
					prop.put(autoEncryptProperties[i], "$" + encrypted);
					hasBeeenEncrypted = true;
				}
			}
		}

		if (hasBeeenEncrypted) {
			FileOutputStream os = new FileOutputStream(new File(getLocation()));
			prop.save(os, "Saved after Auto Encryption");
		}
	}
}