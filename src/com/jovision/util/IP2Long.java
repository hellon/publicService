package com.jovision.util;

/**
 * 
 * @Title: IP2Long.java 
 * @Package com.jovision.util
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-28 下午05:02:37
 */
public class IP2Long {
	public static long ipToLong(String strIP) {
		long[] ip = new long[4];
		int position1 = strIP.indexOf(".");
		int position2 = strIP.indexOf(".", position1 + 1);
		int position3 = strIP.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strIP.substring(0, position1));
		ip[1] = Long.parseLong(strIP.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIP.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIP.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}

	public static String longToIP(long longIP) {
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longIP >>> 24));
		sb.append(".");
		// ����8λ��0��Ȼ������16λ
		sb.append(String.valueOf((longIP & 0x00FFFFFF) >>> 16));
		sb.append(".");
		sb.append(String.valueOf((longIP & 0x0000FFFF) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longIP & 0x000000FF));
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String ipStr = "192.168.0.1";
		long ipLong = IP2Long.ipToLong(ipStr);
		System.out.println(ipLong);
		System.out.println("192.168.0.1 �Ķ�������ʽΪ: "
				+ Long.toBinaryString(ipLong));*/
		
		//System.out.println(1&0);
	//	System.out.println(longToIP(getMask(24)));
		int mask = getMask(16);
		int ip = IPv4Util.ipToInt("139.170.0.1");
		//int result = ip & mask;
		System.out.println(ip);
		//System.out.println(IPv4Util.intToIp(result));
	}
	
	public static int getMask(int x)
	{
		return 0xFFFFFFFF<<(32-x);
	}
}