/*
 * File Name       : SpringBeanFactory.java
 * Class Name      : SpringBeanFactory
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
 * | Adelwin Handoyo | 2014-10-14 13.51 | 2.4.0   | Add destroy close and registerShutdownHook onto Spring Application Context
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.factory;

import java.util.Map;

import org.apache.log4j.Logger;
import org.si.diamond.base.config.ConfigurationUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.si.diamond.base.exception.BaseException;
import org.si.diamond.base.exception.BaseRuntimeException;

public class SpringBeanFactory extends BeanFactory {
	protected Logger logger = Logger.getLogger(SpringBeanFactory.class);
	protected ClassPathXmlApplicationContext springApplicationContext;
	
	private SpringBeanFactory() {
		String springConfigFileNames = ConfigurationUtil.getInstance().getConfigurationValue(ConfigurationUtil.KEY_SPRING_CONFIGFILELOCATION);
		this.init(springConfigFileNames);
	}
	
	private static class SingletonHolder {
		public static final SpringBeanFactory INSTANCE = new SpringBeanFactory();
	}
	
	public static SpringBeanFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public void init(String springConfigFileNames) throws BaseRuntimeException {
		try {
			logger.info("Initializing local spring bean factory");
			logger.info("spring config file names : " + springConfigFileNames);
			String[] configFileNames = springConfigFileNames.split(",");
			springApplicationContext = new ClassPathXmlApplicationContext(configFileNames);
			logger.info("local spring bean factory initialized");
			logger.info("registering shutdown hook to the initialized application context");
			springApplicationContext.registerShutdownHook();
			logger.info("shutdown hook registered");
		} catch (Exception e) {
			logger.error("failed initializing local spring bean factory", e);
			e.printStackTrace();
			throw new BaseRuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * Minimum implementation,
	 * initially provided to delay spring context instantiation as late as possible.
	 * provided to create an abstract layer of bean instantiation on later stage.
	 * @param className
	 * @return
	 * @throws org.si.diamond.base.exception.BaseException
	 */
	public Object getBean(String className) throws BaseException {
		try {
			Object retVal = this.springApplicationContext.getBean(className);
			logger.debug("returning an instance of " + className);
			return retVal;
		} catch (Exception e) {
			logger.error("failed retrieving bean from local factory", e);
			e.printStackTrace();
			throw new BaseException(e.getMessage(), e);
		}
	}

	public String[] getBeans() throws BaseException {
		return this.springApplicationContext.getBeanDefinitionNames();
	}

	public Object getBean(Class<?> clazz) throws BaseException {
		return this.getBean(clazz.getName());
	}
	
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BaseException {
		return this.springApplicationContext.getBeansOfType(type);
	}
	
	public <T> Map<String, ?> getBeansOfType(String type) throws BaseException {
		try {
			return this.getBeansOfType(Class.forName(type));
		} catch (ClassNotFoundException e) {
			throw new BaseException(e.getMessage(), e);
		}
	}

	public void destroy() throws BaseException {
		((ClassPathXmlApplicationContext)this.springApplicationContext).destroy();
	}

	public void close() throws BaseException {
		((ClassPathXmlApplicationContext) this.springApplicationContext).close();
	}

	public void registerShutDownHook() {
		((ClassPathXmlApplicationContext) this.springApplicationContext).registerShutdownHook();
	}
}
