package com.jovision.system;

import java.io.File;
import java.lang.reflect.Method;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.VisitorSupport;
import org.dom4j.io.SAXReader;

/**
 * 系统配置文件对象
 * 
 * @author Hellon(刘海龙)
 * @date 2015-09-09
 */
public class ConfigBean {
	private static ConfigBean bean = null;
	
	//redis数据库ip地址
	private String redisIP;
	//redis数据库端口号
	private String redisPort;
	//zeroMQ服务端口
	private String zeroServicePort;
	//设备模块连接信息
	private String deviceConnInfo;
	//报警模块连接信息
	private String alarmConnInfo;
	//云存储模块连接信息
	private String cloudStorageConnInfo;
	//解密密码
	private String aesPasseord;
	//访问账号服务器地址
	private String accountService;
	public static synchronized ConfigBean getInstace() {
		if (bean == null) {
			try {
				bean = load("/self_config.xml");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return bean;
	}

	public static void main(String[] args) {
		String str = ConfigBean.getInstace().getRedisIP();
		System.out.println(str);
	}

	public static ConfigBean load(String xmlfile) throws Exception {
		
		
		final ConfigBean be = new ConfigBean();
		SAXReader reader = new SAXReader();
		String path = ConfigBean.class.getResource(xmlfile).toURI().getPath();
		try {
			Document doc = reader.read(new File(path));
			doc.accept(new VisitorSupport() {
				@Override
				public void visit(Attribute node) {
					try {
						String fieldName = ConfigBean.class.getDeclaredField(
								node.getName()).getName();
						String methodName = "set"
								+ fieldName.replaceFirst(fieldName.substring(0,
										1), fieldName.substring(0, 1)
										.toUpperCase());
						Method method = ConfigBean.class.getMethod(methodName,
								String.class);
						method.invoke(be, node.getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void visit(Element node) {
					// System.out.println("元素名称："+node.getName()+"\t\t"+"元素内容："+node.getText());
				}
			});
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return be;
	}
	private ConfigBean() {
	}

	public String getRedisIP() {
		return redisIP;
	}

	public void setRedisIP(String redisIP) {
		this.redisIP = redisIP;
	}

	public String getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(String redisPort) {
		this.redisPort = redisPort;
	}

	public String getZeroServicePort() {
		return zeroServicePort;
	}

	public void setZeroServicePort(String zeroServicePort) {
		this.zeroServicePort = zeroServicePort;
	}

	public String getDeviceConnInfo() {
		return deviceConnInfo;
	}

	public void setDeviceConnInfo(String deviceConnInfo) {
		this.deviceConnInfo = deviceConnInfo;
	}

	public String getAlarmConnInfo() {
		return alarmConnInfo;
	}

	public void setAlarmConnInfo(String alarmConnInfo) {
		this.alarmConnInfo = alarmConnInfo;
	}

	public String getCloudStorageConnInfo() {
		return cloudStorageConnInfo;
	}

	public void setCloudStorageConnInfo(String cloudStorageConnInfo) {
		this.cloudStorageConnInfo = cloudStorageConnInfo;
	}

	public String getAesPasseord() {
		return aesPasseord;
	}

	public void setAesPasseord(String aesPasseord) {
		this.aesPasseord = aesPasseord;
	}

	public String getAccountService() {
		return accountService;
	}

	public void setAccountService(String accountService) {
		this.accountService = accountService;
	}
	
	
}
