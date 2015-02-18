/*
 * File Name       : CommandExecutor.java
 * Class Name      : CommandExecutor
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
 * |                 |                  |         |
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.executor;

/**
 * Created by adelwin.handoyo on 2014-07-01.
 */
public abstract class CommandExecutor {
	private String[] commandArray;

	public String[] getCommandArray() {
		return commandArray;
	}

	public void setCommandArray(String[] commandArray) {
		this.commandArray = commandArray;
	}

	public abstract int executeCommand();
}
