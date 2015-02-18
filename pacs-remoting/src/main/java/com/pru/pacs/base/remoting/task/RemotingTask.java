package com.pru.pacs.base.remoting.task;

import com.pru.pacs.base.remoting.model.TaskModel;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import com.pru.pacs.base.exception.BaseException;
import com.pru.pacs.base.exception.BaseRuntimeException;
import com.pru.pacs.base.exception.BaseTaskException;
import com.pru.pacs.base.factory.SpringBeanFactory;

public abstract class RemotingTask implements ApplicationContextAware, BeanNameAware, Runnable {
	protected Logger logger = Logger.getLogger(RemotingTask.class);

	protected String beanName;
	protected ApplicationContext applicationContext;
	protected TaskStatus taskStatus;
	protected TaskModel taskModel;
	
	public enum TaskStatus {
		START,
		STOP
	}
	
	public RemotingTask() {
		super();
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanName() {
		return this.beanName;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public synchronized void setTaskStatus(TaskStatus taskStatus) {
		logger.info("setting task status of [" + this.beanName + "] to [" + taskStatus + "]");
		this.taskStatus = taskStatus;
		logger.info("task status of [" + this.beanName + "] set");
	}

	public TaskModel getTaskModel() {
		return taskModel;
	}

	public void setTaskModel(TaskModel taskModel) {
		this.taskModel = taskModel;
	}

	public void init(String taskName) throws BaseTaskException {
		try {
			logger.info("initializing task");
			logger.debug("retrieving cron expression");
			String cronExpr = this.getTaskModel().getProcessCron();
			logger.info("cron expression retrieved as " + cronExpr);
			
			logger.debug("registering task to task scheduler");
			TaskScheduler taskScheduler = (TaskScheduler) SpringBeanFactory.getInstance().getBean("mainScheduler");
			taskScheduler.schedule(new Thread(this, taskName), new CronTrigger(cronExpr));
			logger.info("registered to task scheduler");
		} catch (BaseException e) {
			logger.error("failed to initialize task bean");
			logger.error(e.getMessage(), e);
			throw new BaseRuntimeException(e.getMessage(), e);
		} finally {
			
		}
	}

	public void run() throws BaseRuntimeException {
		try {
			this.doTask();
		} catch (BaseException e) {
			throw new BaseRuntimeException(e.getMessage(), e);
		}
	}

	public abstract void doTask() throws BaseException;

}