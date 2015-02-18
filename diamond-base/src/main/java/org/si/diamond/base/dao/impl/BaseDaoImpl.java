/*
 * File Name       : BaseDaoImpl.java
 * Class Name      : BaseDaoImpl
 * Module Name     : pacs-base
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 10:00:35
 *
 * Copyright (C) 2014 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2014-10-14 10.04 | 2.4.0   | Add Mybatis Base Dao
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

/**
 * File Name    : BaseDaoImpl.java
 * Author       : adelwin
 * Created Date : Jan 23, 2011 11:41:35 AM
 */
package org.si.diamond.base.dao.impl;

import org.si.diamond.base.dao.IBaseDao;
import org.si.diamond.base.exception.BaseDaoException;
import org.si.diamond.base.model.BaseModel;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.io.Serializable;
import java.util.List;

public class BaseDaoImpl<ID extends Serializable, M extends BaseModel> extends SqlSessionDaoSupport implements IBaseDao<ID, M> {
	protected Logger logger = Logger.getLogger(BaseDaoImpl.class);
	protected Class<? extends BaseModel> modelClass;

	/**
	 * @return the modelClass
	 */
	public Class<? extends BaseModel> getModelClass() {
		return modelClass;
	}

	/**
	 * @param modelClass the modelClass to set
	 */
	public void setModelClass(Class<? extends BaseModel> modelClass) {
		this.modelClass = modelClass;
	}
	
	public StringBuffer getModelClassName() {
		return new StringBuffer(this.modelClass.getName());
	}

	@SuppressWarnings("unchecked")
	public M getById(ID id) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".getById").toString());
		return (M) this.getSqlSession().selectOne(this.getModelClassName().append(".getById").toString(), id);
	}

	@SuppressWarnings("unchecked")
	public List<M> getListByExample(M criterion) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".getListByExample").toString());
		return (List<M>) this.getSqlSession().selectList(this.getModelClassName().append(".getListByExample").toString(), criterion);
	}

	@SuppressWarnings("unchecked")
	public List<M> listAll() throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".listAll").toString());
		return (List<M>) this.getSqlSession().selectList(this.getModelClassName().append(".listAll").toString());
	}

	public int insert(M model) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".insert").toString());
		return this.getSqlSession().insert(this.getModelClassName().append(".insert").toString(), model);
	}

	public int updateFull(M model) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".updateFull").toString());
		return this.getSqlSession().update(this.getModelClassName().append(".updateFull").toString(), model);
	}

	public int updatePartial(M model, M sample) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".updatePartial").toString());
		return this.getSqlSession().update(this.getModelClassName().append(".updatePartial").toString(), model);
	}

	public int delete(M model) throws BaseDaoException {
		logger.debug("trying to execute statement : " + this.getModelClassName().append(".delete").toString());
		return this.getSqlSession().delete(this.getModelClassName().append(".delete").toString(), model);
	}
	
	@SuppressWarnings("unchecked")
	public List<M> customSelect(M criterion, String queryName) throws BaseDaoException {
		return this.getSqlSession().selectList(queryName, criterion);
	}

	/* (non-Javadoc)
	 * @see org.si.diamond.base.dao.IBaseDao#customExecute(org.si.diamond.base.model.BaseModel, java.lang.String)
	 */
	public int customExecute(M criterion, String queryName) throws BaseDaoException {
		return this.getSqlSession().update(queryName, criterion);
	}
}
