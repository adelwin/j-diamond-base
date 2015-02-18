/*
 * File Name       : CmServerRemotingImpl.java
 * Class Name      : com.pru.pacs.base.remoting.CmServerRemotingImpl
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 12:35:12
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package com.pru.pacs.base.remoting.impl;

import com.pru.pacs.base.remoting.ServerRemoting;
import com.pru.pacs.base.remoting.exception.RemotingException;
import com.pru.pacs.base.remoting.provider.RemotingProvider;
import org.apache.log4j.Logger;

/**
 * Created by adelwin.handoyo on 2015-01-16.
 */
public class CmServerRemotingImpl implements ServerRemoting {
	protected static Logger logger = Logger.getLogger(CmServerRemotingImpl.class);

	private RemotingProvider remotingProvider;
	private String usergroup;

	public RemotingProvider getRemotingProvider() {
		return remotingProvider;
	}

	public void setRemotingProvider(RemotingProvider remotingProvider) {
		this.remotingProvider = remotingProvider;
	}

	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}

	public String validateUser(String userName, String password) throws RemotingException {
		return (this.getRemotingProvider().testLogin(userName, password, this.getUsergroup()) ? "SUCCESS" : "FAIL");
	}

	public String start() throws RemotingException {
		return null;
	}

	public String stop() throws RemotingException {
		return null;
	}

	public String status() throws RemotingException {
		return null;
	}

	public String quit() throws RemotingException {
		return null;
	}
}
