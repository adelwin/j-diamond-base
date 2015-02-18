/*
 * File Name       : SaxGenericXmlWriter.java
 * Class Name      : SaxGenericXmlWriter
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:22:59
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

package org.si.diamond.base.xml.writer;

import org.si.diamond.base.xml.documentHandler.BaseDocumentHandler;
import org.si.diamond.base.xml.element.NodeAttribute;
import org.si.diamond.base.xml.element.XmlNode;
import org.si.diamond.base.xml.exception.XmlException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class SaxGenericXmlWriter {
	public static String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static String LITERAL_END_OF_LINE = "\n";
	public static String LITERAL_SPACE = " ";
	public static String LITERAL_TAB = "\t";
	public static String LITERAL_EQUAL = "=";
	public static String LITERAL_QUOTE = "\"";
	public static String NODE_HEADER_OPENING_BRACKET = "<";
	public static String NODE_HEADER_CLOSING_BRACKET = ">";
	public static String NODE_FOOTER_OPENING_BRACKET = "</";
	public static String NODE_FOOTER_CLOSING_BRACKET = ">";
	public static String NODE_ATTR_TYPE = "type";
	public static String NODE_ATTR_URI = "uri";
	public static String STRING_EMPTY = "";

	private int depth = 0;//internal use no getters and setters
	private static SaxGenericXmlWriter instance;
	private BaseDocumentHandler handler;
	private Writer xmlWriter;

	/**
	 * getter methods
	 */
	public DefaultHandler getHandler() {
		return handler;
	}
	public Writer getXmlWriter() {
		return xmlWriter;
	}
	/**
	 * setter methods
	 */
	public void setHandler(BaseDocumentHandler handler) {
		this.handler = handler;
	}
	public void setXmlWriter(Writer xmlWriter) {
		this.xmlWriter = xmlWriter;
	}

	/**
	 * default constructor
	 */
	private SaxGenericXmlWriter() throws XmlException {
	}

	public static SaxGenericXmlWriter getInstance() throws XmlException {
		if (instance == null) {
			instance = new SaxGenericXmlWriter();
		}
		return instance;
	}

	/**
	 * methd to write the xml document into a file
	 * @param	handler is the xml document to be written into a file
	 * 			inside handler already exists a file object, the moethod will try to open the file first and try to write into it
	 */
	public void write(BaseDocumentHandler handler) throws XmlException {
		try {
			//check file (can write, is exists, etc, throw new XMLException if error
			if (handler.getDocument().canWrite() == false) {
				throw new IOException("Unable to write to file");
			}
			if (handler.getDocument().isFile() == false) {
				throw new IOException("Parameter supplied is not a file");
			}
			//open file writer
			this.xmlWriter = openFileStream(handler.getDocument());
			//write xml declaration
			appendLine(this.xmlWriter, XML_DECLARATION, this.depth);
			//write each node reccursively
			appendNode(this.xmlWriter, handler.getRoot());
		} catch (IOException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	private Writer openFileStream(File file) throws XmlException {
		try {
			OutputStreamWriter outputStreamWriter = new FileWriter(file);
			return outputStreamWriter;
		} catch (FileNotFoundException e) {
			throw new XmlException(e.getMessage(), e);
		} catch (IOException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	private void appendLine(Writer writer, String input, int depth) throws XmlException {
		try {
			for (int i = 1; i <= depth; i++) {
				writer.write(LITERAL_TAB);
			}
			writer.write(input);
			writer.write(LITERAL_END_OF_LINE);
			writer.flush();
		} catch (IOException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unused")
	private void appendString(Writer writer, String input) throws XmlException {
		try {
			writer.write(input);
			writer.flush();
		} catch (IOException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}


	private void appendNode(Writer writer, XmlNode node) throws XmlException {
		String strLineHeader = new String();
		String strLineFooter = new String();
//		String strTemp = new String();
		try {
			if (node.getValue() != null) {
				appendLine(writer, node.getValue(), this.depth);
				return;
			}
			//write node header and iterate the attributes
			strLineHeader += NODE_HEADER_OPENING_BRACKET;
			strLineHeader += node.getQualifiedName();
			strLineHeader += LITERAL_SPACE;
			//iterate attributes
			for (NodeAttribute nodeAttribute : node.getAttributes()) {
				strLineHeader += (STRING_EMPTY.equals(nodeAttribute.getLocalName()) ? nodeAttribute.getQualifiedName() : nodeAttribute.getLocalName()) + LITERAL_EQUAL + LITERAL_QUOTE + nodeAttribute.getValue() + LITERAL_QUOTE + LITERAL_SPACE;
			}
			strLineHeader += NODE_HEADER_CLOSING_BRACKET;
			appendLine(writer, strLineHeader, this.depth);
			this.depth++;
			//iterate each child reccursively
			for (XmlNode childNode : node.getElements()) {
				appendNode(writer, childNode);
			}
			//min depth
			this.depth--;
			//write node footer
			strLineFooter += NODE_FOOTER_OPENING_BRACKET;
			strLineFooter += node.getQualifiedName();
			strLineFooter += NODE_FOOTER_CLOSING_BRACKET;
			appendLine(writer, strLineFooter, depth);
		} catch (Exception e) {
			throw new XmlException(e.getMessage(), e);
		}
	}
}
