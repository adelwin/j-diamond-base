/*
 * File Name       : BaseQuery.java
 * Class Name      : BaseQuery
 * Module Name     : pacs-base
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

package org.si.diamond.base.query;


public class BaseQuery {
	private StringBuffer query;

	public BaseQuery() {}
	public BaseQuery(String query) {
		this.query = new StringBuffer(query);
	}
	
	public String getQuery() {
		return query.toString();
	}

	public void setQuery(String query) {
		this.query = new StringBuffer(query);
	}
	
}
