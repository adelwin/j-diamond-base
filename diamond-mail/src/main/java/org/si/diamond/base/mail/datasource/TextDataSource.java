/*
 * File Name       : TextDataSource.java
 * Class Name      : TextDataSource
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

public class TextDataSource implements DataSource {

	private StringBuffer	textString;

	public TextDataSource(String _textString) {
		this.textString = new StringBuffer(_textString);
	}

	public String getContentType() {
		return "text/plain";
	}

	public InputStream getInputStream() throws IOException {
		if (textString == null || textString.toString().trim().length() == 0) {
			throw new IOException("Empty buffer");
		}
		return new ByteArrayInputStream(textString.toString().getBytes());
	}

	public String getName() {
		return "JAF text/html DataSource to send eMail only";
	}

	public OutputStream getOutputStream() throws IOException {
		throw new IOException("This DataHandler cannot write text");
	}

}
