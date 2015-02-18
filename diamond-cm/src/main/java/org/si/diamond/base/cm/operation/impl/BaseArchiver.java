/*
 * File Name       : BaseArchiver.java
 * Class Name      : BaseArchiver
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

package org.si.diamond.base.cm.operation.impl;

import com.ibm.mm.sdk.common.*;
import com.ibm.mm.sdk.server.DKDatastoreICM;
import org.si.diamond.base.cm.util.MimeUtil;
import org.si.diamond.base.exception.BaseException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by adelwin.handoyo on 2014-07-21.
 */
public abstract class BaseArchiver {
	protected Logger logger = Logger.getLogger(BaseArchiver.class);

	private DKDatastoreICM cmDataStore = null;
	private String docStoreUser;
	private String docStorePassword;
	private String docStoreServerName;
	private String docStoreGroup;

	public DKDatastoreICM getCmDataStore() {
		return cmDataStore;
	}

	public void setCmDataStore(DKDatastoreICM cmDataStore) {
		this.cmDataStore = cmDataStore;
	}

	public String getDocStoreUser() {
		return docStoreUser;
	}

	public void setDocStoreUser(String docStoreUser) {
		this.docStoreUser = docStoreUser;
	}

	public String getDocStorePassword() {
		return docStorePassword;
	}

	public void setDocStorePassword(String docStorePassword) {
		this.docStorePassword = docStorePassword;
	}

	public String getDocStoreServerName() {
		return docStoreServerName;
	}

	public void setDocStoreServerName(String docStoreServerName) {
		this.docStoreServerName = docStoreServerName;
	}

	public String getDocStoreGroup() {
		return docStoreGroup;
	}

	public void setDocStoreGroup(String docStoreGroup) {
		this.docStoreGroup = docStoreGroup;
	}

	public void logon(String username, String password, String serverName) throws BaseException {
		try {
			logger.debug("creating ICM Data Store");
			cmDataStore = new DKDatastoreICM();
			logger.debug("logging in with supplied credentials [" + username + "@" + serverName + "]");
			cmDataStore.connect(serverName, username, password, "");
			logger.debug("connected");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	public void connect() throws BaseException {
		logger.debug("connecting to Content Manager");
		logon(this.getDocStoreUser(), this.getDocStorePassword(), this.getDocStoreServerName());

	}

	public void disconnect() throws BaseException {
		try {
			logger.debug("disconnecting");
			cmDataStore.disconnect();
			logger.debug("destroying");
			if (cmDataStore != null) cmDataStore.destroy();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	public boolean isConnectionValid() throws BaseException {
		try {
			long connectionStatus = cmDataStore.validateConnection();
			return connectionStatus == 0;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	public boolean isConnected() throws BaseException {
		try {
			if (cmDataStore == null) {
				return false;
			} else if ( !isConnectionValid() ) {
				try {
					cmDataStore.disconnect();
				} catch (Exception e) {
					logger.error("disconnect failed", e);
				}
				try {
					cmDataStore.destroy();
				} catch (Exception e) {
					logger.error("destroy failed", e);
				}
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	protected String getMimeType(String filename) {
		String extension = (filename.substring(filename.lastIndexOf(".")+ 1)).toLowerCase();
		return MimeUtil.getType(extension);
	}

	protected DKAttrDefICM getAttributeDefinition(String attributeName) throws BaseException {
		logger.debug("retrieving attribute type of [" + attributeName + "]");
		try {
			DKDatastoreDefICM dataStoreDefinition = (DKDatastoreDefICM) cmDataStore.datastoreDef();
			DKAttrDefICM attributeDefinition = (DKAttrDefICM) dataStoreDefinition.retrieveAttr(attributeName);
			if (attributeDefinition == null) {
				throw new BaseException("Failed retrieving attribute definition of [" + attributeName + "]");
			}
			logger.debug("returning attribute definition of [" + attributeName + "] as [" + attributeDefinition.getType() + "]");
			return attributeDefinition;
		} catch (DKException e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		}
	}

	protected void setAttributes(DKDDO ddoItem, Map<String, String> attributes) throws BaseException {
		logger.debug("setting attribute for ddo item to be checked in");
		try {
			for (String key : attributes.keySet()) {
				String value = attributes.get(key);
				logger.debug("setting value [" + value + "] as [" + key + "]");
				if (value == null || value.trim().length() == 0) {
					logger.debug("key [" + key + "] has null value, skipping");
//					ddoItem.setNull(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key));
				} else {
					switch (this.getAttributeDefinition(key).getType()) {
						case DKConstant.DK_CM_BLOB:
							logger.debug("attribute type is BLOB");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), value.getBytes(value));
							break;
						case DKConstant.DK_CM_CHAR:
							logger.debug("attribute type is CHAR");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new String(value));
							break;
						case DKConstant.DK_CM_CLOB:
							logger.debug("attribute type is CLOB");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new String(value));
							break;
						case DKConstant.DK_CM_DATE:
							logger.debug("attribute type is DATE");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), java.sql.Date.valueOf(value));
							break;
						case DKConstant.DK_CM_DECIMAL:
							logger.debug("attribute type is DECIMAL");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new java.math.BigDecimal(value));
							break;
						case DKConstant.DK_CM_DOUBLE:
							logger.debug("attribute type is DOUBLE");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new Double(value));
							break;
						case DKConstant.DK_CM_INTEGER:
							logger.debug("attribute type is INT");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new Integer(value));
							break;
						case DKConstant.DK_CM_SHORT:
							logger.debug("attribute type is SHORT");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new Short(value));
							break;
						case DKConstant.DK_CM_TIME:
							logger.debug("attribute type is TIME");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), java.sql.Time.valueOf(value));
							break;
						case DKConstant.DK_CM_TIMESTAMP:
							logger.debug("attribute type is TIMESTAMP");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), java.sql.Timestamp.valueOf(value));
							logger.debug(java.sql.Timestamp.valueOf(value));
							break;
						case DKConstant.DK_CM_VARCHAR:
							logger.debug("attribute type is VARCHAR");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new String(value));
							break;
						default:
							logger.debug("attribute type is others");
							ddoItem.setData(ddoItem.dataId(DKConstant.DK_CM_NAMESPACE_ATTR, key), new String(value));
							break;
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		} catch (DKUsageError e) {
			logger.error(e.getMessage(), e);
			throw new BaseException(e.getMessage(), e);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}
	}

	public String[] archive(File file, Map<String, String> attributes, String indexClass) {
		try {
			logger.debug("archiving file [" + file.getName() + "] with attributes: " + attributes + " to [" + indexClass + "]");
			logger.debug("checking if file exist");
			if (!file.exists()) {
				throw new Exception("file does not exist");
			}
			if (!file.isFile()) {
				throw new Exception("input not a file");
			}

			String itemId;

			if ( !this.isConnected() ) {
				this.connect();
			}

			logger.debug("starting transaction");
			this.cmDataStore.startTransaction();

			DKDDO ddoItem = cmDataStore.createDDO(indexClass, DKConstant.DK_CM_DOCUMENT);
			DKLobICM resource = (DKLobICM) cmDataStore.createDDO("ICMBASE", 128);

			DKParts dkParts = (DKParts) ddoItem.getData(ddoItem.dataId("ATTR", "DKParts"));
			dkParts.addElement(resource);
			resource.setMimeType(getMimeType(file.getAbsolutePath()));

			resource.setContentFromClientFile(file.getAbsolutePath());

			logger.debug("setting attributes");
			this.setAttributes(ddoItem, attributes);

			logger.debug("adding item");
			ddoItem.add();
			logger.debug("retrieving item id");
			itemId = ddoItem.getPidObject().pidString();
			logger.debug("item added to content manager as [" + itemId + "]");

			logger.debug("committing transaction");
			cmDataStore.commit();
			logger.debug("transaction committed");

			return new String[] {"SUCCESS", itemId};
		} catch (Exception e) {
			logger.error("failed adding item to CM");
			logger.error(e.getMessage(), e);
			try {
				cmDataStore.rollback();
			} catch (Exception e1) {
				logger.error("failed rolling back transaction");
				logger.error(e.getMessage(), e);
			}
			return new String[] {"FAIL", e.getMessage()};
		}
	}

	private String[] archiveSingle(File file, Map<String, String> attributes, String targetIndexClass) throws BaseException {
		try {
			logger.debug("archiving file [" + file.getName() + "] with attributes: " + attributes + " to [" + targetIndexClass + "]");
			logger.debug("checking if file exist");
			if (!file.exists()) {
				throw new BaseException("file does not exist");
			}
			if (!file.isFile()) {
				throw new BaseException("input not a file");
			}

			DKDDO ddoItem = cmDataStore.createDDO(targetIndexClass, DKConstant.DK_CM_DOCUMENT);
			DKLobICM resource = (DKLobICM) cmDataStore.createDDO("ICMBASE", 128);

			DKParts dkParts = (DKParts) ddoItem.getData(ddoItem.dataId("ATTR", "DKParts"));
			dkParts.addElement(resource);
			resource.setMimeType(getMimeType(file.getAbsolutePath()));

			resource.setContentFromClientFile(file.getAbsolutePath());

			logger.debug("setting attributes");
			this.setAttributes(ddoItem, attributes);

			logger.debug("adding item");
			ddoItem.add();
			logger.debug("retrieving item id");
			String itemId = ddoItem.getPidObject().pidString();
			logger.debug("item added to content manager as [" + itemId + "]");

			logger.debug("committing transaction");
			cmDataStore.commit();
			logger.debug("transaction committed");

			return new String[] {"SUCCESS", itemId};
		} catch (Exception e) {
			logger.error("failed archiving file [" + file.getName() + "]");
			try {
				cmDataStore.rollback();
			} catch (Exception e1) {
				logger.error("failed rolling back transaction");
				logger.error(e.getMessage(), e);
			}
			throw new BaseException(e.getMessage(), e);
		}
	}

	public List<String[]> archiveBatch(List<File> files, List<Map<String, String>> attributes, List<String> indexClass) {
		try {
			List<String[]> returnList = new ArrayList<String[]>();

			logger.debug("archiving batch with the size of [" + files.size() + "]");

			logger.debug("checking parameter integrity");
			int fileCount = files.size();
			int attributeCount = attributes.size();
			int indexClassCount = indexClass.size();

			if (fileCount != attributeCount || attributeCount != indexClassCount || fileCount != indexClassCount) {
				logger.warn("parameter counts are not consistent");
				throw new IllegalArgumentException("parameter counts are not consistent");
			}

			if ( !this.isConnected() ) {
				this.connect();
			}

			logger.debug("starting transaction");
			this.cmDataStore.startTransaction();

			for (int i = 0; i < files.size(); i++) {
				File currentFile = files.get(i);
				Map<String, String> currentAttributes = attributes.get(i);
				String currentIndexClass = indexClass.get(i);

				String[] result;
				try {
					result = archiveSingle(currentFile, currentAttributes, currentIndexClass);
				} catch (BaseException e) {
					logger.error("failed in archive single");
					e.printStackTrace();
					result = new String[] {"FAIL", e.getMessage()};
				}

				returnList.add(result);
			}

			logger.debug("process complete, committing transaction");
			this.cmDataStore.commit();
			return returnList;
		} catch (Exception e) {
			logger.error("failed adding item to CM");
			logger.error(e.getMessage(), e);
			try {
				cmDataStore.rollback();
			} catch (Exception e1) {
				logger.error("error rolling back transaction");
				logger.error(e.getMessage(), e);
			}
			return null;
		}
	}

	public boolean testLogin(String username, String password) {

		try {
			logger.debug("starting test login");
			logger.debug("creating duplicate Data Store");
			DKDatastoreICM cmDataStoreTest = new DKDatastoreICM();
			logger.debug("connecting on the duplicate data store");
			cmDataStoreTest.connect(this.docStoreServerName, username, password, "");

			logger.debug("creating User Mgmt");
			DKUserMgmtICM userManagement = new DKUserMgmtICM(cmDataStoreTest);
			logger.debug("listing user on group " + this.docStoreGroup);
			dkCollection users = userManagement.listUsersInGroup(this.docStoreGroup);
			logger.debug("user list retrieved");
			for (dkIterator iterator = users.createIterator(); iterator.more();) {
				DKUserDefICM userInfo = (DKUserDefICM) iterator.next();
				if (userInfo.getName().equalsIgnoreCase(username)) {
					return true;
				}
			}
			return false;
		} catch (DKException e) {
			logger.error("failed test login");
			logger.error(e.getMessage(), e);
			return false;
		} catch (Exception e) {
			logger.error("failed test login");
			logger.error(e.getMessage(), e);
			return false;
		} finally {
		}
	}

}
