package com.jovision.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;





/** 
 *  
 *  
 * <p>Title: Java发送邮件测试类 /p> 
 * 
 * <p>Description: 示例 业务类</p> 
 * 
 * <p>Copyright: Copyright (c) 2012</p> 
 * 
 * 
 * @author dml@2012-12-17 
 * @version 1.0 
 */  
  
/**************************************************************** 
 * 对QQ邮箱使用（对于其他的使用类似的你应该也会更改了） 首先把QQ邮箱的POP3 SMTP打开 首先确定你的网络正常、非代理 
 * 【确定你的QQ邮箱开启了SMTP】！！！！ 如果没有开启，那么这样设置下 设置->账户 --在下面-- 把这个选上 [√]SMTP发信后保存到服务器 
 ****************************************************************/  
public class JavaMail {  
	private static Logger log=Logger.getLogger(JavaMail.class);
    // 设置服务器  
    private static String KEY_SMTP = "mail.smtp.host";  
    private static String VALUE_SMTP = "smtp.jovision.com";  
    // 服务器验证  
    private static String KEY_PROPS = "mail.smtp.auth";  
    private static boolean VALUE_PROPS = true;  
    // 发件人用户名、密码  
    private String SEND_USER = "accountadmin@jovision.com";  
    private String SEND_UNAME = "accountadmin@jovision.com";  
    private String SEND_PWD = "jovision123";  
    // 建立会话  
    private MimeMessage message;  
    private Session s;  
  
    /* 
     * 初始化方法 
     */  
    public JavaMail(String server,String username,String password) { 
    	
    	server="smtp.jovision.com";
    	username="feedback@jovision.com";
    	password="sjwtfk1202";
    	
    	VALUE_SMTP = server;
    	SEND_USER = username;
    	SEND_UNAME = username;
    	SEND_PWD = password;
    	
        Properties props = System.getProperties();  
        props.setProperty(KEY_SMTP, VALUE_SMTP);  
        props.put(KEY_PROPS, VALUE_PROPS);  
        s = Session.getInstance(props);  
        //s=Session.getDefaultInstance(props);  
        /* s.setDebug(true);开启后有调试信息 */  
        message = new MimeMessage(s);  
    }  
  
    /** 
     * 发送邮件 
     *  
     * @param headName 
     *            邮件头文件名 
     * @param sendHtml 
     *            邮件内容 
     * @param receiveUser 
     *            收件人地址 
     * @throws MailException 
     */  
    public void doSendHtmlEmail(String headName, String sendHtml, String receiveUser) throws Exception {  
        try {  
        	
            // 发件人  
            InternetAddress from = new InternetAddress(SEND_USER);  
            message.setFrom(from);  
            // 收件人  
            InternetAddress to = new InternetAddress(receiveUser);  
            message.setRecipient(Message.RecipientType.TO, to);  
            // 邮件标题  
            message.setSubject(headName);  
            String content = sendHtml.toString();  
            // 邮件内容,也可以使纯文本"text/plain"  
            message.setContent(content, "text/html;charset=GBK");  
            //message.setContent(content, "text/plain");  
            message.saveChanges();  
            Transport transport = s.getTransport("smtp");  
            // smtp验证，就是你用来发邮件的邮箱用户名密码  
            transport.connect(VALUE_SMTP, SEND_UNAME, SEND_PWD);  
            // 发送  
            transport.sendMessage(message, message.getAllRecipients());  
            transport.close();  
            System.out.println("send success!");  
        } catch (AddressException e) {  
            log.error("doSendHtmlEmail ||"+e.getMessage()) ;
            throw new Exception(e);
        } 
    }  
  
    public static void main(String[] args) throws Exception {  
    	
    	//JavaMail main=new JavaMail("smtp.jovision.com","feedback@jovision.com","sjwtfk1202");
    	
    	//JavaMail main=new JavaMail("smtp.jovision.com","accountadmin@jovision.com","jovision123");
    	JavaMail main=new JavaMail("smtp.jovision.com","feedback@jovision.com","sjwtfk1202");
    	main.doSendHtmlEmail("fdhgfg", "测试", "iswgg@163.com");
    	
    	
    	
    }  
}  
