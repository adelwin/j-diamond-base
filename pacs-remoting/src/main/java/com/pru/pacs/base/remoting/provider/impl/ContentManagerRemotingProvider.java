/*
 * File Name       : ContentManagerRemotingProvider.java
 * Class Name      : com.pru.pacs.base.remoting.provider.impl.ContentManagerRemotingProvider
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 18:23:15
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

package com.pru.pacs.base.remoting.provider.impl;

import com.pru.pacs.base.remoting.exception.RemotingException;
import com.pru.pacs.base.remoting.provider.RemotingProvider;

/**
 * Created by adelwin.handoyo on 2015-01-16.
 */
public class ContentManagerRemotingProvider extends RemotingProvider {

	@Override
	public String quit() throws RemotingException {
		return null;
	}

	@Override
	public String status() throws RemotingException {
		return null;
	}

	@Override
	public String stop() throws RemotingException {
		return null;
	}

	@Override
	public String start() throws RemotingException {
		return null;
	}

	@Override
	public String listen() throws RemotingException {
		return null;
	}

	@Override
	public String testLogin(String username, String password, String usergroup) throws RemotingException {
		return null;
	}
}
