package com.moomanow.utils;

import java.util.Arrays;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;

public class ByteUtils {

	public static boolean isMatch(byte[] pattern, byte[] input, int pos) {
	    for(int i=0; i< pattern.length; i++) {
	        if(pattern[i] != input[pos+i]) {
	            return false;
	        }
	    }
	    return true;
	}

	public static List<byte[]> split(byte[] pattern, byte[] input) {
	    List<byte[]> l = new LinkedList<byte[]>();
	    int blockStart = 0;
	    for(int i=0; i<input.length; i++) {
	       if(isMatch(pattern,input,i)) {
	          l.add(Arrays.copyOfRange(input, blockStart, i));
	          blockStart = i+pattern.length;
	          i = blockStart;
	       }
	    }
	    l.add(Arrays.copyOfRange(input, blockStart, input.length ));
	    return l;
	}
	
	public static long getUnsignedInt(byte[] data) {
	    long result = 0;

	    for (int i = 0; i < data.length; i++) {
	        result += data[i] << 8 * (data.length - 1 - i);
	    }
	    return result;
	}
	
	public static String byteToHexString(byte[] data) {
		StringBuilder sbuf = new StringBuilder();
		Formatter fmt = new Formatter(sbuf);
		for (byte b : data) {
			fmt.format("0x%x ", b);
		}
		return sbuf.toString();
	}
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
