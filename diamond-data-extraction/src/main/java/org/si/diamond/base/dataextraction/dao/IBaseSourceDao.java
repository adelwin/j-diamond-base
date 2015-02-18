/*
 * File Name       : IBaseSourceDao.java
 * Class Name      : IBaseSourceDao
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

import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.model.BaseModel;

/**
 * @author adelwin
 *
 */
public interface IBaseSourceDao<M extends BaseModel> extends IBaseDao<M> {
	public List<M> getListByCriteria(M criteria) throws BaseException;
}
