/*
 * File Name       : BaseRowCallbackHandler.java
 * Class Name      : BaseRowCallbackHandler
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
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.handler;

import java.io.BufferedWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.si.diamond.base.dataextraction.format.DefaultDataFormat;
import org.si.diamond.base.model.BaseModel;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowCallbackHandler;

import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.mapper.BaseRowMapper;

/**
 * @author adelwin
 * 
 */
public abstract class BaseRowCallbackHandler<M extends BaseModel> implements RowCallbackHandler {
	protected Logger logger = Logger.getLogger(BaseRowCallbackHandler.class);

	private BaseRowMapper<M>		baseRowMapper;
	private String outputFileName;
	private String logFileName;
	private String delimiter;
	private List<DefaultDataFormat> rowFormatList;
	private BufferedWriter localBufferedWriter;

	private Date startTime;
	private Date endTime;
	private String beanName;

	public abstract void processRow(M rowItem) throws SQLException, BaseException;

	public final void processRow(ResultSet resultSet) throws SQLException {
		logger.debug("mapping resultset to row model");
		M rowItem = this.getBaseRowMapper().mapRow(resultSet);
		logger.debug("resultset mapped successfully");
		try {
			this.processRow(rowItem);
		} catch (Exception e) {
			long timeStamp = System.currentTimeMillis();
			logger.error("Internal System Exception and Data of Ref # " + timeStamp + ": " + rowItem.toFlatString(), e);
		}
	}

	/**
	 * Get the <code>baseRowMapper</code>.
	 * 
	 * @return the baseRowMapper
	 */
	public BaseRowMapper<M> getBaseRowMapper() {
		return baseRowMapper;
	}

	/**
	 * Set the <code>baseRowMapper</code>.
	 * 
	 * @param baseRowMapper
	 *            the baseRowMapper to set
	 */
	public void setBaseRowMapper(BaseRowMapper<M> baseRowMapper) {
		this.baseRowMapper = baseRowMapper;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public List<DefaultDataFormat> getRowFormatList() {
		return rowFormatList;
	}

	public void setRowFormatList(List<DefaultDataFormat> rowFormatList) {
		this.rowFormatList = rowFormatList;
	}

	public BufferedWriter getLocalBufferedWriter() {
		return localBufferedWriter;
	}

	public void setLocalBufferedWriter(BufferedWriter localBufferedWriter) {
		this.localBufferedWriter = localBufferedWriter;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

}