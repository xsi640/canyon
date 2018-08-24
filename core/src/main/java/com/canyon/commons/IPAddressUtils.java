package com.canyon.commons;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPAddressUtils {
	public static boolean validIP(String strIPAddress) {
		try {
			if (strIPAddress == null || strIPAddress.isEmpty()) {
				return false;
			}

			String[] parts = strIPAddress.split("\\.");
			if (parts.length != 4) {
				return false;
			}

			for (String s : parts) {
				int i = Integer.parseInt(s);
				if ((i < 0) || (i > 255)) {
					return false;
				}
			}
			if (strIPAddress.endsWith(".")) {
				return false;
			}

			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	public static InetAddress toIPAddress(String strIPAddress){
		InetAddress result = null;
		try {
			result = InetAddress.getByName(strIPAddress);
		} catch (UnknownHostException e) {
		}
		return result;
	}

	public static long toLong(String strIPAddress){
		long result = 0L;
		InetAddress ip = toIPAddress(strIPAddress);
		if(ip != null){
			result = toLong(ip);
		}
		return result;
	}
	
	public static long toLong(InetAddress ip){
		long result = 0L;
		byte[] octets = ip.getAddress();
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
	}

	public static boolean contains(String startIP, String endIP, String ip){
        long ipStart  = toLong(startIP);
        long ipEnd  = toLong(endIP);
        long ipTest  = toLong(ip);
        
        return ipTest >= ipStart && ipTest <= ipEnd;
	}
}
