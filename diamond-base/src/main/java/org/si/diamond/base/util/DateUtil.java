/*
 * File Name       : DateUtil.java
 * Class Name      : DateUtil
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
 * | Adelwin Handoyo | 2014-11-29 14.52 | 2.5.0   | add convenience method
 * |                 |                  |         |
 * |=================|==================|=========|======================================
 */

package org.si.diamond.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final Date getTodayDate() {
		// Calendar calendar = Calendar.getInstance();
		Date retVal = new Date();
		return retVal;
	}

	public static final String getDateAsString(Date inputDate, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(inputDate);
	}

	public static final Date formatDate(String inputDate, String pattern) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(inputDate);
	}

	public static final boolean isToday(Date inputDate) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		Calendar input = Calendar.getInstance();
		input.setTime(inputDate);
		input.set(Calendar.HOUR_OF_DAY, 0);
		input.set(Calendar.MINUTE, 0);
		input.set(Calendar.SECOND, 0);
		input.set(Calendar.MILLISECOND, 0);

		if ((today.get(Calendar.YEAR) == input.get(Calendar.YEAR)) &&
			(today.get(Calendar.MONTH) == input.get(Calendar.MONTH)) &&
			(today.get(Calendar.DAY_OF_MONTH) == input.get(Calendar.DAY_OF_MONTH))) {
			return true;
		} else {
			return false;
		}
	}

	public static final boolean isYesterday(Date inputDate) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		today.add(Calendar.DAY_OF_MONTH, -1);

		Calendar input = Calendar.getInstance();
		input.setTime(inputDate);
		input.set(Calendar.HOUR_OF_DAY, 0);
		input.set(Calendar.MINUTE, 0);
		input.set(Calendar.SECOND, 0);
		input.set(Calendar.MILLISECOND, 0);

		if ((today.get(Calendar.YEAR) == input.get(Calendar.YEAR)) &&
				(today.get(Calendar.MONTH) == input.get(Calendar.MONTH)) &&
				(today.get(Calendar.DAY_OF_MONTH) == input.get(Calendar.DAY_OF_MONTH))) {
			return true;
		} else {
			return false;
		}
	}

	public static final boolean isTomorrow(Date inputDate) {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);

		today.add(Calendar.DAY_OF_MONTH, 1);

		Calendar input = Calendar.getInstance();
		input.setTime(inputDate);
		input.set(Calendar.HOUR_OF_DAY, 0);
		input.set(Calendar.MINUTE, 0);
		input.set(Calendar.SECOND, 0);
		input.set(Calendar.MILLISECOND, 0);

		if ((today.get(Calendar.YEAR) == input.get(Calendar.YEAR)) &&
				(today.get(Calendar.MONTH) == input.get(Calendar.MONTH)) &&
				(today.get(Calendar.DAY_OF_MONTH) == input.get(Calendar.DAY_OF_MONTH))) {
			return true;
		} else {
			return false;
		}
	}

	public static final boolean isBetween(Date base, Date rangeStart, Date rangeEnd) {
		if (base.before(rangeStart)) return false;
		if (base.after(rangeEnd)) return false;
		return true;
	}
}
