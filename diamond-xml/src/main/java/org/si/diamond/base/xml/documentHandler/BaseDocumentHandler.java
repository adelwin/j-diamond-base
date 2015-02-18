/*
 * File Name       : BaseDocumentHandler.java
 * Class Name      : BaseDocumentHandler
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:05:15
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

package org.si.diamond.base.xml.documentHandler;

import org.si.diamond.base.xml.element.NodeAttribute;
import org.si.diamond.base.xml.element.XmlNode;
import org.si.diamond.base.xml.exception.XmlException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class BaseDocumentHandler extends DefaultHandler {
	private XmlNode root;

	private static XmlNode	currentNode;

	private File			document;

	private InputStream		documentStream;

	/**
	 * constructors
	 */
	public BaseDocumentHandler(File file) {
		this.document = file;
	}

	public BaseDocumentHandler(InputStream inputStream) {
		this.documentStream = inputStream;
	}

	/**
	 * getters
	 */
	public XmlNode getRoot() {
		return root;
	}

	public File getDocument() {
		return document;
	}

	public InputStream getDocumentStream() {
		return documentStream;
	}

	/**
	 * setters
	 */
	public void setRoot(XmlNode root) {
		this.root = root;
	}

	public void setDocument(File file) {
		this.document = file;
	}

	public void setDocumentStream(InputStream documentStream) {
		this.documentStream = documentStream;
	}

	/**
	 * supposed to override startDocument method of DefaultHandler <br>
	 * but since the Exceptions from SAX has been wrapped as XMLException then
	 * the method will be renamed as startRoot, <br>
	 * but actually it is the same function<br>
	 * the method handles functions such as setting up initial root for xml
	 * document, and maybe validating the document against the DTD
	 */
	public void startRoot() throws XmlException {
		try {
			startDocument();
		} catch (SAXException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	public void startDocument() throws SAXException {
		// no implementation
	}

	/**
	 * supposed to override endDocument method of DefaultHandler <br>
	 * but since the Exceptions from SAX has been wrapped as XMLException then
	 * the method will be renamed as endRoot, <br>
	 * but actually it is the same function <br>
	 * the method handles functions such as setting up end document, closing
	 * document, and maybe validating the tree made by the parser
	 */
	public void endRoot() throws XmlException {
		try {
			endDocument();
		} catch (SAXException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	public void endDocument() throws SAXException {
		// remove initalization vars
		currentNode = null;
	}

	/**
	 * supposed to override startElement method of DefaultHandler <br>
	 * but since the Exceptions from SAX has been wrapped as XMLException then
	 * the method will be renamed as startNode, <br>
	 * but actually handles the same functions such as instantiating a new
	 * XMLNode for the most recently found node from the xml document, setting
	 * up the attribute, and the child elements
	 */
	public void startNode(String uri, String localName, String qualifiedName, Attributes attributes) throws XmlException {
		try {
			startElement(uri, localName, qualifiedName, attributes);
		} catch (SAXException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {
		if (localName == null || localName.trim().length() == 0) localName = new String(qualifiedName);
		if (root == null) {
			root = new XmlNode();
			root.setParent(null);
			root.setUri(uri);
			root.setLocalName(localName);
			root.setQualifiedName(qualifiedName);
			root.setAttributes(parseAttributes(attributes));
			currentNode = root;
		} else {
			XmlNode node = new XmlNode();
			node.setParent(currentNode);
			node.setUri(uri);
			node.setLocalName(localName);
			node.setQualifiedName(qualifiedName);
			node.setAttributes(parseAttributes(attributes));
			if (node.getParent().getElements() == null) {
				node.getParent().setElements(new ArrayList<XmlNode>());
			}
			node.getParent().getElements().add(node);
			currentNode = node;
		}
	}

	private List<NodeAttribute> parseAttributes(Attributes attributes) {
		List<NodeAttribute> retVal = new ArrayList<NodeAttribute>();
		NodeAttribute attr;
		if (attributes == null) return null;
		for (int i = 0; i < attributes.getLength(); i++) {
			attr = new NodeAttribute();
			attr.setLocalName(attributes.getLocalName(i));
			attr.setQualifiedName(attributes.getQName(i));
			attr.setType(attributes.getType(i));
			attr.setUri(attributes.getURI(i));
			attr.setValue(attributes.getValue(i));
			retVal.add(i, attr);
		}
		return retVal;
	}

	/**
	 * supposed to override endElement method of DefaultHandler <br>
	 * but since the Exceptions from SAX has been wrapped as XMLException then
	 * the method will be renamed as endNode, <br>
	 * but actually handles the same functions
	 */
	public void endNode(String uri, String localName, String qualifiedName) throws XmlException {
		try {
			endElement(uri, localName, qualifiedName);
		} catch (SAXException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	public void endElement(String uri, String localName, String qualifiedName) throws SAXException {
		if (currentNode.getParent() != null) currentNode = currentNode.getParent();
	}

	/**
	 * handler for XML Item
	 */
	public void characters(char[] chars, int offset, int length) throws SAXException {
		String value = new String(chars, offset, length);
		value = value.trim();
		if (value.length() < 1) return;
		XmlNode node = new XmlNode();
		node.setParent(currentNode);
		node.setUri(null);
		node.setLocalName(null);
		node.setQualifiedName(null);
		node.setAttributes(parseAttributes(null));
		node.setValue(value);
		if (node.getParent() == null) {
			return;
		}
		if (node.getParent().getElements() == null) {
			node.getParent().setElements(new ArrayList<XmlNode>());
		}
		node.getParent().getElements().add(node);
	}

	/**
	 * below are methods to support random access to xml nodes<br>
	 *
	 */
	public XmlNode getNode(XmlNode nodeToSearch, XmlNode nodeStartSearch) {
		if (nodeStartSearch.equals(nodeToSearch)) {
			return nodeToSearch;
		} else {
			List<XmlNode> childElements = nodeStartSearch.getElements();
			for (XmlNode node : childElements) {
				XmlNode searchResult = this.getNode(nodeToSearch, node);
				if (searchResult != null) {
					return searchResult;
				}
			}
		}
		return null;
	}

	public XmlNode getNode(XmlNode nodeToSearch) {
		return getNode(nodeToSearch, this.getRoot());
	}

	public Object getAttributeValue(XmlNode node, String name) {
		List<NodeAttribute> attributes = node.getAttributes();
		for (NodeAttribute attribute : attributes) {
			String attributeName = attribute.getLocalName();
			if (name.equalsIgnoreCase(attributeName)) {
				return attribute.getValue();
			}
		}
		return null;
	}

	public Object getAttributeValue(XmlNode node, int index) {
		return node.getAttributes().get(index).getValue();
	}

	public XmlNode getElement(XmlNode parent, String name) {
		List<XmlNode> elements = parent.getElements();
		for (XmlNode element : elements) {
			if (element.getLocalName() != null && element.getLocalName().trim().length() > 0 && element.getLocalName().equalsIgnoreCase(name)) {
				return element;
			}
		}
		return null;
	}

	public XmlNode getElement(XmlNode parent, int index) {
		return parent.getElements().get(index);
	}
}
