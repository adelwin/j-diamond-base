/*
 * File Name       : ConfigurationUtil.java
 * Class Name      : ConfigurationUtil
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
 * | Adelwin Handoyo | 2014-11-20 19:41 | 2.5.0   | Add checks if main configuration file cannot be found
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.config;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.si.diamond.base.util.ClassLoaderUtil;
import org.apache.log4j.Logger;

public class ConfigurationUtil {
	protected static Logger		logger							= Logger.getLogger(ConfigurationUtil.class);

	public static final String	CONFIG_FILE						= "systems.properties";

	public static final String	KEY_SPRING_CONFIGFILELOCATION	= "config.spring.configFileLocation";

	private Properties			configurationProperties			= null;

	private ConfigurationUtil() {
		try {
			this.configurationProperties = new Properties();
			File configFile = new File(CONFIG_FILE);
			if (configFile.exists()) {
				this.configurationProperties.load(ClassLoader.getSystemResourceAsStream(CONFIG_FILE));
			} else {
				this.configurationProperties.load(ClassLoaderUtil.getResourceAsStream(CONFIG_FILE, getClass()));
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw new RuntimeException("Error in instantialing a base singleton");
		}
	}

	private static class SingletonHolder {
		public static final ConfigurationUtil	INSTANCE	= new ConfigurationUtil();
	}

	public static ConfigurationUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public String getConfigurationValue(String key) {
		return this.configurationProperties.getProperty(key);
	}
}
