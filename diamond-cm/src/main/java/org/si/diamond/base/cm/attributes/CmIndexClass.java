/*
 * File Name       : CmIndexClass.java
 * Class Name      : CmIndexClass
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
 * | Adelwin Handoyo | 2014-01-15 13:52 | 2.7.1   | Add GBPOLDOC Index Class
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.cm.attributes;

/**
 * Created by adelwin on 27/07/2014.
 */
public enum CmIndexClass {
	BACKFILEDOC		("BACKFILEDOC"),
	POLDOCTEMP		("POLDOCTEMP"),
	POLDOC			("POLDOC"),
	LOOSEMAILS		("LOOSEMAILS"),
	DELETEDDOC		("DELETEDDOC"),
	AGYDOC			("AGYDOC"),
	GBPOLDOC		("GBPOLDOC");

	private String indexClass;

	private CmIndexClass(String indexClass) {
		this.indexClass = indexClass;
	}

	@Override
	public String toString() {
		return this.indexClass;
	}
}
