/**
 * 
 */
package com.jovision.common;

import org.zeromq.ZMQ;

/**
 * @Title: ZeroClient.java 
 * @Package com.jovision.common
 * @author Hellon(刘海龙)
 * @Description: TODO() 
 * @date 2015-9-16 上午10:03:21
 */
public class ZeroClient {
	/**
	 * 使用zeroMq向服务端发送消息
	 * @author Hellon(刘海龙) 
	 * @param message
	 * @param connectInfo
	 * @return
	 * @throws Exception 
	 */
	public static String sendMess(String message,String connectInfo) throws Exception{
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REQ);
		//"tcp://localhost:5555"
		socket.connect(connectInfo);

		byte[] request = message.getBytes("utf-8");
		// 发送
		socket.send(request, ZMQ.NOBLOCK);
		// 获得返回值
		byte[] reply = socket.recv(0);
		
		return new String(reply,"utf-8");
	}
}
