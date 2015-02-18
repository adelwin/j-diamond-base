/*
 * File Name       : XmlNode.java
 * Class Name      : XmlNode
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:13:16
 *
 * Copyright (C) 2015 Prudential Assurance Company Singapore. All Rights Reserved. <BR/>
 * This software contains confidential and proprietary information of Prudential Assurance Company Singapore.
 *
 * |=================|==================|=========|======================================
 * | Author          | Date             | Version | Description
 * |=================|==================|=========|======================================
 * | Adelwin Handoyo | 2015-02-03 15:12 | 2.7.2   | fix equality check for xml node
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.xml.element;

import java.util.List;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class XmlNode {
	private String localName;
	private String qualifiedName;
	private XmlNode parent;
	private List<XmlNode> elements;
	private List<NodeAttribute> attributes;
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

	public XmlNode getParent() {
		return parent;
	}

	public void setParent(XmlNode parent) {
		this.parent = parent;
	}

	public List<XmlNode> getElements() {
		return elements;
	}

	public void setElements(List<XmlNode> elements) {
		this.elements = elements;
	}

	public List<NodeAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<NodeAttribute> attributes) {
		this.attributes = attributes;
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
		if (other != null && other instanceof XmlNode) {
			XmlNode otherXmlNode = (XmlNode) other;
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

			boolean resultElements = true;
			if ((this.getElements() == null && otherXmlNode.getElements() == null) || (this.getElements().size() == 0 && otherXmlNode.getElements().size() == 0)) {
				resultElements = true;
			} else if (this.getElements().size() != 0 &&otherXmlNode.getElements().size() != 0 && this.getElements().size() == otherXmlNode.getElements().size()) {
				for (int i = 0; i < this.getElements().size(); i++) {
					resultElements = resultElements && this.getElements().get(i).equals(otherXmlNode.getElements().get(i));
				}
			} else {
				resultElements = false;
			}

			boolean resultAttributes = true;
			if ((this.getAttributes() == null && otherXmlNode.getAttributes() == null) || this.getAttributes().size() == 0 && otherXmlNode.getAttributes().size() == 0) {
				resultAttributes = true;
			} else if (this.getAttributes().size() != 0 && otherXmlNode.getAttributes().size() != 0 && this.getAttributes().size() == ((XmlNode) other).getAttributes().size()) {
				for (int i = 0; i < this.getAttributes().size(); i++) {
					resultAttributes = resultAttributes && this.getAttributes().get(i).equals(otherXmlNode.getAttributes().get(i));
				}
			} else {
				resultAttributes = false;
			}

			return (this.canEqual(other) && resultLocalName && resultQualifiedName && resultUri && resultValue && resultElements && resultAttributes);
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

		final int prime = 31;
		int result = 1;
		result = result * prime + hashLocalName;
		result = result * prime + hashQualifiedName;
		result = result * prime + hashUri;
		result = result * prime + hashValue;

		return result;
	}

	public boolean canEqual(Object other) {
		return (other instanceof XmlNode);
	}
}
