package org.si.diamond.base.xml;

import org.si.diamond.base.xml.documentHandler.BaseDocumentHandler;
import org.si.diamond.base.xml.element.XmlNode;
import org.si.diamond.base.xml.exception.XmlException;
import org.si.diamond.base.xml.parser.SaxGenericXmlParser;
import org.xml.sax.SAXException;

import java.io.File;

/**
 * Hello world!
 */
public class App {
	public static void main(String[] args) throws SAXException, XmlException {
		BaseDocumentHandler docHandlerV4 = new BaseDocumentHandler(new File("C:\\Temp\\4.xml"));
		docHandlerV4.startDocument();
		SaxGenericXmlParser.getInstance().parse(docHandlerV4);
		XmlNode rootNodeV4 = docHandlerV4.getRoot();

		BaseDocumentHandler docHandlerV5 = new BaseDocumentHandler(new File("C:\\Temp\\6.xml"));
		docHandlerV5.startDocument();
		SaxGenericXmlParser.getInstance().parse(docHandlerV5);
		XmlNode rootNodeV5 = docHandlerV5.getRoot();

		System.out.println("Hello World!");
		System.out.println(rootNodeV4.equals(rootNodeV5));
	}
}
