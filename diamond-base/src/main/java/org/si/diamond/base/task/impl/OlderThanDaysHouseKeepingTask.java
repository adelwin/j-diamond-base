/*
 * File Name       : OlderThanDaysHouseKeepingTask.java
 * Class Name      : OlderThanDaysHouseKeepingTask
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

package org.si.diamond.base.task.impl;

import org.si.diamond.base.exception.BaseRuntimeException;
import org.si.diamond.base.task.BaseTask;
import org.si.diamond.base.util.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by adelwin.handoyo on 2014-07-31.
 */
public class OlderThanDaysHouseKeepingTask implements BaseTask {
	protected static Logger logger = Logger.getLogger(OlderThanDaysHouseKeepingTask.class);

	private String searchLocation;
	private String targetLocation;
	private String olderThanDays;

	public String getSearchLocation() {
		return searchLocation;
	}

	public void setSearchLocation(String searchLocation) {
		this.searchLocation = searchLocation;
	}

	public String getTargetLocation() {
		return targetLocation;
	}

	public void setTargetLocation(String targetLocation) {
		this.targetLocation = targetLocation;
	}

	public String getOlderThanDays() {
		return olderThanDays;
	}

	public void setOlderThanDays(String olderThanDays) {
		this.olderThanDays = olderThanDays;
	}

	public void doTask() throws BaseRuntimeException {
		logger.debug("finding files that fits criteria");
		File searchLocationDirectory = new File(this.getSearchLocation());
		logger.debug("searching file from location [" + searchLocationDirectory.getAbsolutePath() + "]");

		logger.debug("threshold is [" + this.getOlderThanDays() + "] days");
		logger.debug("calculating threshold date");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, Integer.parseInt("-" + this.getOlderThanDays()));
		logger.debug("stripping time component");
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Date thresholdDate = calendar.getTime();
		logger.debug("threshold date is [" + DateUtil.getDateAsString(thresholdDate, "yyyy-MM-dd-HH-mm-ss") + "]");

		if (searchLocationDirectory.isDirectory()) {
			logger.debug("search location is a directory");
			logger.debug("searching");

			Collection<File> filesToDelete  = FileUtils.listFiles(searchLocationDirectory, new AgeFileFilter(thresholdDate), null);

			logger.debug("found [" + filesToDelete.size() + "] files");
			logger.debug("iterating");

			for (File file : filesToDelete) {
				logger.info("removing file [" + file.getName() + "]");
				if (this.getTargetLocation() != null && this.getTargetLocation().trim().length() != 0) {
					logger.debug("target location is available, moving to target location");
					try {
						file.renameTo(new File(this.getTargetLocation() + File.separator + file.getName()));
					} catch (Exception e) {
						logger.error("failed moving file to target location");
						logger.error(e.getMessage(), e);
						throw new BaseRuntimeException("failed moving file [" + file.getAbsolutePath() + "] to target location [" + this.getTargetLocation() + "]", e);
					} finally {
					}
				} else {
					logger.debug("target location is not available, deleting the file");
					try {
						file.delete();
					} catch (Exception e) {
						logger.error("failed to delete the file");
						logger.error(e.getMessage(), e);
						throw new BaseRuntimeException("failed to delete the file [" + file.getAbsolutePath() + "]", e);
					} finally {
					}
				}
			}
			logger.debug("complete");
		} else {
			logger.error("search location is a file, not a directory");
			throw new BaseRuntimeException("search location is not a directory");
		}
	}
}
