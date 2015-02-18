/*
 * File Name       : FileNameFilterImpl.java
 * Class Name      : FileNameFilterImpl
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-02-09 15:25:42
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-02-09 15.25 | 2.8.0   | New feature, file filter and file finder
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.file.filter.impl;

import org.si.diamond.base.file.filter.BaseFileNameFilter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adelwin.handoyo on 2015-02-09.
 */
public class FileNameFilterImpl implements BaseFileNameFilter {
	protected static Logger logger = Logger.getLogger(FileNameFilterImpl.class);
	private List<String> patterns;

	public FileNameFilterImpl(String... nameFilter) {
		super();
		logger.debug("creating file name filter with parameter [" + nameFilter + "]");
		for (String currentPart : nameFilter) {
			if (this.patterns == null) {
				this.patterns = new ArrayList<String>();
			}
			this.patterns.add(currentPart);
		}
		logger.debug("File Name Filter Created [" + patterns + "]");
	}


	/* (non-Javadoc)
	 * @see com.pru.pacs.printinterface.filter.BasicFileNameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		boolean returnValue = true;
		for (String filter : this.patterns) {
			if (filter.startsWith("!")) {
				filter = filter.replaceAll("!", "");
				if (name.contains(filter)) {
					returnValue = returnValue && false;
				} else {
					returnValue = returnValue && true;
				}
			} else {
				if (name.endsWith(filter)) {
					returnValue = returnValue && true;
				} else {
					returnValue = returnValue && false;
				}
			}
		}
		return returnValue;
	}

	@Override
	public String toString() {
		return "Patterns = {" + patterns + "}";
	}
}
