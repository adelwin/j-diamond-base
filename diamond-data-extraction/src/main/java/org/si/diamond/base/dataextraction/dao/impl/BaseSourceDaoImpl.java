/*
 * File Name       : BaseSourceDaoImpl.java
 * Class Name      : BaseSourceDaoImpl
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

import org.si.diamond.base.dataextraction.dao.IBaseSourceDao;
import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.model.BaseModel;
import org.si.diamond.base.query.BaseQuery;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.List;

/**
 * Created by adelwin.handoyo on 2014-06-19.
 */
public class BaseSourceDaoImpl<M extends BaseModel> extends NamedParameterJdbcDaoSupport implements IBaseSourceDao<M> {
	protected Logger logger = Logger.getLogger(BaseSourceDaoImpl.class);
	private BaseQuery query;

	@Override
	public BaseQuery getQuery() {
		return query;
	}

	@Override
	public void setQuery(BaseQuery query) {
		this.query = query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<M> getListByCriteria(M criteria) throws BaseException {
		logger.debug("creating sql parameter source");
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(criteria);
		logger.debug("executing query");
		return (List<M>) this.getNamedParameterJdbcTemplate().query(this.query.getQuery(), sqlParameterSource, BeanPropertyRowMapper.newInstance(criteria.getClass()));
	}
}
