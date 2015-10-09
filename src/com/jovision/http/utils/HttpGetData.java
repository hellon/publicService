package com.jovision.http.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import org.apache.log4j.Logger;
import net.sf.json.JSONObject;

public  class  HttpGetData {
	protected static Logger infoLogger = Logger.getLogger("forInfo");
	
	String _url="";
	
	Map<String,String> _params;
	
	public HttpGetData(String url,Map<String,String> keyValueParams)
	{
		this._url=url;
		this._params=keyValueParams;
	}
	@SuppressWarnings("unchecked")
	public <T> T Do(Class<T> clazz) throws Exception
	{
		 String result = "";
	     BufferedReader in = null;
	        try {
	        	
	        	String urlStr = this._url + "&" + getParamStr();
	        	//System.out.println("GET请求的URL为："+urlStr);
	        	infoLogger.info("GET请求的URL为："+urlStr);
	            URL realUrl = new URL(urlStr);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            connection.setReadTimeout(30000);
	            connection.setConnectTimeout(30000);
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 建立实际的连接
	            connection.connect();
	            
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	            //System.out.println("获取的结果为："+result);
	            infoLogger.info("获取的结果为："+result);
	        } catch (Exception e) {
	            //System.out.println("发送GET请求出现异常！" + e);
	            infoLogger.info("发送GET请求出现异常！" + e);
	            //e.printStackTrace();
	            throw e;
	        }
	        // 使用finally块来关闭输入流
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	            	throw e2;
	            }
	        }
	        T t = (T) JSONObject.toBean(JSONObject.fromObject(result), clazz);
	        return t;
	
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
