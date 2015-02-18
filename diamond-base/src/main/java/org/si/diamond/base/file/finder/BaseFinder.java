/*
 * File Name       : BaseFinder.java
 * Class Name      : BaseFinder
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-02-09 15:38:14
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

/**
 * File Name    : BasicFinder.java
 * Created Date : Mar 10, 2014 9:22:01 PM
 * Author       : adelwin.handoyo
 *
 * Change Log
 * ================|=============|=================================
 * Author          | Change Date | Description
 * ================|=============|=================================
 *
 *
 */
package org.si.diamond.base.file.finder;

import org.si.diamond.base.file.filter.BaseFileNameFilter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BaseFinder {
	
	protected static Logger logger = Logger.getLogger(BaseFinder.class);
	
	public List<File> findDirectory(String location, BaseFileNameFilter filter) {
		logger.info("finding directories in [" + location + "] with filter [" + filter + "]");
		File baseLocation = new File(location);
		File[] filterResult = null;
		if (filter == null) {
			filterResult = baseLocation.listFiles();
		} else {
			filterResult = baseLocation.listFiles(filter);
		}
		
		List<File> returnList = new ArrayList<File>();
		for (File file : filterResult) {
			if (file.isDirectory()) {
				returnList.add(file);
			}
		}
		logger.debug("found [" + returnList.size() + "] items {" + returnList + "}");
		return returnList;
	}

	public List<File> findFiles(String location, BaseFileNameFilter filter) {
		logger.info("finding files in [" + location + "] with filter [" + filter + "]");
		File baseLocation = new File(location);
		File[] filterResult = null;
		if (filter == null) {
			filterResult = baseLocation.listFiles();
		} else {
			filterResult = baseLocation.listFiles(filter);
		}
		
		List<File> returnList = new ArrayList<File>();
		for (File file : filterResult) {
			if (file.isFile()) {
				returnList.add(file);
			}
		}
		logger.debug("found [" + returnList.size() + "] items {" + returnList + "}");
		return returnList;
	}

}
