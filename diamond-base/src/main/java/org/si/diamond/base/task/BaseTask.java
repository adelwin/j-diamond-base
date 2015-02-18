/*
 * File Name       : BaseTask.java
 * Class Name      : BaseTask
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

package org.si.diamond.base.task;

import org.si.diamond.base.exception.BaseRuntimeException;

/**
 * Created by adelwin.handoyo on 2014-06-30.
 */
public interface BaseTask {
	public void doTask() throws BaseRuntimeException;
}
