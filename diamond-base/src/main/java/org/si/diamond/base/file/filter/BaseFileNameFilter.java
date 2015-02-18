/*
 * File Name       : BaseFilter.java
 * Class Name      : com.pru.pacs.base.file.filter.BaseFilter
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-02-09 15:24:24
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

package org.si.diamond.base.file.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by adelwin.handoyo on 2015-02-09.
 */
public interface BaseFileNameFilter extends FilenameFilter {
	/**
	 * Tests if a specified file should be included in a file list.
	 *
	 * @param  dir  the directory in which the file was found.
	 * @param  name the name of the file.
	 * @return <code>true</code> if and only if the name should be included in the file list; <code>false</code> otherwise.
	 */
	boolean accept(File dir, String name);

}
