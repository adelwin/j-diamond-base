/*
 * File Name       : WinCommandExecutor.java
 * Class Name      : WinCommandExecutor
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

package org.si.diamond.base.executor;

import org.si.diamond.base.exception.BaseRuntimeException;
import org.si.diamond.base.exception.CommandExecutorException;
import org.si.diamond.base.gobbler.StreamLogger;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adelwin.handoyo on 2014-07-01.
 */
public class WinCommandExecutor extends CommandExecutor {
	protected static Logger logger = Logger.getLogger(WinCommandExecutor.class);

	@Override
	public int executeCommand() {
		logger.debug("executing win command " + Arrays.asList(this.getCommandArray()));

		logger.debug("adding basic windows cmd");
		List<String> commandList = new ArrayList<String>();
		commandList.add("cmd");
		commandList.add("/C");
		commandList.addAll(Arrays.asList(this.getCommandArray()));

		logger.debug("spawning process");
		Runtime runtime;
		Process process = null;
		int returnValue = -1;
		try {
			logger.debug("creating process runtime");
			runtime = Runtime.getRuntime();
			if (runtime != null) {
				logger.debug("running command");
				process = runtime.exec(commandList.toArray(new String[commandList.size()]));
			} else {
				logger.error("failed in retrieving runtime");
				logger.error("throwing base runtime exception");
				throw new BaseRuntimeException("failed in retrieving runtime");
			}

			logger.debug("capturing output and error stream");
			StreamLogger messageLogger = new StreamLogger(process.getInputStream(), logger);
			messageLogger.start();

			StreamLogger errorLogger = new StreamLogger(process.getErrorStream(), logger);
			errorLogger.start();

			logger.debug("waiting for return code");
			returnValue = process.waitFor();
			if (returnValue != 0) {
				logger.error("command executed with error [" + returnValue + "]");
				throw new CommandExecutorException("" + returnValue);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (CommandExecutorException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if (process != null) {
				process.destroy();
			}
			logger.debug(returnValue);
		}
		logger.info("Command executed");
		return returnValue;
	}
}
