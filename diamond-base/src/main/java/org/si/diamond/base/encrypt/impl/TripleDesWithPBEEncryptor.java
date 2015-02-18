/*
 * File Name       : TripleDesWithPBEEncryptor.java
 * Class Name      : TripleDesWithPBEEncryptor
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import org.si.diamond.base.encrypt.Encryptor;

/**
 * @author adelwin.handoyo
 * 
 */
public class TripleDesWithPBEEncryptor extends Encryptor {
	protected Logger logger = Logger.getLogger(TripleDesWithPBEEncryptor.class);

	protected Cipher ecipher;
	protected Cipher dcipher;
	protected String passPhrase = "!@#$%^&**&^%$#@!";

	protected byte[] salt = { -87, -101, -56, 50, 86, 53, -29, 3 };

	protected int iterationCount = 19;

	TripleDesWithPBEEncryptor(String passPhrase) {
		init();
	}

	TripleDesWithPBEEncryptor() {
	}

	public void init() {
		try {
			KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndTripleDES").generateSecret(keySpec);

			ecipher = Cipher.getInstance(key.getAlgorithm());
			dcipher = Cipher.getInstance(key.getAlgorithm());

			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

			ecipher.init(1, key, paramSpec);
			dcipher.init(2, key, paramSpec);
		} catch (InvalidAlgorithmParameterException e) {
			logger.error("Error intialising encrypter", e);
		} catch (InvalidKeySpecException e) {
			logger.error("Error intialising encrypter", e);
		} catch (NoSuchPaddingException e) {
			logger.error("Error intialising encryptor", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error intialising encryptor", e);
		} catch (InvalidKeyException e) {
			logger.error("Error intialising encryptor", e);
		}
		logger.info("Initialisation of encryptor complete");
	}

	public String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes("UTF8");

			byte[] enc = ecipher.doFinal(utf8);

//			return new BASE64Encoder().encode(enc);
			return new String(Base64.encodeBase64(enc), "UTF-8");
		} catch (BadPaddingException e) {
			logger.error("Error encrypting ", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("Error encrypting ", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error encrypting ", e);
		}
		return null;
	}

	public String decrypt(String str) {
		try {
//			byte[] dec = new BASE64Decoder().decodeBuffer(str);
			byte[] dec = Base64.decodeBase64(str);

			byte[] utf8 = dcipher.doFinal(dec);

			return new String(utf8, "UTF8");
		} catch (BadPaddingException e) {
			logger.error("Error Decrypting", e);
		} catch (IllegalBlockSizeException e) {
			logger.error("Error Decrypting", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Error Decrypting", e);
		}
		return null;
	}

	public static void main(String[] args) {
		String data = "";
		String passwd = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter Data: ");
			data = br.readLine();
			System.out.print("Enter Pass Phrase: ");
			passwd = br.readLine();

			Encryptor encrypter = new TripleDesWithPBEEncryptor(passwd);

			String encrypted = encrypter.encrypt(data);
			System.out.println("Encrypted: " + encrypted);
			encrypter = null;

			Encryptor decrypter = new TripleDesWithPBEEncryptor(passwd);

			String decrypted = decrypter.decrypt(encrypted);
			System.out.println("Decrypted: " + decrypted);
			decrypter = null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getPassPhrase() {
		return passPhrase;
	}

	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}
}
