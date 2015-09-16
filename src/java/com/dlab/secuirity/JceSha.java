/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dlab.secuirity;

/**
 *
 * @author user
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.*;

public class JceSha {
    final static Logger logger = LoggerFactory.getLogger(JceSha.class);

    public static String getHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] output = md.digest();
            md.update(input.getBytes());
            output = md.digest();
            return bytesToHex(output);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return null;
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }
}
