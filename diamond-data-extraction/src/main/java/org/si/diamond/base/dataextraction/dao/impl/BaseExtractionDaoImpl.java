/*
 * File Name       : BaseExtractionDaoImpl.java
 * Class Name      : BaseExtractionDaoImpl
 * Module Name     : pacs-data-extraction
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
 * | Adelwin Handoyo | 2014-10-14 09:55 | 2.4.0   | package refactoring, moving all data extraction dao to data extraction package
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.dataextraction.dao.impl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.si.diamond.base.dataextraction.format.*;
import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.mapper.BaseRowMapper;
import org.si.diamond.base.query.CustomQuery;
import org.si.diamond.base.util.BeanUtil;
import org.si.diamond.base.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import org.si.diamond.base.model.BaseModel;
import org.si.diamond.base.query.BaseQuery;
import org.si.diamond.base.util.StringUtil;
import org.si.diamond.base.dataextraction.dao.IBaseExtractionDao;
import org.si.diamond.base.dataextraction.exception.BaseExtractionRuntimeException;

public abstract class BaseExtractionDaoImpl<M> extends JdbcDaoSupport implements IBaseExtractionDao<BaseModel>, BeanNameAware, Runnable {
	protected static Logger			logger				= Logger.getLogger(BaseExtractionDaoImpl.class);

	private BaseQuery				query;
	private String					outputFileName;
	private String					logFileName;
	private String					delimiter;
	private String                  countHeader;
	private List<DefaultDataFormat>	rowFormatList;
	private BaseRowMapper<M>		rowMapper;
	private BufferedWriter			localBufferedWriter;

	private String					finalOutputFileName;
	private String					finalLogFileName;

	private Date					startTime;
	private Date					endTime;
	private String					beanName;

	private static final String	DEFAULT_DELIMITER	= ",";

	private int					ctlRowsExported		= 0;

	public BaseQuery getQuery() {
		return query;
	}

	public void setQuery(BaseQuery query) {
		this.query = query;
	}

	public String getOutputFileName() {
		/**
		 * if the value is not provided or empty, will try to infer a location
		 * in the current working directory.
		 */
		if (StringUtil.isEmptyString(this.outputFileName)) {
			this.outputFileName = this.beanName + ".dat";
		}
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	public String getLogFileName() {
		/**
		 * if the value is not provided or empty, will try to infer a location
		 * in the current working directory.
		 */
		if (StringUtil.isEmptyString(this.logFileName)) {
			this.logFileName = this.beanName + ".log01";
		}
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getDelimiter() {
		/**
		 * if the value is not provided or empty, will try to infer the default
		 * delimiter
		 */
		if (StringUtil.isEmptyString(this.delimiter)) {
			this.delimiter = BaseExtractionDaoImpl.DEFAULT_DELIMITER;
		}
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

	public int getCtlRowsExported() {
		return ctlRowsExported;
	}

	public void setCtlRowsExported(int ctlRowsExported) {
		this.ctlRowsExported = ctlRowsExported;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public BaseRowMapper<M> getRowMapper() {
		return rowMapper;
	}

	public void setRowMapper(BaseRowMapper<M> rowMapper) {
		this.rowMapper = rowMapper;
	}

	public String getCountHeader() {
		return countHeader;
	}

	public void setCountHeader(String countHeader) {
		this.countHeader = countHeader;
	}

	public String getFinalOutputFileName() {
		return finalOutputFileName;
	}

	public void setFinalOutputFileName(String finalOutputFileName) {
		this.finalOutputFileName = finalOutputFileName;
	}

	public String getFinalLogFileName() {
		return finalLogFileName;
	}

	public void setFinalLogFileName(String finalLogFileName) {
		this.finalLogFileName = finalLogFileName;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowCallbackHandler#processRow(java.sql.ResultSet)
	 */
	public void processRow(ResultSet resultSet) throws SQLException {
		logger.debug("Process Row Result Set");
		this.ctlRowsExported++;
		this.printToFile(resultSet);
	}

	public void processRow(String key, String value) {
		logger.debug("Process Row key value");
		this.ctlRowsExported++;
		this.printToFile(new StringBuffer().append(key).append(this.getDelimiter()).append(value));
	}

	public void processRow(M model) throws BaseException {
		logger.debug("Process Row Base Model");
		this.ctlRowsExported++;
		this.printToFile(model);
	}

	public void extract() {
		this.startTime = new Date();
		this.setCtlRowsExported(0);
		logger.debug(this.beanName + " Extraction Start :: " + this.startTime);
		logger.debug("Temp OutputFile :: " + this.getOutputFileName() + "_tmp");

		logger.debug("Opening Stream to output file");
		this.openStream();

		logger.debug("Printing File Header");
		this.printHeader();

		logger.debug("Iterating Rows");
		if (this.query != null) {
			if (this.getQuery() instanceof BaseQuery) {
				logger.debug("Query parameter exists, running query as data");
				this.getJdbcTemplate().query(this.query.getQuery(), this);
			} else if (this.getQuery() instanceof CustomQuery) {

			}
		}

		logger.debug("Closing Stream to output file");
		this.closeStream();

		logger.debug("Total # of rows Extracted = " + this.ctlRowsExported);

		this.endTime = new Date();
		logger.debug(this.beanName + " Extraction Ends :: " + this.endTime);

		logger.debug("renaming to final output file");
		String finalOutputFileName = new String(this.getOutputFileName());
		File originalOutputFileName = new File(this.getOutputFileName());

		finalOutputFileName = finalOutputFileName.replace("@TIME", DateUtil.getDateAsString(new Date(), "HHmmss"));
		finalOutputFileName = finalOutputFileName.replace("@DATE", DateUtil.getDateAsString(new Date(), "ddMMyyyy"));
		finalOutputFileName = finalOutputFileName.replace("@COUNT", "" + this.ctlRowsExported);
		this.finalOutputFileName = finalOutputFileName;
		logger.debug("final output file name is [" + this.finalOutputFileName + "]");

		try {
			originalOutputFileName.renameTo(new File(this.finalOutputFileName));
			logger.debug("renaming success");
		} catch (Exception e) {
			logger.error("renaming failed");
			logger.error(e.getMessage(), e);
		}

		String finalLogFileName = this.getLogFileName();
		finalLogFileName = finalLogFileName.replace("@TIME", DateUtil.getDateAsString(new Date(), "HHmmss"));
		finalLogFileName = finalLogFileName.replace("@DATE", DateUtil.getDateAsString(new Date(), "ddMMyyyy"));
		finalLogFileName = finalLogFileName.replace("@COUNT", "" + this.ctlRowsExported);
		this.finalLogFileName = finalLogFileName;

		logger.debug("Log File :: " + this.finalLogFileName);
		logger.debug("Printing ControlFile");
		this.printControlFile();
		logger.debug("ControlFile Printed");
		
	}

	public void cleanup() {
		logger.debug("clean up is not implemented at base class");
	}

	protected void printHeader() {
		StringBuffer header = new StringBuffer();
		for (int i = 0; i < this.rowFormatList.size(); i++) {
			DefaultDataFormat rowFormat = (DefaultDataFormat) this.rowFormatList.get(i);
			if (header.length() != 0) {
				header.append(this.getDelimiter());
			}
			if (rowFormat.getLength() == 0) {
				header.append(rowFormat.getColumnName());
			} else {
				if (rowFormat instanceof NumberDataFormat) {
					header.append(StringUtil.padString(rowFormat.getColumnName(), rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_INFRONT));
				} else if (rowFormat instanceof AlphaDataFormat) {
					header.append(StringUtil.padString(rowFormat.getColumnName(), rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_ATREAR));
				}
			}
		}

		if (this.getCountHeader() == null || this.getCountHeader().trim().length() == 0 || this.getCountHeader().trim().equalsIgnoreCase("No")) {

		} else {
			this.ctlRowsExported++;
		}

		this.printToFile(header);
	}

	public void openStream() {
		try {
			this.localBufferedWriter = new BufferedWriter(new FileWriter(this.getOutputFileName()));
		} catch (IOException e) {
			logger.error("failed opening stream");
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		}
	}

	public void closeStream() {
		try {
			this.localBufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		}
	}

	protected void printToFile(ResultSet resultSet) {
		try {
			StringBuffer details = new StringBuffer();
			for (DefaultDataFormat rowFormat : rowFormatList) {
				if (details.length() != 0 && rowFormat.getPad() == null) {
					details.append(this.getDelimiter());
				}

				String value = new String();
				if (rowFormat instanceof AlphaDataFormat) {
					value = rowFormat.format(resultSet.getString(rowFormat.getColumnName()));
				} else if (rowFormat instanceof NumberDataFormat) {
					value = ((DecimalDataFormat) rowFormat).format(resultSet.getDouble(rowFormat.getColumnName()));
				} else if (rowFormat instanceof DateDataFormat) {
					value = rowFormat.format(resultSet.getDate(rowFormat.getColumnName()));
				}

				if (value == null) {
					value = "";
				}

				value = value.replaceAll("\n", "__");

				//if pad attribute is found then this must a for fixed-in-length format
				if (rowFormat.getPad() != null) {
					if ("N".equalsIgnoreCase(rowFormat.getPosition())) {
						details.append(value);
					} else {
						int pos = rowFormat.getPosition().equalsIgnoreCase("L") ? StringUtil.PAD_INFRONT : StringUtil.PAD_ATREAR;
						details.append(StringUtil.padString(value, rowFormat.getLength(), rowFormat.getPad(), pos));
					}
					continue;
				}

				if (rowFormat.getLength() == 0) {
					details.append(value);
				} else {
					if (rowFormat instanceof NumberDataFormat) {
						details.append(StringUtil.padString(value, rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_INFRONT));
					} else if (rowFormat instanceof AlphaDataFormat || rowFormat instanceof DateDataFormat) {
						details.append(StringUtil.padString(value, rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_ATREAR));
					}
				}
			}
			printToFile(details);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		}
	}

	protected void printToFile(M model) {
		try {
			StringBuffer details = null;
			for (DefaultDataFormat rowFormat : rowFormatList) {
				if (details == null) {
					details = new StringBuffer();
				} else {
					details.append(this.getDelimiter());
				}

				String propertyName = rowFormat.getPropertyName();
				String value = null;
				try {
					value = (String) BeanUtil.getProperty(model, propertyName);
				} catch (NoSuchMethodException e) {
					logger.error("cannot file property named ["+ propertyName + "]", e);
					value = null;
				}

				if (value == null || value.length() == 0)
					value = "";

				value = value.trim();

				if (rowFormat.getLength() == 0) {
					details.append("\"" + value + "\"");
				} else {
					if (rowFormat instanceof NumberDataFormat) {
						details.append("\"" + StringUtil.padString(value, rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_INFRONT) + "\"");
					} else if (rowFormat instanceof AlphaDataFormat || rowFormat instanceof DateDataFormat) {
						details.append("\"" + StringUtil.padString(value, rowFormat.getLength(), StringUtil.WHITE_SPACE, StringUtil.PAD_ATREAR) + "\"");
					}
				}
			}
			printToFile(details);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		}
	}

	protected void printToFile(StringBuffer stringBuffer) {
		if (this.localBufferedWriter == null) {
			throw new BaseExtractionRuntimeException("Output stream closed for writing");
		}
		try {
			this.localBufferedWriter.write(stringBuffer.toString());
			String osName = System.getProperty("os.name");
			if (osName.startsWith("Windows")) {
				this.localBufferedWriter.write("\n");
			} else {
				this.localBufferedWriter.newLine();
			}
			this.localBufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new BaseExtractionRuntimeException(e.getMessage(), e);
		}
	}

	public void printControlFile() {
		try {
			logger.debug("creating buffered writer");
			BufferedWriter controlFileBufferedWriter = new BufferedWriter(new FileWriter(this.finalLogFileName));
			logger.debug("buffered writer created");

			controlFileBufferedWriter.write("Extraction Process Name     :: " + this.beanName);
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			controlFileBufferedWriter.write("Extraction Output File Name :: " + this.finalOutputFileName);
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			controlFileBufferedWriter.write("Extraction Start Time       :: " + this.startTime);
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			controlFileBufferedWriter.write("Extraction End Time         :: " + this.endTime);
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			controlFileBufferedWriter.write("Extraction Row Count        :: " + this.ctlRowsExported);
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			controlFileBufferedWriter.write("MD5 CheckSum on file        :: " + this.createMD5SumOnFile());
			controlFileBufferedWriter.newLine();
			logger.debug("line written");

			logger.debug("flush");
			controlFileBufferedWriter.flush();
			logger.debug("closing buffered writer");
			controlFileBufferedWriter.close();
			logger.debug("buffered writer closed");

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	protected String createMD5SumOnFile() throws NoSuchAlgorithmException, IOException {
		InputStream fis = new FileInputStream(this.finalOutputFileName);
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		byte[] finalChecksum = complete.digest();
		String result = "";
		for (int i = 0; i < finalChecksum.length; i++) {
			result += Integer.toString((finalChecksum[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public final void run() {
		this.extract();
	}

}
