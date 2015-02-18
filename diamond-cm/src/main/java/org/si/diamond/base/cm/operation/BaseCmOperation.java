/*
 * File Name       : BaseCmOperation.java
 * Class Name      : BaseCmOperation
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

package org.si.diamond.base.cm.operation;

import org.si.diamond.base.cm.attributes.CmAttributes;
import org.si.diamond.base.cm.attributes.CmIndexClass;
import org.si.diamond.base.cm.attributes.CmOrderSequence;
import org.si.diamond.base.cm.exception.CmException;
import org.si.diamond.base.cm.model.DocumentModel;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by adelwin on 26/07/2014.
 */
public interface BaseCmOperation {
	public String archive(File file, Map<CmAttributes, String> attributes, CmIndexClass targetIndexClass) throws CmException;
	public Map<String, String> archive(Map<String, File> files, Map<String, Map<CmAttributes, String>> attributes, CmIndexClass targetIndexClass) throws CmException;
	public String extract(String cmItemId, String outputPath, boolean overwrite) throws CmException;
	public List<DocumentModel> searchByAttributes(Map<CmAttributes, String> attributes, Map<CmAttributes, CmOrderSequence> orderSequence, CmIndexClass targetIndexClass, String maxResult, String... additionalQuery) throws CmException;
}
