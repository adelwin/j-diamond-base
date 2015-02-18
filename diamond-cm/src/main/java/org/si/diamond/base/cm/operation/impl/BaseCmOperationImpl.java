/*
 * File Name       : BaseCmOperationImpl.java
 * Class Name      : BaseCmOperationImpl
 * Module Name     : pacs-cm
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
 * | Adelwin Handoyo | 2015-01-16 09:42 | 2.7.1   | - Fix return variable for batch archival
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.cm.operation.impl;

import org.si.diamond.base.cm.attributes.CmAttributes;
import org.si.diamond.base.cm.attributes.CmIndexClass;
import org.si.diamond.base.cm.attributes.CmOrderSequence;
import org.si.diamond.base.cm.datasource.CmDataSource;
import org.si.diamond.base.cm.exception.CmException;
import org.si.diamond.base.cm.model.DocumentModel;
import org.si.diamond.base.cm.operation.BaseCmOperation;
import org.si.diamond.base.util.ExceptionUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.*;

/**
 * Created by adelwin on 26/07/2014.
 */
public class BaseCmOperationImpl implements BaseCmOperation {
	protected static Logger logger = Logger.getLogger(BaseCmOperationImpl.class);
	private CmDataSource dataSource;

	public CmDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(CmDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Archives the specified file to Content Manager to the specified targetIndexClass
	 * Using the attributes provided
	 * @param file
	 * @param attributes
	 * @param targetIndexClass
	 * @return
	 * @throws org.si.diamond.base.cm.exception.CmException
	 */
	@Override
	public String archive(File file, Map<CmAttributes, String> attributes, CmIndexClass targetIndexClass) throws CmException {
		try {
			logger.debug("archiving file [" + file.getName() + "] with attributes: " + attributes + " to [" + targetIndexClass + "]");
			logger.debug("checking if file exist");
			if (!file.exists()) {
				throw new CmException("file does not exist");
			}
			if (!file.isFile()) {
				throw new CmException("input not a file");
			}

			logger.debug("checking attributes");
			if (attributes == null || attributes.size() == 0) {
				throw new CmException("Cm Attributes provided is empty");
			}

			return this.getDataSource().archive(file, attributes, targetIndexClass);
		} catch (Exception e) {
			logger.error("failed archiving file [" + file.getName() + "]");
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("failed archiving file [" + file.getName() + "]", e);
		}
	}

	/**
	 * Archives a list of files
	 * @param files
	 * @param attributes
	 * @param targetIndexClass
	 * @return
	 * @throws CmException
	 */
	@Override
	public Map<String, String> archive(Map<String, File> files, Map<String, Map<CmAttributes, String>> attributes, CmIndexClass targetIndexClass) throws CmException {
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			logger.debug("archiving a map of files containing [" + files.size() + "] file count");
			for (String key : files.keySet()) {
				File currentFile = files.get(key);
				Map<CmAttributes, String> currentAttributes = attributes.get(key);
				String resultItemId = this.archive(currentFile, currentAttributes, targetIndexClass);
				returnMap.put(key, resultItemId);
			}
		} catch (Exception e) {
			logger.error("failed archiving files");
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("failed archiving file", e);
		}
		return returnMap;
	}

	/**
	 * Extracts documents from CM with the provided Item Id<BR/>
	 * Result will be placed in outputFile<BR/>
	 * Resulting file will be named based on the item id, with the extension derived fromt he Mime Type stored in Content Manager
	 * Overwrite flag will determine if an exception will be thrown when the file to be written already exist
	 * @param cmItemId
	 * @param outputPath
	 * @param overwrite
	 * @throws CmException
	 */
	@Override
	public String extract(String cmItemId, String outputPath, boolean overwrite) throws CmException {
		try {
			logger.info("extracting content manager item to disk");

			if (cmItemId == null || cmItemId.trim().length() == 0) {
				throw new CmException("No CM Item Id specified");
			}
			if (outputPath == null || !(new File (outputPath)).canWrite()) {
				throw new CmException("no output path specified, or not writable");
			}
			if (overwrite == false && (new File (outputPath)).isFile()) {
				throw new CmException("output path specified already exist");
			}

			logger.info("Extracting item [" + cmItemId + "] to path [" + outputPath + "]");
			return this.getDataSource().extractToDisk(cmItemId, outputPath, overwrite);
		} catch (Exception e) {
			logger.error("exception during extract", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during extraction to disk", e);
		}
	}

	/**
	 * Searches CM by the attributes in the index class provided.<BR/>
	 * Attributes are provided in a key value pair, where the possible key values are dictated by the CmAttributes Enumerator
	 * Returns a list of CM ITEM ID matching the search criteria
	 *
	 * @param attributes       attributes to be used as search criteria
	 * @param orderSequence    sequence to sort the result
	 * @param targetIndexClass the index class to search from
	 * @return lists of CM ITEM ID matching the search criteria
	 * @throws CmException
	 */
	@Override
	public List<DocumentModel> searchByAttributes(Map<CmAttributes, String> attributes, Map<CmAttributes, CmOrderSequence> orderSequence, CmIndexClass targetIndexClass, String maxResult, String... additionalQuery) throws CmException {
		try {
			logger.info("searching content manager");

			/**
			 * validating parameter
			 */
			if (targetIndexClass == null) {
				throw new CmException("no target index class specified");
			}
			if (attributes == null || attributes.size() == 0) {
				throw new CmException("no attributes criteria specified");
			}

			logger.debug("building query string");
			StringBuffer queryString = new StringBuffer();
			queryString.append("/" + targetIndexClass + "[");

			Iterator<CmAttributes> attributesIterator = attributes.keySet().iterator();
			while (attributesIterator.hasNext()) {
				CmAttributes attribute = attributesIterator.next();
				queryString.append("@").append(attribute).append(" = ").append("\"" + attributes.get(attribute) + "\"");
				if (attributesIterator.hasNext()) {
					queryString.append(" AND ");
				}
			}

			logger.debug("appending additional query");
			for (String query : additionalQuery) {
				queryString.append(" ").append(query).append(" ");
			}
			queryString.append("]");

			if (orderSequence != null && orderSequence.size() > 0) {
				logger.debug("adding sorting query");
				queryString.append(" SORTBY(");
				Iterator<CmAttributes> orderSequenceIterator = orderSequence.keySet().iterator();
				while (orderSequenceIterator.hasNext()) {
					CmAttributes order = orderSequenceIterator.next();
					queryString.append("@").append(order).append(" ");
					if (orderSequence.get(order).toString().equalsIgnoreCase("ASC")) {
					} else {
						queryString.append(orderSequence.get(order).toString());
					}

					if (orderSequenceIterator.hasNext()) {
						queryString.append(", ");
					}
				}
				queryString.append(")");
			}
			logger.info("searching Content Manager with String [" + queryString + "]");
			return this.getDataSource().evaluate(queryString.toString(), maxResult);
		} catch (Exception e) {
			logger.error("exception during search", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during search by attributes", e);
		}
	}
}
