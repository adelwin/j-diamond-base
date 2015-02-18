/*
 * File Name       : SaxGenericXmlParser.java
 * Class Name      : SaxGenericXmlParser
 * Module Name     : pacs-xml
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2015-01-09 16:20:16
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

package org.si.diamond.base.xml.parser;

import org.si.diamond.base.xml.documentHandler.BaseDocumentHandler;
import org.si.diamond.base.xml.exception.XmlException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

/**
 * Created by adelwin.handoyo on 2015-01-09.
 */
public class SaxGenericXmlParser {
	private static SaxGenericXmlParser	instance;
	private SAXParserFactory factory;
	private BaseDocumentHandler handler;
	private SAXParser parser;

	/**
	 * getter methods
	 */
	public SAXParserFactory getFactory() {
		return factory;
	}

	public DefaultHandler getHandler() {
		return handler;
	}

	public SAXParser getParser() {
		return parser;
	}

	/**
	 * setter methods
	 */
	public void setFactory(SAXParserFactory factory) {
		this.factory = factory;
	}

	public void setHandler(BaseDocumentHandler handler) {
		this.handler = handler;
	}

	public void setParser(SAXParser parser) {
		this.parser = parser;
	}

	public void parse(BaseDocumentHandler handler) throws XmlException {
		try {
			factory = SAXParserFactory.newInstance();
			parser = factory.newSAXParser();
			if (handler.getDocument() != null) {
				InputStream inputStream = new FileInputStream(handler.getDocument());
				Reader reader = new InputStreamReader(inputStream, "UTF-8");
				InputSource is = new InputSource(reader);
				is.setEncoding("UTF-8");
				parser.parse(is, handler);
			} else if (handler.getDocumentStream() != null) {
				Reader reader = new InputStreamReader(handler.getDocumentStream(), "UTF-8");
				InputSource is = new InputSource(reader);
				is.setEncoding("UTF-8");
				parser.parse(is, handler);
			}
		} catch (ParserConfigurationException e) {
			throw new XmlException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new XmlException(e.getMessage(), e);
		} catch (IOException e) {
			throw new XmlException(e.getMessage(), e);
		}
	}

	private SaxGenericXmlParser() throws XmlException {
	}

	public static SaxGenericXmlParser getInstance() throws XmlException {
		if (instance == null) {
			instance = new SaxGenericXmlParser();
		}
		return instance;
	}
}
