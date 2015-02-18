/*
 * File Name       : CmDataSource.java
 * Class Name      : CmDataSource
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
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.cm.datasource;

import com.ibm.mm.sdk.common.*;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import org.si.diamond.base.cm.attributes.CmAttributes;
import org.si.diamond.base.cm.attributes.CmIndexClass;
import org.si.diamond.base.cm.exception.CmException;
import org.si.diamond.base.cm.model.DocumentModel;
import org.si.diamond.base.cm.util.MimeUtil;
import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.util.DateUtil;
import org.si.diamond.base.util.ExceptionUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adelwin.handoyo on 2014-07-21.
 */
public class CmDataSource {
	protected static Logger logger = Logger.getLogger(CmDataSource.class);

	private String userName;
	private String password;
	private String serverName;
	private String userGroup;

	private DKDatastoreICM internalDataSource;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public DKDatastoreICM getInternalDataSource() {
		return internalDataSource;
	}

	public void setInternalDataSource(DKDatastoreICM internalDataSource) {
		this.internalDataSource = internalDataSource;
	}

	public void logon() throws CmException {
		try {
			logger.info("logging on to Content Manager");
			logger.debug("creating new internal data source object");
			this.setInternalDataSource(new DKDatastoreICM());
			logger.debug("internal data source object created");
			logger.debug("running connect method on internal data source object");
			this.getInternalDataSource().connect(this.getServerName(), this.getUserName(), this.getPassword(), "");
			logger.debug("logon complete");
		} catch (Exception e) {
			logger.error("error during logon to content manager", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("error during logon to Content Manager", e);
		}
	}

	public boolean isConnected() throws CmException {
		if (this.getInternalDataSource() == null) {
			return false;
		} else {
			if (this.getInternalDataSource().isConnected() == true) {
				long isConnectionValid = 9;
				try {
					isConnectionValid = this.getInternalDataSource().validateConnection();
				} catch (DKException e) {
					logger.error("exception during validating connection", e);
					logger.error(ExceptionUtil.getStackTraces(e));
				} catch (Exception e) {
					logger.error("exception during validating connection", e);
					logger.error(ExceptionUtil.getStackTraces(e));
				}
				if (isConnectionValid == 0) {
					return true;
				} else {
					this.disconnect();
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public void disconnect() throws CmException {
		logger.info("disconnecting from Content Manager");
		if (this.getInternalDataSource() == null) {
			logger.error("not connected to Content Manager");
		}
		try {
			logger.debug("disconnecting");
			this.getInternalDataSource().disconnect();
			logger.debug("disconnected");
			logger.debug("destroying connection");
			this.getInternalDataSource().destroy();
			logger.debug("connection destroyed");
		} catch (DKException e) {
			logger.error("exception during disconnect", e);
			logger.error(ExceptionUtil.getStackTraces(e));
		} catch (Exception e) {
			logger.error("exception during disconnect", e);
			logger.error(ExceptionUtil.getStackTraces(e));
		}
		this.setInternalDataSource(null);
		logger.info("disconnected from Content Manager");
	}

	public void connect() throws CmException {
		logger.info("Checking existing connections to Content Manager");
		if (this.isConnected()) {
			logger.debug("already connected to Content Manager");
		} else {
			logger.debug("not yet connected to Content Manager");
			logger.debug("checking parameters for connections");
			if (this.getUserName() == null || this.getUserName().trim().length() == 0) {
				throw new CmException("User Name not provided, unable to log-on to Content Manager");
			}
			if (this.getPassword() == null || this.getPassword().trim().length() == 0) {
				throw new CmException("Password not provided, unable to log-on to Content Manager");
			}
			if (this.getServerName() == null || this.getServerName().trim().length() == 0) {
				throw new CmException("Server Name not provided, unable to log-on to Content Manager");
			}
			if (this.getUserGroup() == null || this.getUserGroup().trim().length() == 0) {
				logger.warn("User Group not available, validating without the user group checking");
			}
			logger.debug("parameters checked");
			logger.debug("logging on to Content Manager");
			this.logon();
		}
		logger.info("connection established");
	}

	public List<DocumentModel> evaluate(String queryString, String maxResult) throws CmException {
		try {
			logger.debug("building options array");
			DKNVPair[] options = null;
			if (maxResult == null) {
				options = new DKNVPair[2];
			} else {
				options = new DKNVPair[3];
			}
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, DKConstant.DK_CM_CONTENT_ATTRONLY);
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);

			if (maxResult != null && maxResult.trim().length() > 0 && Integer.parseInt(maxResult) > 0) {
				options[2] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, maxResult);
			}

			logger.debug("running the query string ==>" + queryString + "<==");
			if (!this.isConnected()) this.connect();
			DKResults results = (DKResults) this.getInternalDataSource().evaluate(queryString.toString(), DKConstant.DK_CM_XQPE_QL_TYPE, options);

			List<DocumentModel> documentModelList = new ArrayList<DocumentModel>();

			logger.debug("running iterator [" + results.cardinality() + "]");
			dkIterator iterator = results.createIterator();
			long count = 0;

			while (iterator.more()) {
				DKDDO dkDocument = (DKDDO) iterator.next();
				String itemId = ((DKPidICM) dkDocument.getPidObject()).getItemId();
				logger.debug("found an item with id [" + itemId + "]");
				logger.debug("retrieving attributes");
				Map<CmAttributes, String> attributeValues = new HashMap<CmAttributes, String>();
				for (CmAttributes attr : CmAttributes.values()) {
					String value = null;
					try {
						Object attributeValue = null;
						try {
							attributeValue = dkDocument.getDataByName(attr.toString());
						} catch (Exception e) {
						}
						value = this.getAttributeAsString(attr, attributeValue);
					} catch (Exception e) {
						logger.error("exception retrieving attribute [" + attr + "] by name on item id [" + itemId + "]", e);
						logger.error(ExceptionUtil.getStackTraces(e));
					}
					if (value != null && value.trim().length() > 0) {
						attributeValues.put(attr, value);
					}
				}
				logger.debug("attributes retrieved");
				DocumentModel cmDocument = new DocumentModel(itemId, attributeValues);
				documentModelList.add(cmDocument);
				count++;
			}

			logger.debug("found [" + count + "] items");
			logger.debug("returning list");
			return documentModelList;
		} catch (Exception e) {
			logger.error("exception during validating evaluate", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during evaluate", e);
		}
	}

	public String extractToDisk(String itemId, String targetPath, boolean overwrite) throws CmException {
		try {
			logger.debug("retrieving the DDO item for [" + itemId + "]");
			DKDDO ddoItem = getDDO(itemId);
			logger.debug("set to retrieve content only");
			ddoItem.retrieve(DKConstantICM.DK_CM_CONTENT_ONLY);

			logger.debug("preparing full absolute file name for the result");
			StringBuffer partPath = new StringBuffer();
			partPath.append(new File(targetPath).getAbsolutePath());
			partPath.append(File.separator);
			partPath.append(((DKPidICM) ddoItem.getPidObject()).getItemId());

			logger.debug("getting parts of DDO");
			DKParts dkParts = (DKParts) (dkCollection) ddoItem.getData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, DKConstant.DK_CM_DKPARTS));
			logger.debug("iterating parts");
			dkIterator iterator = dkParts.createIterator();

			while (iterator.more()) {
				DKLobICM part = (DKLobICM) iterator.next();
				if (part.getPidObject().getObjectType().equalsIgnoreCase("ICMBASE")) {
					part.retrieve();
					String mime = part.getMimeType();
					String extension = MimeUtil.getExtension(mime);
					partPath.append(".").append(extension);
					logger.debug("target file set as [" + partPath.toString() + "]");

					logger.debug("checking if existing file exists");
					File targetFile = new File(partPath.toString());
					if (targetFile.exists()) {
						logger.warn("existing file exist");
						if (overwrite == true) {
							if (targetFile.canWrite()) {
								logger.debug("writing file");
								part.getContentToClientFile(partPath.toString(), DKLobICM.DK_CM_XDO_FILE_OVERWRITE);
							} else {
								logger.error("file cannot be written onto");
								throw new CmException("file cannot be written onto");
							}
						} else {
							logger.error("file exists and cannot be overwritten");
							throw new CmException("file exists and cannot be overwritten");
						}
					} else {
						logger.debug("existing file does not exist");
						logger.debug("writing file");
						part.getContentToClientFile(partPath.toString(), DKLobICM.DK_CM_XDO_FILE_OVERWRITE);
					}
					return partPath.toString();
				}
			}
			return null;
		} catch (DKUsageError e) {
			logger.error("exception during validating extraction", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during extraction", e);
		} catch (DKException e) {
			logger.error("exception during validating extraction", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during extraction", e);
		} catch (Exception e) {
			logger.error("exception during validating extraction", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("exception during extraction", e);
		}
	}

	protected DKAttrDefICM getAttributeDefinition(CmAttributes attributeName) throws BaseException {
		try {
			DKDatastoreDefICM dataStoreDefinition = (DKDatastoreDefICM) this.getInternalDataSource().datastoreDef();
			DKAttrDefICM attributeDefinition = (DKAttrDefICM) dataStoreDefinition.retrieveAttr(attributeName.toString());
			if (attributeDefinition == null) {
				throw new BaseException("Failed retrieving attribute definition of [" + attributeName + "]");
			}
			return attributeDefinition;
		} catch (DKException e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	protected String getAttributeAsString(CmAttributes attributeName, Object attributeValue) throws CmException {
		try {
			if (attributeName == null) {
				throw new CmException("attribute name provided was empty");
			}
			if (attributeValue == null) {
				return null;
			}
			short attributeType = this.getAttributeDefinition(attributeName).getType();
			if (attributeType == DKConstant.DK_CM_DATE) {
				DKDate date = (DKDate) attributeValue;
				return DateUtil.getDateAsString(date.getSQLDateObject(), "yyyy-MM-dd");
			} else {
				return attributeValue.toString();
			}
		} catch (BaseException e) {
			logger.error(e.getMessage(), e);
			throw new CmException(e.getMessage(), e);
		}
	}

	protected void setAttributes(DKDDO ddoItem, Map<CmAttributes, String> attributes) throws CmException {
		logger.debug("setting attribute for ddo item to be checked in");
		try {
			for (CmAttributes key : attributes.keySet()) {
				String value = attributes.get(key);
				logger.debug("setting value [" + value + "] as [" + key + "]");
				if (value == null || value.trim().length() == 0) {
					logger.debug("key [" + key + "] has null value, skipping");
//					ddoItem.setNull(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key));
				} else {
					switch (this.getAttributeDefinition(key).getType()) {
						case DKConstant.DK_CM_BLOB:
							logger.debug("attribute type is BLOB");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), value.getBytes(value));
							break;
						case DKConstant.DK_CM_CHAR:
							logger.debug("attribute type is CHAR");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new String(value));
							break;
						case DKConstant.DK_CM_CLOB:
							logger.debug("attribute type is CLOB");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new String(value));
							break;
						case DKConstant.DK_CM_DATE:
							logger.debug("attribute type is DATE");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), java.sql.Date.valueOf(value));
							break;
						case DKConstant.DK_CM_DECIMAL:
							logger.debug("attribute type is DECIMAL");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new java.math.BigDecimal(value));
							break;
						case DKConstant.DK_CM_DOUBLE:
							logger.debug("attribute type is DOUBLE");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new Double(value));
							break;
						case DKConstant.DK_CM_INTEGER:
							logger.debug("attribute type is INT");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new Integer(value));
							break;
						case DKConstant.DK_CM_SHORT:
							logger.debug("attribute type is SHORT");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new Short(value));
							break;
						case DKConstant.DK_CM_TIME:
							logger.debug("attribute type is TIME");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), java.sql.Time.valueOf(value));
							break;
						case DKConstant.DK_CM_TIMESTAMP:
							logger.debug("attribute type is TIMESTAMP");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), java.sql.Timestamp.valueOf(value));
							logger.debug(java.sql.Timestamp.valueOf(value));
							break;
						case DKConstant.DK_CM_VARCHAR:
							logger.debug("attribute type is VARCHAR");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new String(value));
							break;
						default:
							logger.debug("attribute type is others");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key.toString()), new String(value));
							break;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("error occurred during setting attributes of DDO Item", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("error occurred during setting attributes of DDO Item", e);
		} catch (DKUsageError e) {
			logger.error("error occurred during setting attributes of DDO Item", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("error occurred during setting attributes of DDO Item", e);
		} catch (Throwable t) {
			logger.error("error occurred during setting attributes of DDO Item", t);
			logger.error(ExceptionUtil.getStackTraces(t));
			throw new CmException("error occurred during setting attributes of DDO Item", t);
		}
	}

	public String archive(File file, Map<CmAttributes, String> attributes, CmIndexClass targetIndexClass) throws CmException {
		try {
			logger.debug("archiving file [" + file.getName() + "] with attributes: " + attributes + " to [" + targetIndexClass + "]");
			logger.debug("starting transaction");
			this.getInternalDataSource().startTransaction();
			logger.debug("creating DDO");
			DKDDO ddoItem = this.getInternalDataSource().createDDO(targetIndexClass.toString(), DKConstant.DK_CM_DOCUMENT);
			DKLobICM resource = (DKLobICM) this.getInternalDataSource().createDDO("ICMBASE", 128);

			logger.debug("creating parts");
			DKParts dkParts = (DKParts) ddoItem.getData(ddoItem.dataId("ATTR", "DKParts"));
			dkParts.addElement(resource);
			resource.setMimeType(MimeUtil.getType(file));

			resource.setContentFromClientFile(file.getAbsolutePath());

			logger.debug("setting attributes");
			this.setAttributes(ddoItem, attributes);

			logger.debug("adding item");
			ddoItem.add();
			logger.debug("retrieving item id");
			String itemId = ddoItem.getPidObject().pidString();
			logger.debug("item added to content manager as [" + itemId + "]");

			logger.debug("committing transaction");
			this.getInternalDataSource().commit();
			logger.debug("transaction committed");

			return itemId;
		} catch (Exception e) {
			logger.error("failed archiving file [" + file.getName() + "]");
			try {
				this.getInternalDataSource().rollback();
			} catch (Exception e1) {
				logger.error("failed rolling back transaction");
				logger.error(e.getMessage(), e);
			}
			throw new CmException(e.getMessage(), e);
		}
	}

	private DKDDO getDDO(String itemId) throws CmException {
		try {
			logger.debug("retrieving DDO Object of the specified Item Id [" + itemId + "]");
			DKDDO ddo = null;

			String query = "/*[@ITEMID=\"" + itemId + "\"]";
			logger.debug("searcing content manager for the item id, query string is ==>" + query + "<==");

			DKNVPair options[] = new DKNVPair[3];
			options[0] = new DKNVPair(DKConstant.DK_CM_PARM_MAX_RESULTS, "1");
			options[1] = new DKNVPair(DKConstant.DK_CM_PARM_RETRIEVE, new Integer(DKConstant.DK_CM_CONTENT_ATTRONLY));
			options[2] = new DKNVPair(DKConstant.DK_CM_PARM_END, null);

			DKResults results = (DKResults) this.getInternalDataSource().evaluate(query, DKConstantICM.DK_CM_XQPE_QL_TYPE, options);

			if (results.cardinality() != 1) {
				logger.error("error, expected one and only one result, did not get one result");
				throw new CmException("error while evaluating itemId [" + itemId + "] in getDDO method, expected one and only one result, found [" + results.cardinality() + "] items");
			}

			logger.debug("Item Id evaluated");

			dkIterator iterator = results.createIterator();
			ddo = (DKDDO) iterator.next();
			logger.debug("returning DDO item");
			return ddo;
		} catch (DKException e) {
			logger.error("error occurred during getDDO method", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("error while retrieving DDO item for [" + itemId + "]", e);
		} catch (Exception e) {
			logger.error("error occurred during getDDO method", e);
			logger.error(ExceptionUtil.getStackTraces(e));
			throw new CmException("error while retrieving DDO item for [" + itemId + "]", e);
		}
	}
}
