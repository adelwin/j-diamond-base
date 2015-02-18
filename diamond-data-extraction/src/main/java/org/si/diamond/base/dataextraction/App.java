/*
 * File Name       : App.java
 * Class Name      : App
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

package org.si.diamond.base.dataextraction;

import org.si.diamond.base.dataextraction.dao.impl.BaseExtractionDaoImpl;
import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.factory.SpringBeanFactory;
import org.si.diamond.base.dataextraction.dao.IBaseExtractionDao;
import org.apache.log4j.Logger;

import java.util.Map;

public class App {
	protected static Logger logger = Logger.getLogger(App.class);

	public static void main(String[] args) throws BaseException {
		logger.debug("Application Started");

		logger.debug("listing down all registered beans of type BaseExtractionDao");
		Map<String, ?> beanList = SpringBeanFactory.getInstance().getBeansOfType(BaseExtractionDaoImpl.class);
		logger.debug("Beans of type BaseExtractionDao retrieved, size = " + beanList.size());

		for (String beanName : beanList.keySet()) {
			IBaseExtractionDao beanElement = (IBaseExtractionDao) beanList.get(beanName);
			logger.debug("Processing Bean of Name " + beanName);
			Thread thread = new Thread(beanElement);
			thread.setName(beanName);
			logger.debug("Launching Bean of Name " + beanName);
			thread.start();
		}
	}
}
