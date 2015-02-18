/*
 * File Name       : AuthenticationProvider.java
 * Class Name      : com.pru.pacs.base.remoting.com.pru.pacs.base.remoting.provider.AuthenticationProvider
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 12:15:26
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

package com.pru.pacs.base.remoting.provider;

import com.pru.pacs.base.remoting.exception.RemotingException;

/**
 * Created by adelwin.handoyo on 2015-01-16.
 */
public abstract class RemotingProvider {

	public abstract String testLogin(String username, String password, String usergroup) throws RemotingException;
	public abstract String listen() throws RemotingException;
	public abstract String start() throws RemotingException;
	public abstract String stop() throws RemotingException;
	public abstract String status() throws RemotingException;
	public abstract String quit() throws RemotingException;

}
