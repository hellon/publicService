/**
 * 
 */
package com.jovision.thread;

import java.io.UnsupportedEncodingException;

import org.zeromq.ZMQ;

/**
 * @Title: StartThread.java 
 * @Package com.jovision.thread
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-16 上午09:58:09
 */

public class ZMQServerThread extends Thread
{
	private String port;
	
	public ZMQServerThread(){}
	
	public ZMQServerThread(String port){
		this.port = port;
	}
	
    @Override
    public void run()
    {
    	// 绑定到端口,并且在io_thread中accept连接
    	System.out.println("我启动啦！！！");
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:"+port);
		while (true) {
			byte[] request;
			// 等待下一个client端的请求
			// 等待一个以0结尾的字符串
			// 忽略最后一位的0打印
			request = socket.recv(0);
			try {
				System.out.println("zhangkai：Received request: [" + new String(request, "utf-8") + "]");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//在这里做业务处理，然后返回给请求端
			String replyString = "World" + " ";
			byte[] reply = null;
			try {
				reply = replyString.getBytes("utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			socket.send(reply, ZMQ.NOBLOCK);
		}
	}
       // String name = Thread.currentThread().getName();
    
}