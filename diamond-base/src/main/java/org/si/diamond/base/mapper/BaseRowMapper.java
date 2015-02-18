/*
 * File Name       : BaseRowMapper.java
 * Class Name      : BaseRowMapper
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

package org.si.diamond.base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class BaseRowMapper<M> extends BeanPropertyRowMapper<M> {
	protected static Logger logger = Logger.getLogger(BaseRowMapper.class);

	public BaseRowMapper() {
		super();
	}

	public BaseRowMapper(Class<M> mappedClass) {
		super(mappedClass);
	}

	public M mapRow(ResultSet rs) throws SQLException {
		return this.mapRow(rs, rs.getRow());
	}
	
}
