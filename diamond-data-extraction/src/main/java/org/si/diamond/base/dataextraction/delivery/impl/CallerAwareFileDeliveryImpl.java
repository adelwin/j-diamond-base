/*
 * File Name       : CallerAwareFileDeliveryImpl.java
 * Class Name      : CallerAwareFileDeliveryImpl
 * Module Name     : pacs-data-extraction
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:05
 *
 * Copyright (C) 2014 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-02-12 19.49 | 2.8.1   | deprecated class after implementation of Caller Aware segregation of deliverable files
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.delivery.impl;

import org.si.diamond.base.dataextraction.delivery.ICallerAwareBaseDelivery;
import org.si.diamond.base.dataextraction.delivery.IFileDelivery;
import org.si.diamond.base.dataextraction.dao.impl.BaseExtractionDaoImpl;
import org.si.diamond.base.dataextraction.exception.BaseExtractionRuntimeException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;

@Deprecated
public class CallerAwareFileDeliveryImpl implements IFileDelivery, ICallerAwareBaseDelivery {
	
	protected static Logger logger = Logger.getLogger(CallerAwareFileDeliveryImpl.class);
	protected String targetFile;

	public void send() {
		throw new BaseExtractionRuntimeException("Method send() not implemented");
	}

	@Override
	public void send(BaseExtractionDaoImpl caller) {
		try {
			logger.debug("sending output file to specified target location");
			Path sourceFile = Paths.get(caller.getOutputFileName());
			Path targetFile = Paths.get(this.targetFile);
			logger.info("target location is [" + targetFile + "]");
			Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
			logger.debug("output file sent to target location");
		} catch (IOException e) {
			logger.error("failed sending output to the delivery");
			e.printStackTrace();
			throw new BaseExtractionRuntimeException("failed sending output to the delivery", e);
		}
	}

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageContent) {
		logger.error("not implemented for file delivery");
	}

	@Override
	public void sendError(BaseExtractionDaoImpl caller, String messageSubject, String messageContent, Exception exception) {
		logger.error("not implemented for file delivery");
	}

	@Override
	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}
}
