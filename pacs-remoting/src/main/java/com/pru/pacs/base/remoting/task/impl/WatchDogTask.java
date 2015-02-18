/**
 * 
 */
package com.pru.pacs.cm_archival.task.impl;

import org.apache.log4j.Logger;

import com.pru.pacs.base.exception.BaseException;

/**
 * @author adelwin.handoyo
 *
 */
public class WatchDogTask extends com.pru.pacs.cm_archival.task.RemotingTask {
	protected Logger logger = Logger.getLogger(WatchDogTask.class);

	@Override
	public void doTask() throws BaseException {
		logger.debug("Watch Doc CmArchivingTask Start.");
		logger.info("SCHEDULER ALIVE!");
		logger.debug("Watch Doc CmArchivingTask End");
	}
	
}
