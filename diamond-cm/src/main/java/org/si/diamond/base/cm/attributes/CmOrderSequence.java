/*
 * File Name       : CmOrderSequence.java
 * Class Name      : CmOrderSequence
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

package org.si.diamond.base.cm.attributes;

/**
 * Created by adelwin on 27/07/2014.
 */
public enum CmOrderSequence {
	ASC		("ASC"),
	DESC	("DESC");

	private String orderSequenceName;

	private CmOrderSequence(String orderSequenceName) {
		this.orderSequenceName = orderSequenceName;
	}

	@Override
	public String toString() {
		return this.orderSequenceName;
	}
}
