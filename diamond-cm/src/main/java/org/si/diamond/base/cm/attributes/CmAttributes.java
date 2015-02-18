/*
 * File Name       : CmAttributes.java
 * Class Name      : CmAttributes
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
public enum CmAttributes {
	CONTRACT_NUMBER		("Contract_Number"),
	DOC_ID				("DocId"),
	OWNER_ID			("Owner_Id"),
	LIFE_ASSURED_ID_1	("LifeAssured_Id1"),
	LIFE_ASSURED_ID_2	("LifeAssured_Id2"),
	USER_ID				("USER_ID"),
	DATE_OF_SCANNING	("DateOfScanning"),
	TIME_STAMP			("Time_Stamp"),
	MAIN_DOC			("Main_Document"),
	SUB_DOC				("Sub_Document"),
	SOURCE_OF_CHANNEL	("SourceOfChannel"),
	REFERENCE_NO		("Reference_No"),
	OTHER_REF_NO		("Oth_Ref_Num"),
	AGENT_CODE			("Agent_Code");

	private String attributeName;

	private CmAttributes(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public String toString() {
		return this.attributeName;
	}
}
