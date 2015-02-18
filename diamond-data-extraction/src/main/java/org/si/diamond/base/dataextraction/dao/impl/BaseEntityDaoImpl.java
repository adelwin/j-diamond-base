/*
 * File Name       : BaseEntityDaoImpl.java
 * Class Name      : BaseEntityDaoImpl
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

package org.si.diamond.base.dataextraction.dao.impl;

import org.si.diamond.base.dataextraction.dao.IBaseEntityDao;
import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.model.BaseModel;
import org.si.diamond.base.query.BaseQuery;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;

/**
 * Created by adelwin.handoyo on 2014-06-19.
 */
public class BaseEntityDaoImpl<M extends BaseModel> extends JdbcDaoSupport implements IBaseEntityDao<M> {

		private BaseQuery query;
		private RowMapper<M> rowMapper;
		private RowCallbackHandler rowCallbackhandler;

		@Override
		public RowCallbackHandler getRowCallbackhandler() {
			return rowCallbackhandler;
		}

		@Override
		public void setRowCallbackhandler(RowCallbackHandler rowCallbackhandler) {
			this.rowCallbackhandler = rowCallbackhandler;
		}

		public BaseQuery getQuery() {
			return query;
		}

		public void setQuery(BaseQuery query) {
			this.query = query;
		}

		public RowMapper<M> getRowMapper() {
			return rowMapper;
		}

		public void setRowMapper(RowMapper<M> rowMapper) {
			this.rowMapper = rowMapper;
		}

		@Override
		public final void executeForRow(BaseQuery query, RowCallbackHandler callbackHandler) throws BaseException {
			if (this.rowCallbackhandler == null) {
				throw new BaseException("Query for Row was executed, but Row Callback Handler was not defined");
			}
			this.getJdbcTemplate().query(query.getQuery(), callbackHandler);
		}

		public final void executeForRow() throws BaseException {
			this.executeForRow(this.query, this.rowCallbackhandler);
		}

		@Override
		public final List<M> executeForList(BaseQuery query, RowMapper<M> rowMapper) throws BaseException {
			if (this.rowMapper == null) {
				throw new BaseException("Query for List was executed, but RowMapper was not defined");
			}
			return this.getJdbcTemplate().query(query.getQuery(), rowMapper);
		}

		public final List<M> executeForList() throws BaseException {
			return this.executeForList(this.query, this.rowMapper);
		}
}
