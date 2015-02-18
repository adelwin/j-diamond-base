/*
 * File Name       : WatchDogTask.java
 * Class Name      : WatchDogTask
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 13:59:46
 *
 * Copyright (C) 2014 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-01-26 19:40 | 2.7.1   | - remove extra logging lines to preserve log rotation
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.task.impl;

import org.si.diamond.base.exception.BaseRuntimeException;
import org.si.diamond.base.task.BaseTask;
import org.apache.log4j.Logger;

/**
 * Created by adelwin.handoyo on 2014-07-31.
 */
public class WatchDogTask implements BaseTask {
	protected static Logger logger = Logger.getLogger(WatchDogTask.class);

	public void doTask() throws BaseRuntimeException {
		logger.info("watch dog task alive");
	}
}
