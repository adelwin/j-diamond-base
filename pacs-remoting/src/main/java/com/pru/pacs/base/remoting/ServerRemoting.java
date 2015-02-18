/*
 * File Name       : ServerRemoting.java
 * Class Name      : com.pru.pacs.base.remoting.ServerRemoting
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 12:07:53
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-01-16 12:08 | 2.7.1   | - Initial Version
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package com.pru.pacs.base.remoting;

import com.pru.pacs.base.remoting.exception.RemotingException;

public interface ServerRemoting {

	public abstract String validateUser(String userName, String password) throws RemotingException;
	public abstract String start() throws RemotingException;
	public abstract String stop() throws RemotingException;
	public abstract String status() throws RemotingException;
	public abstract String quit() throws RemotingException;

}
