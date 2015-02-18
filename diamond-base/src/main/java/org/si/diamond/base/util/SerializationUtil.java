/*
 * File Name       : SerializationUtil.java
 * Class Name      : SerializationUtil
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import org.si.diamond.base.exception.SerializationException;

public class SerializationUtil {
	private static final Base64 BASE64 = new Base64();
	
	public static final Object clone(Serializable object) throws SerializationException {
		return deserializeFromByte(serializeToByte(object));
	}

	public static void serializeToStream(Serializable obj, OutputStream outputStream) throws SerializationException {
        ObjectOutputStream out = null;
        try {
        	out = new ObjectOutputStream(outputStream);
        	out.writeObject(obj);
        } catch(IOException e) {
        	throw new SerializationException(e.getMessage(), e);
        } finally {
        	try {
        		if(out != null) out.close();
        	} catch(IOException e) {
        		throw new SerializationException(e.getMessage(), e);
        	}
        }
	}

	public static byte[] serializeToByte(Serializable obj) throws SerializationException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	serializeToStream(obj, ((OutputStream) (baos)));
    	return baos.toByteArray();
	}
	
	public static String serializeToString(Serializable obj) throws SerializationException {
		return BASE64.encodeAsString(serializeToByte(obj));
	}
	
	public static Object deserializeFromStream(InputStream inputStream) throws SerializationException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(inputStream);
			Object obj = in.readObject();
			return obj;
        } catch(ClassNotFoundException e) {
        	throw new SerializationException(e.getMessage(), e);
        } catch(IOException e) {
        	throw new SerializationException(e);
        } finally {
        	try {
            	if(in != null) in.close();
        	} catch(IOException e) {
        		throw new SerializationException(e.getMessage(), e);
        	}
        }
	}

    public static Object deserializeFromByte(byte objectData[]) throws SerializationException {
    	ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
    	return deserializeFromStream(((InputStream) (bais)));
	}
    
    public static Object deserializeFromString(String strObject) throws SerializationException {
    	byte objectData[];
		objectData = BASE64.decode(strObject);
		return deserializeFromByte(objectData);
    }
    
}
