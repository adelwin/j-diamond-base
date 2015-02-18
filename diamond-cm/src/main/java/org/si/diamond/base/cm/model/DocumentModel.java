/*
 * File Name       : DocumentModel.java
 * Class Name      : DocumentModel
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

package org.si.diamond.base.cm.model;

import org.si.diamond.base.cm.attributes.CmAttributes;

import java.util.Map;

/**
 * Created by adelwin on 27/07/2014.
 */
public class DocumentModel {
	private final String itemId;
	private final Map<CmAttributes, String> attributes;

	public DocumentModel(String itemId, Map<CmAttributes, String> attributes) {
		this.itemId = itemId;
		this.attributes = attributes;
	}

	public String getItemId() {
		return itemId;
	}

	public Map<CmAttributes, String> getAttributes() {
		return attributes;
	}
}
