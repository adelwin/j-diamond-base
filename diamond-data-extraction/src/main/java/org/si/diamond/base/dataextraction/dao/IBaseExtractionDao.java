/*
 * File Name       : IBaseExtractionDao.java
 * Class Name      : IBaseExtractionDao
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

package org.si.diamond.base.dataextraction.dao;

import org.springframework.jdbc.core.RowCallbackHandler;

import org.si.diamond.base.model.BaseModel;

public interface IBaseExtractionDao<M> extends IBaseDao<BaseModel>, RowCallbackHandler, Runnable {
	
	public void openStream();
	public void closeStream();
	public void extract();
	public void cleanup();
	public String getBeanName();
	
}
