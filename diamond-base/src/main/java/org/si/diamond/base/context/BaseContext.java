/*
 * File Name       : BaseContext.java
 * Class Name      : BaseContext
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

package org.si.diamond.base.context;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class BaseContext implements Serializable {

	private static final long serialVersionUID = 8359523658098312976L;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
	public Set<String> getKeySet() {
		return attributes.keySet();
	}
	
	public boolean containsKey(String key) {
		return attributes.containsKey(key);
	}
	
	public boolean containsValue(Object value) {
		return attributes.containsValue(value);
	}
	
	public void clearAttributes() {
		this.attributes.clear();
	}
	
	public Collection<Object> getValues() {
		return attributes.values();
	}
	
	public void remove(String key) {
		this.attributes.remove(key);
	}
	
	
}
