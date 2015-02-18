/*
 * File Name       : IBaseEntityDao.java
 * Class Name      : IBaseEntityDao
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

import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.model.BaseModel;
import org.si.diamond.base.query.BaseQuery;

/**
 * @author adelwin
 *
 */
public interface IBaseEntityDao<M extends BaseModel> extends IBaseDao<M> {

	public RowCallbackHandler getRowCallbackhandler();
	public void setRowCallbackhandler(RowCallbackHandler rowCallbackhandler);

	public void executeForRow(BaseQuery query, RowCallbackHandler callbackHandler) throws BaseException;
	public void executeForRow() throws BaseException;
	
	public List<M> executeForList(BaseQuery query, RowMapper<M> rowMapper) throws BaseException;
	public List<M> executeForList() throws BaseException;

}