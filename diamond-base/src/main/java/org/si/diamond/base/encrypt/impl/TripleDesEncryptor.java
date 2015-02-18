/*
 * File Name       : TripleDesEncryptor.java
 * Class Name      : TripleDesEncryptor
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
package org.si.diamond.base.encrypt.impl;

import org.si.diamond.base.encrypt.Encryptor;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * @author adelwin.handoyo
 * 
 */
public class TripleDesEncryptor extends Encryptor {
	protected Logger logger = Logger.getLogger(TripleDesEncryptor.class);

	
	protected Cipher ecipher;
	protected Cipher dcipher;
	protected String passPhrase = "!@#$%^&**&^%$#@!";

//	private final String CIPHER_TRANS = "DESede/CBC/PKCS5Padding";
	static final String KEY_GEN_TRANS = "DESede";
	static final byte[] myIV = { 50, 51, 52, 53, 54, 55, 56, 57 };

	String keyLocation = ".";
	static final int BUFSIZE = 2048;
	SecretKey key = null;

	public void init() throws Exception {
		File keyFile = new File(keyLocation);
		if (keyFile.exists()) {
			key = readKey(keyFile);
		} else {
			key = generateKey();
			writeKey(key, keyFile);
		}
	}

	public String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes("UTF8");

			return encrypt(key, utf8);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error encrypting ", e);
		}
		return null;
	}

	public String decrypt(String str) {
			byte[] dec = Base64.decodeBase64(str);
			return decrypt(key, dec);
	}

	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

	public static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keygen = KeyGenerator.getInstance("DESede");
		return keygen.generateKey();
	}

	public SecretKey readKey(File f) throws InvalidKeyException, InvalidKeySpecException, IOException, NoSuchAlgorithmException {
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		byte[] rawkey = new byte[(int) f.length()];
		in.readFully(rawkey);
		in.close();
		DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = keyfactory.generateSecret(keyspec);
		return key;
	}

	public void writeKey(SecretKey key, File f) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key, DESedeKeySpec.class);
		byte[] rawkey = keyspec.getKey();
		FileOutputStream out = new FileOutputStream(f);
		out.write(rawkey);
		out.close();
	}

	public String encrypt(SecretKey key, byte[] input) {
		String encryptText = null;
		try {
			Cipher encryptor = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			encryptor.init(1, key, new IvParameterSpec(myIV));
			byte[] result = encryptor.doFinal(input);
//			encryptText = new BASE64Encoder().encode(result);
			encryptText = new String(Base64.encodeBase64(result), "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return encryptText;
	}

	public String decrypt(SecretKey key, byte[] input) {
		String decryptText = null;
		try {
			Cipher encryptor = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			encryptor.init(2, key, new IvParameterSpec(myIV));
			byte[] result = encryptor.doFinal(input);
			decryptText = new String(result);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decryptText;
	}

	public void setKeyLocation(String keyLocation) {
		this.keyLocation = keyLocation;
	}
}
