/*
 * File Name       : ICallerAwareBaseDelivery.java
 * Class Name      : ICallerAwareBaseDelivery
 * Module Name     : pacs-data-extraction
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:05
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

package org.si.diamond.base.dataextraction.delivery;

import org.si.diamond.base.dataextraction.dao.impl.BaseExtractionDaoImpl;

public interface ICallerAwareBaseDelivery extends IBaseDelivery {
	public void send(BaseExtractionDaoImpl caller);
	public void sendError(BaseExtractionDaoImpl caller, String messageContent);
	public void sendError(BaseExtractionDaoImpl caller, String messageSubject, String messageContent, Exception exception);
}
