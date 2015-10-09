package com.jovision.http.utils;


	public class HttpUriDef {

	/**
	 * 服务器基地址
	 */
	static String BaseUrl="210.14.156.66:80";//测试环境：9999，正式版：80
	static String bbsUrl = "http://bbs.cloudsee.net";//bbs论坛地址
	static int streamMediaPort = 53230;//流媒体服务器端口
	static int mediaPicPort = 54161;//流媒体服务器端口
	static int vodVideoPort = 54161;//点播视频端口
	static String checkcodeUrl = "123.57.150.154/MSGSend";//短信，邮箱验证码获取ip
	

	public static String getCheckcodeUrl() {
		return checkcodeUrl;
	}

	public static void setCheckcodeUrl(String checkcodeUrl) {
		HttpUriDef.checkcodeUrl = checkcodeUrl;
	}

	public static int getVodVideoPort() {
		return vodVideoPort;
	}

	public static void setVodVideoPort(int vodVideoPort) {
		HttpUriDef.vodVideoPort = vodVideoPort;
	}

	public static int getMediaPicPort() {
		return mediaPicPort;
	}

	public static void setMediaPicPort(int mediaPicPort) {
		HttpUriDef.mediaPicPort = mediaPicPort;
	}


	public static int getStreamMediaPort() {
		return streamMediaPort;
	}

	public static void setStreamMediaPort(int streamMediaPort) {
		HttpUriDef.streamMediaPort = streamMediaPort;
	}

	
	
	public static String getBbsUrl() {
		return bbsUrl;
	}

	public static void setBbsUrl(String bbsUrl) {
		HttpUriDef.bbsUrl = bbsUrl;
	}

	/**
	 * 设置服务器基地址
	 * @param url 地址：如"210.14.156.66:9999"
	 */
	public static void SetBaseUrl(String url)
	{
		BaseUrl=url;
	}
	
	public static String GetBaseUrl()
	{
		return BaseUrl;
	}
	
	/**
	 * 设备类操作
	 * @author zerostyle
	 *
	 */
	public static class Devices
	{
	
	/**
	 * 设备列表 get 请求， 支持jsonp，支持异步
	 * /userdevice?sid=<sidtest>
	 * sid 的值从登录后的页面cookie获取jv_session的值
	 */
		public static final String getDeviceBySid="http://"+HttpUriDef.BaseUrl+"/userdevice?pv=1.0&lpt=1";
		
		/**
		 * 添加设备 支持get ，post 请求，支持jsonp，支持异步
		 * 此接口不验证设备是否存在只验证设备是否被重复添加
		 * /adddevice?dvusername=<设备名>&dvpassword=<设备密码>&dguid=<云视通号>&sid=<sidtest>
		 */
		public static final String addDevice="http://"+HttpUriDef.BaseUrl+"/adddevice?pv=2.0&lpt=1";
		
		/**
		 * 删除设备 支持get ，post 请求，支持jsonp，支持异步
		 * 此接口不验证设备是否存，只要后台不崩溃都会rt 为0
		 * /removedevice?sid=<sidtest>&dguid=<云视通号>
		 */
		public static String removeDevice="http://"+HttpUriDef.BaseUrl+"/removedevice?pv=1.0&lpt=1";
		
		
		/**
		 * 获取设备在线状态 支持get 支持jsonp 支持异步
		 */
		public static String onlinestatus="http://"+HttpUriDef.BaseUrl+"/onlinestatus??pv=1.0&lpt=1";
		
		/**
		 * 流媒体服务开通
		 */
		public static String streamservice="http://"+HttpUriDef.BaseUrl+"/streamservice?pv=2.0&lpt=0";
		
		/**
		 * 视频分享
		 */
		public static String sharevideo="http://"+HttpUriDef.BaseUrl+"/sharevideo?pv=2.0&lpt=0";
		
		
		
		public static String sharevideoadmin="http://"+HttpUriDef.BaseUrl+"/sharevideoadmin?pv=2.0&lpt=0";
	}
	
	/**
	 * 用户类操作
	 * @author zerostyle
	 *
	 */
	public static class Users
	{
		/**
		 * 用户登录接口 支持get ，post 请求，支持jsonp，支持异步
		 */
		public static final String userlogin="http://"+HttpUriDef.BaseUrl+"/userlogin?pv=2.0&lpt=0";
		
		/**
		 * 查询用户是否存在 支持get请求，支持jsonp， 支持异步
		 */
		public static final String userexist="http://"+HttpUriDef.BaseUrl+"/userexist?pv=2.0&lpt=0";
		
		/**
		 * 用户注册 支持get post请求，支持jsonp，支持异步
		 */
		public static final String userreg="http://"+HttpUriDef.BaseUrl+"/userreg?pv=2.0&lpt=0";
		
		/**
		 * 用户退出
		 */
		public static final String userlogout="http://"+HttpUriDef.BaseUrl+"/userlogout?pv=2.0&lpt=0";
		
		/**
		 * 修改密码
		 */
		public static final String modifypwd="http://"+HttpUriDef.BaseUrl+"/modifypwd?pv=2.0&lpt=0";
		
		/**
		 * 重置密码
		 */
		public static final String resetpwd="http://"+HttpUriDef.BaseUrl+"/resetpwd?pv=2.0&lpt=0";
		
		/**
		 * 找到邮箱
		 */
		public static final String findemail="http://"+HttpUriDef.BaseUrl+"/findemail?pv=2.0&lpt=0";
		
		/**
		 * 根据sid获取用户信息
		 */
		public static final String userinfobysid="http://"+HttpUriDef.BaseUrl+"/userinfo?pv=2.0&lpt=0";
		/**
		 * 根据用户的username获取用户在bbs上的信息
		 */
		public static final String bbsReturn = HttpUriDef.bbsUrl + "/user/infojson?pv=2.0&lpt=0";
		
	}
	
	/**
	 * 公共API
	 * @author zerostyle
	 *
	 */
	public static class Common
	{
		public static final String genstreamurl="http://"+HttpUriDef.BaseUrl+"/api/genstreamurl?pv=2.0&lpt=0";
	}	
	/**
	 * 获取验证码
	 * @author liuhailong
	 *
	 */
	public static class Code{
		public static final String smsCodeUrl = "http://"+HttpUriDef.checkcodeUrl+"/api/smsService.action?pv=2.0&lpt=0";
		public static final String emailCodeUrl = "http://"+HttpUriDef.checkcodeUrl+"/api/EmailSendServlet.action?pv=2.0&lpt=0";
	}
	/**
	 * 分享到我的网站
	 * @author liuhailong
	 *
	 */
	public static class WebSite{
		//获取视频地址
		public static final String getWebsiteStreamurl="http://"+HttpUriDef.BaseUrl+"/api/getstreamurl?pv=2.0&lpt=0";
		//视频分享地址
		public static String websiteSharevideo="http://"+HttpUriDef.BaseUrl+"/sharevideowebsite?pv=2.0&lpt=0";
	}
}
