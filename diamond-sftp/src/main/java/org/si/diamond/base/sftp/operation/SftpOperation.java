/*
 * File Name       : SftpOperation.java
 * Class Name      : SftpOperation
 * Module Name     : pacs-sftp
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

package org.si.diamond.base.sftp.operation;

import org.si.diamond.base.sftp.exception.SftpException;

import java.util.List;

/**
 * Created by adelwin.handoyo on 2014-06-23.
 */
public interface SftpOperation {
	public void download(String remoteFile, String localFile) throws SftpException;
	public void upload(String remoteFile, String localFile) throws SftpException;
	public void removeRemoteFile(String remoteFile) throws SftpException;
	public String getCurrentLocation() throws SftpException;
	public String getCurrentLocalLocation() throws SftpException;
	public List<String> listRemoteFiles() throws SftpException;
	public List<String> listRemoteFiles(String remotePath) throws SftpException;
	public String getLatestFile(String remotePath) throws SftpException;
}
