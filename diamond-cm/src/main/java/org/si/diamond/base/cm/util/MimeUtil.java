/*
 * File Name       : MimeUtil.java
 * Class Name      : MimeUtil
 * Module Name     : pacs-cm
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

package org.si.diamond.base.cm.util;

import java.io.File;
import java.util.Hashtable;

/**
 * Created by adelwin on 26/07/2014.
 */
public class MimeUtil {
	private static Hashtable<String, String> MimeType = new Hashtable<String, String>();

	static {
		MimeType.put("xls", "application/excel");
		MimeType.put("doc", "application/msword");
		MimeType.put("bin", "application/octet-stream");
		MimeType.put("pdf", "application/pdf");
		MimeType.put("rtf", "application/rtf");
		MimeType.put("xls", "application/vnd.ms-excel");
		MimeType.put("ppt", "application/vnd.ms-powerpoint");
		MimeType.put("mpp", "application/vnd.ms-project");
		MimeType.put("tnef", "application/vnd.ms-tnef");
		MimeType.put("rm", "application/vnd.rn-realmedia");
		MimeType.put("xml", "application/xml");
		MimeType.put("wav", "audio/x-wav");
		MimeType.put("bmp", "image/bmp");
		MimeType.put("gif", "image/gif");
		MimeType.put("jpg", "image/jpeg");
		MimeType.put("jpeg", "image/jpeg");
		MimeType.put("png", "image/png");
		MimeType.put("tif", "image/tiff");
		MimeType.put("html", "text/html");
		MimeType.put("txt", "text/plain");
	}

	public static String getType(String fileExtension) {
		return MimeType.get(fileExtension);
	}

	public static String getType(File file) {
		String fileName = file.getAbsolutePath();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		return MimeUtil.getType(extension);
	}

	public static String getExtension(String mimeType) {
		for (String extension : MimeType.keySet()) {
			String type = MimeType.get(extension);
			if (type.trim().equals(mimeType)) return extension;
		}
		return null;
	}
}