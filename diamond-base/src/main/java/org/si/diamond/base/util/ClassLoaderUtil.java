/*
 * File Name       : ClassLoaderUtil.java
 * Class Name      : ClassLoaderUtil
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

public class ClassLoaderUtil {
	public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
		try {
			return Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException ex) {
				try {
					return ClassLoaderUtil.class.getClassLoader().loadClass(className);
				} catch (ClassNotFoundException exc) {
				}
			}
		}
		return callingClass.getClassLoader().loadClass(className);
	}

	public static URL getResource(String resourceName, Class<?> callingClass) {
		URL url = null;

		url = Thread.currentThread().getContextClassLoader().getResource(resourceName);

		if (url == null) {
			url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
		}

		if (url == null) {
			url = callingClass.getClassLoader().getResource(resourceName);
		}
		return url;
	}

	public static Enumeration<?> getResources(String resourceName, Class<?> callingClass) throws IOException {
		Enumeration<?> urls = Thread.currentThread().getContextClassLoader().getResources(resourceName);
		if (urls == null) {
			urls = ClassLoaderUtil.class.getClassLoader().getResources(resourceName);
			if (urls == null) {
				urls = callingClass.getClassLoader().getResources(resourceName);
			}
		}

		return urls;
	}

	public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
		URL url = getResource(resourceName, callingClass);
		try {
			return url != null ? url.openStream() : null;
		} catch (IOException e) {
		}
		return null;
	}
}
