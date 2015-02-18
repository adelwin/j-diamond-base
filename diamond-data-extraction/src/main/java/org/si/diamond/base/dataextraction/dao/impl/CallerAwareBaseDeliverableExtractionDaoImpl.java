/*
 * File Name       : CallerAwareBaseDeliverableExtractionDaoImpl.java
 * Class Name      : CallerAwareBaseDeliverableExtractionDaoImpl
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

import org.si.diamond.base.dataextraction.delivery.ICallerAwareBaseDelivery;
import org.si.diamond.base.model.BaseModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;

public abstract class CallerAwareBaseDeliverableExtractionDaoImpl<M extends BaseModel> extends BaseExtractionDaoImpl<BaseModel> implements BeanNameAware, Runnable {
	protected static Logger			logger				= Logger.getLogger(CallerAwareBaseDeliverableExtractionDaoImpl.class);

	private ICallerAwareBaseDelivery delivery;

	/**
	 * @return the delivery
	 */
	public ICallerAwareBaseDelivery getDelivery() {
		return delivery;
	}

	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(ICallerAwareBaseDelivery delivery) {
		this.delivery = delivery;
	}

	public void extract() {
		logger.debug("Calling base extraction");
		super.extract();
		logger.debug("extraction complete");
		logger.debug("calling send");
		if (this.getDelivery() != null) {
			this.getDelivery().send(this);
		}
		logger.debug("delivery complete");
	}

}
