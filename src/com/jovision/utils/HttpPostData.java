package com.jovision.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpPostData {

	String _url="";
	
	Map<String,String> _params;
	
	public HttpPostData(String url,Map<String,String> keyValueParams)
	{
		this._url=url;
		this._params=keyValueParams;
	}
	
	public String Do() throws Exception
	{
		String result="";
		PrintWriter  out = null;
		BufferedReader in = null;
		try
		{
			System.out.println("POST请求的URL为："+this._url);
			URL realUrl = new URL(this._url);
			String paramStr="";
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
         // 设置doOutput属性为true表示将使用此urlConnection写入数据  
            connection.setDoOutput(true); 
            connection.setDoInput(true);
            
            
            paramStr=getParamStr();

            // 得到请求的输出流对象  
            out = new PrintWriter(connection.getOutputStream());
            System.out.println("请求参数为："+paramStr);
            // 发送请求参数
            out.print(paramStr);
            // flush输出流的缓冲
            out.flush();
            
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("获取的结果为："+result);  
		}
		catch(Exception ex)
		{
			System.out.println("发送 POST 请求出现异常！"+ex);
            ex.printStackTrace();
			throw ex;
		}
		finally
		{
			try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
		}
		return result;
	}
	
	private String getParamStr()
	{
		String paramStr="";
		// 获取所有响应头字段
        Map<String, String> params = this._params;
        // 获取参数列表组成参数字符串
        for (String key : params.keySet()) {
        	paramStr+=key+"="+params.get(key)+"&";
        }
        //去除最后一个"&"
        paramStr=paramStr.substring(0, paramStr.length()-1);
        
        return paramStr;
	}
}
