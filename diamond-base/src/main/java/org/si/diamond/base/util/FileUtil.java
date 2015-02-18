/*
 * File Name       : FileUtil.java
 * Class Name      : FileUtil
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
 * | Adelwin Handoyo | 2014-12-01 21.44 | 2.6.0   | - Add convenience method to dump a list of BaseModels to a file
 * | Adelwin Handoyo | 2015-01-07 17.56 | 2.6.1   | - Add target file name during error reporting during dump string
 * |                 |                  |         | - Remove unused import
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.si.diamond.base.exception.BaseException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class FileUtil {
	protected static Logger logger = Logger.getLogger(FileUtil.class);

	@Deprecated
	public static boolean fileStreamCopy(File fromFile, File toFile) throws IOException, BaseException {
		if (!fromFile.exists()) throw new BaseException("FileCopy: " + "no such source file: " + fromFile.getName());
		if (!fromFile.isFile()) throw new BaseException("FileCopy: " + "can't copy directory: " + fromFile.getName());
		if (!fromFile.canRead()) throw new BaseException("FileCopy: " + "source file is unreadable: " + fromFile.getName());

		if (toFile.isDirectory()) toFile = new File(toFile, fromFile.getName());

		if (toFile.exists()) {
			throw new BaseException("FileCopy: " + "destination file already exist: " + toFile.getName());
		} else {
			String parent = toFile.getParent();
			if (parent == null) parent = System.getProperty("user.dir");
			File dir = new File(parent);
			if (!dir.exists()) throw new BaseException("FileCopy: " + "destination directory doesn't exist: " + parent);
			if (dir.isFile()) throw new BaseException("FileCopy: " + "destination is not a directory: " + parent);
			if (!dir.canWrite()) throw new BaseException("FileCopy: " + "destination directory is unwriteable: " + parent);
		}

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write

		} finally {
			if (from != null) from.close();
			if (to != null) to.close();
		}
		if (fromFile.delete()) return false;
		return true;
	}

	public static boolean fileStreamCopy(File fromFile, File toFile, boolean overwrite) throws IOException, BaseException {
		if (!fromFile.exists()) throw new BaseException("FileCopy: " + "no such source file: " + fromFile.getName());
		if (!fromFile.isFile()) throw new BaseException("FileCopy: " + "can't copy directory: " + fromFile.getName());
		if (!fromFile.canRead()) throw new BaseException("FileCopy: " + "source file is unreadable: " + fromFile.getName());

		if (toFile.isDirectory()) toFile = new File(toFile, fromFile.getName());

		if (toFile.exists() && (!overwrite)) {
			throw new BaseException("FileCopy: " + "destination file already exist: " + toFile.getName());
		}
		String parent = toFile.getParent();
		if (parent == null) parent = System.getProperty("user.dir");
		File dir = new File(parent);
		if (!dir.exists()) throw new BaseException("FileCopy: " + "destination directory doesn't exist: " + parent);
		if (dir.isFile()) throw new BaseException("FileCopy: " + "destination is not a directory: " + parent);
		if (!dir.canWrite()) throw new BaseException("FileCopy: " + "destination directory is unwriteable: " + parent);

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write

		} finally {
			if (from != null) from.close();
			if (to != null) to.close();
		}
		if (fromFile.delete()) return false;
		return true;
	}

	public static void dumpStringListToFile(List<String> content, String targetFileName) throws IOException, BaseException {
		File targetFile = new File(targetFileName);

		if (targetFile.exists()) throw new BaseException("FileDump: target file [" + targetFileName + "] already exist");
		if (!targetFile.canWrite() && targetFile.exists()) throw new BaseException("FileDump: target file [" + targetFileName + "] not writable");

		FileUtils.writeLines(targetFile, content);
	}

	public static void dumpListToFile(List<Object> content, String targetFileName) throws IOException, BaseException {
		File targetFile = new File(targetFileName);

		if (targetFile.exists()) throw new BaseException("FileDump: target file already exist");
		if (!targetFile.canWrite()) throw new BaseException("FileDump: target file not writable");

		List<String> list = new ArrayList<String>();
		for (Object model : content) {
			list.add(model.toString());
		}

		FileUtils.writeLines(targetFile, list);
	}

	public static void dumpListToFile(List<?> content, List<String> columns, String targetFileName) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException, BaseException {
		List<String> fileContent = new ArrayList<String>();
		StringBuffer line = new StringBuffer();

		Iterator<String> columnNameIterator = columns.iterator();
		while (columnNameIterator.hasNext()) {
			String columnName = columnNameIterator.next();
			line.append(columnName.toUpperCase());
			if (columnNameIterator.hasNext()) {
				line.append(",");
			}
		}
		fileContent.add(line.toString());

		for (Object model : content) {
			line = new StringBuffer();
			Iterator<String> columnValueIterator = columns.iterator();
			while (columnValueIterator.hasNext()) {
				String columnName = columnValueIterator.next();
				String columnValue = BeanUtil.getProperty(model, columnName).toString();
				line.append(columnValue);
				if (columnValueIterator.hasNext()) {
					line.append(",");
				}
			}
			line.append("\n");
			fileContent.add(line.toString());
		}

		FileUtil.dumpStringListToFile(fileContent, targetFileName);
	}
}
