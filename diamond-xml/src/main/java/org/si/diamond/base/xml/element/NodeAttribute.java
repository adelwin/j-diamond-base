/*
 * File Name       : NodeAttribute.java
 * Class Name      : NodeAttribute
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:12:34
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

package org.si.diamond.base.xml.element;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class NodeAttribute {
	private String localName;
	private String qualifiedName;
	private String type;
	private String uri;
	private String value;

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Support for equality method
	 */
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof NodeAttribute) {
			NodeAttribute otherXmlNode = (NodeAttribute) other;
			boolean resultLocalName = (   (this.getLocalName() == null && otherXmlNode.getLocalName() == null)
									   || (   (this.getLocalName()!= null && otherXmlNode.getLocalName() != null)
										   && (this.getLocalName().equalsIgnoreCase(otherXmlNode.getLocalName()))));

			boolean resultQualifiedName = (   (this.getQualifiedName() == null && otherXmlNode.getQualifiedName() == null)
										   || (   (this.getQualifiedName()!= null && otherXmlNode.getQualifiedName() != null)
											   && (this.getQualifiedName().equalsIgnoreCase(otherXmlNode.getQualifiedName()))));

			boolean resultUri = (   (this.getUri() == null && otherXmlNode.getUri() == null)
								 || (   (this.getUri()!= null && otherXmlNode.getUri() != null)
									 && (this.getUri().equalsIgnoreCase(otherXmlNode.getUri()))));

			boolean resultValue = (   (this.getValue() == null && otherXmlNode.getValue() == null)
								   || (   (this.getValue()!= null && otherXmlNode.getValue() != null)
									   && (this.getValue().equalsIgnoreCase(otherXmlNode.getValue()))));

			boolean resultType = (   (this.getType() == null && otherXmlNode.getType() == null)
								  || (   (this.getType()!= null && otherXmlNode.getType() != null)
									  && (this.getType().equalsIgnoreCase(otherXmlNode.getType()))));

			return (this.canEqual(other) && resultLocalName && resultQualifiedName && resultUri && resultValue && resultType);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hashLocalName = this.getLocalName().hashCode();
		int hashQualifiedName = this.getQualifiedName().hashCode();
		int hashUri = this.getUri().hashCode();
		int hashValue = this.getValue().hashCode();
		int hashType = this.getType().hashCode();

		final int prime = 31;
		int result = 1;
		result = result * prime + hashLocalName;
		result = result * prime + hashQualifiedName;
		result = result * prime + hashUri;
		result = result * prime + hashValue;
		result = result * prime + hashType;

		return result;
	}

	public boolean canEqual(Object other) {
		return (other instanceof NodeAttribute);
	}

}
