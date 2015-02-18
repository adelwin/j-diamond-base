/*
 * File Name       : HTMLDataSource.java
 * Class Name      : HTMLDataSource
 * Module Name     : pacs-mail
 * Project Name    : pacs-base
 * Author          : adelwin.handoyo
 * Created Date    : 2014-10-14 09:57:05
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

package org.si.diamond.base.mail.datasource;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTMLDataSource implements DataSource {

	private StringBuffer	htmlString;

	public HTMLDataSource(String _htmlString) {
		this.htmlString = new StringBuffer(_htmlString);
	}

	public String getContentType() {
		return "text/html";
	}

	public InputStream getInputStream() throws IOException {
		if (htmlString == null || htmlString.toString().trim().length() == 0) {
			throw new IOException("Empty buffer");
		}
		return new ByteArrayInputStream(htmlString.toString().getBytes());
	}

	public String getName() {
		return "JAF text/html DataSource to send eMail only";
	}

	public OutputStream getOutputStream() throws IOException {
		throw new IOException("This DataHandler cannot write HTML");
	}

}
