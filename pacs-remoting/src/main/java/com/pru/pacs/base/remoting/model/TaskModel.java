package com.pru.pacs.base.remoting.model;

/*
 * File Name       : TaskModel.java
 * Class Name      : TaskModel
 * Module Name     : pacs-remoting
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-16 18:43:22
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

import com.pru.pacs.base.model.BaseModel;

/**
 * Created by adelwin.handoyo on 2015-01-16.
 */
public class TaskModel extends BaseModel {
	private String taskName;
	private String processCron;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessCron() {
		return processCron;
	}

	public void setProcessCron(String processCron) {
		this.processCron = processCron;
	}
}
