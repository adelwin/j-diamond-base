/*
 * File Name       : GUIDUtil.java
 * Class Name      : GUIDUtil
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

package org.si.diamond.base.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

public class GUIDUtil {
    private static SecureRandom seeder;
    private static String sInetAddress;

    static {
        InetAddress inetaddress;


        //initialize the seeder
        seeder = new SecureRandom();

        int i = seeder.nextInt();

        try {
            inetaddress = InetAddress.getLocalHost();

            byte[] addrbyte = inetaddress.getAddress();
            sInetAddress = hexFormat(getInt(addrbyte), 8);
        } catch (UnknownHostException ex) {
            sInetAddress = hexFormat(i, 8);
        }
    }

    /**
     * Generate a global unique identifier
     * @return String
     */
    public static final String getGUID() {
        long l = System.currentTimeMillis();
        int i = (int) l & -1;
        int j = seeder.nextInt();

        String sThreadHash = hexFormat(Thread.currentThread().hashCode(), 8);

        StringBuffer sb = new StringBuffer();
        sb.append(hexFormat(i, 8));
        sb.append(sInetAddress);
        sb.append(sThreadHash);
        sb.append(hexFormat(j, 8));

        return sb.toString();
    }

    private static final int getInt(byte[] bytearr) {
        int i = 0;
        int j = 24;

        for (int k = 0; j >= 0; k++) {
            int l = bytearr[k] & 0xff;
            i += (l << j);
            j -= 8;
        }

        return i;
    }

    private static final String hexFormat(int i, int j) {
        String s = Integer.toHexString(i);

        return padHex(s, j);
    }

    private static final String padHex(String s, int i) {
        StringBuffer stringbuffer = new StringBuffer();

        if (s.length() < i) {
            for (int j = 0; j < (i - s.length()); j++)
                stringbuffer.append("0");
        }

        stringbuffer.append(s);

        return stringbuffer.toString();
    }
}
