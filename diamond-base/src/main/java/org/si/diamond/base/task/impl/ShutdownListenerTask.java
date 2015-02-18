/*
 * File Name       : ShutdownListenerTask.java
 * Class Name      : ShutdownListenerTask
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 16:08:49
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

import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.exception.BaseRuntimeException;
import org.si.diamond.base.factory.SpringBeanFactory;
import org.si.diamond.base.task.BaseTask;
import org.si.diamond.base.util.ExceptionUtil;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by adelwin.handoyo on 2014-10-14.
 */
public class ShutdownListenerTask implements BaseTask {

	protected Logger logger = Logger.getLogger(ShutdownListenerTask.class);
	private String triggerFileLocation;

	public String getTriggerFileLocation() {
		return triggerFileLocation;
	}

	public void setTriggerFileLocation(String triggerFileLocation) {
		this.triggerFileLocation = triggerFileLocation;
	}

	@Override
	public void doTask() throws BaseRuntimeException {
		try {
			File triggerFile = new File(this.getTriggerFileLocation());
			if (triggerFile.exists()) {
				logger.debug("shutdown listener task found trigger file exists");
				logger.debug("removing trigger file");
				triggerFile.delete();
				logger.debug("sending signal to stop application");
				SpringBeanFactory.getInstance().close();
				SpringBeanFactory.getInstance().destroy();
			} else {
				logger.debug("shutdown listener task found trigger file does not exist");
			}
		} catch (BaseException e) {
			logger.error("exception occurred");
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new BaseRuntimeException(e.getMessage(), e);
		}
	}
}
