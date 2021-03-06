/*
 * File Name       : TripleDESCipher.java
 * Class Name      : TripleDESCipher
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.si.diamond.base.exception.CipherException;

public class TripleDESCipher extends CipherWrapper {

	@Override
	public byte[] decrypt(byte[] input, String key) throws CipherException {
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("md5");
			final byte[] digestOfKey = messageDigest.digest(key.getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfKey, 24);
			
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			
			final SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			
			final byte[] encryptedText = input;
			final byte[] plainText = decipher.doFinal(encryptedText);
			
			return plainText;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		}
	}

	@Override
	public byte[] encrypt(byte[] input, String key) throws CipherException {
		try {
			final MessageDigest messageDigest = MessageDigest.getInstance("md5");
			final byte[] digestOfKey = messageDigest.digest(key.getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfKey, 24);
			
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			
			final SecretKey secretKey = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			
			final byte[] plainTextBytes = input;
			final byte[] cipherText = cipher.doFinal(plainTextBytes);
			
			return cipherText;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new CipherException(e.getMessage(), e);
		}
	}

}
