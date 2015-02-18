/*
 * File Name       : ServerRemotingClient.java
 * Class Name      : com.pru.pacs.base.remoting.ServerRemotingClient
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 12:08:30
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


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemotingClient extends Remote {
	public abstract String validateUser(String userName, String password) throws RemoteException;
	public abstract void start() throws RemoteException;
	public abstract void stop() throws RemoteException;
	public abstract String status() throws RemoteException;
	public abstract void quit() throws RemoteException;
}
