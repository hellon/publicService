package com.jovision.deviceManage.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jovision.Exception.DeviceBindByOthersException;
import com.jovision.Exception.DeviceBindNoExistException;
import com.jovision.Exception.DeviceBindSelfExistException;
import com.jovision.Exception.DeviceExistException;
import com.jovision.Exception.RedisException;
import com.jovision.Exception.RemotErrorCodeException;
import com.jovision.Exception.RemoteConnectException;
import com.jovision.aes.BizAccSession;
import com.jovision.common.ErrorCodeDef;
import com.jovision.common.constProperty;
import com.jovision.controller.BaseController;
import com.jovision.deviceManage.service.DeviceService;
import com.jovision.domain.DeviceWhole;
import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.domain.TbDeviceUser;
import com.jovision.domain.TbUser;
import com.jovision.domain.TbUserDevice;
import com.jovision.http.modelBean.AccountData;
import com.jovision.http.modelBean.RootData;
import com.jovision.redisDao.redisFactory;
import com.jovision.util.SerializeUtil;
import com.jovision.utils.HttpGetData;
import com.jovision.utils.Tools;

/**
 * 
 * @Title: DeviceController.java 
 * @Package com.jovision.controller
 * @author Hellon(刘海龙) Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午07:48:40
 */
public class DeviceController extends BaseController{

	
	private static final long serialVersionUID = -6664493494484187082L;
	private RootData root = new RootData();
	private DeviceService service = new DeviceService();

	
	public String ListData(){
		
		/*BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String userId = bas.getUsername();*/
			
		String userId = "65d0b5a7eca949c69746417272157fba";
		
		try {
			/*List list_self = service.deviceList(userId,"0", 200, 0);
			List list_share = service.deviceList(userId,"1", 200, 0);
			
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			
			for(int i = 0;i<list_self.size();i++){//自己拥有的设备
				Map<String,String> map = (Map<String, String>) list_self.get(i);
				map.put("permission", "0");
				removeAttr(map);
				list.add(map);
			}
			for(int i = 0;i<list_share.size();i++){//被分享的设备
				Map<String,String> map = (Map<String, String>) list_share.get(i);
				map.put("permission", "1");
				removeAttr(map);
				list.add(map);
			}*/
			List list = service.deviceList(userId,"0", 200, 0);
			root = new RootData("true","用户设备列表获取成功",list,ErrorCodeDef.DO_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			root = new RootData("false","用户设备列表获取失败","",ErrorCodeDef.MYSQL_ERROR);
		}
		
		return SUCCESS;
	}	
	

	public String userShare()
	{
		
		/*BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		String userId = bas.getUsername();*/
		
		HttpServletRequest  request = getRequest();
		String userId = "3c4c3a3aab714af9852c1e254c554178";
		String userIdArr = request.getParameter("userIdArr");
		String deviceGuid = request.getParameter("deviceGuid");
		
		if(Tools.strArrIsNull(userId,userIdArr,deviceGuid))
		{
			root = new RootData("false","传入参数不能为空！","",ErrorCodeDef.PARAMETER_IS_NULL);
			return SUCCESS;
		}
		
		List<TbUserDevice> tud = new ArrayList<TbUserDevice>();
		List<TbDeviceUser> tdu = new ArrayList<TbDeviceUser>();

		//分享
		boolean isMulti = userIdArr.contains(",");
		if(isMulti)
		{   //多个被分享者
			String[] userIdArray = null;

			try 
			{
				//远程account转为userId
				userIdArray = account2userIds(userIdArr);
			} 
			catch (RemotErrorCodeException rece)
			{
				logger.error("userShare接口异常！",rece);
				root = new RootData("false",rece.getMessage().split("_")[0],"",rece.getMessage().split("_")[1]);
				return SUCCESS;
			}
			catch (RemoteConnectException rce)
			{
				//rce.printStackTrace();
				logger.error("userShare接口异常！",rce);
				root = new RootData("false","远程连接账号库失败！","",ErrorCodeDef.REMOTE_EXCEPTION);
				return SUCCESS;
			}
			if(userIdArray==null)
			{
				root = new RootData("false","账号都不存在！","",ErrorCodeDef.ACCOUNT_NO_EXIT);
				return SUCCESS;
			}
			
			for(String userId_sharee:userIdArray)
			{
				/*TbUserDevice*/
				TbUserDevice tbud_sharee = new TbUserDevice();
				tbud_sharee.setAddtime(new Timestamp((new Date()).getTime()));
				tbud_sharee.setDeviceGuid(deviceGuid);
				tbud_sharee.setPermission(Integer.parseInt(constProperty.SHARE));
				tbud_sharee.setUserid(userId_sharee);
				tud.add(tbud_sharee);
				
				String dev_nickname =null;
				try {
					dev_nickname = redisFactory.hget(8, deviceGuid, "dev_nickname");
				} catch (RedisException e) {
					logger.error(e,e);
				}
				
				if(!Tools.strIsNull(dev_nickname))
				{
					tbud_sharee.setNickname(dev_nickname);
				}
				
				/*TbDeviceUser*/
				TbDeviceUser tbdu_sharee = new TbDeviceUser();
				tbdu_sharee.setAddtime(new Timestamp((new Date()).getTime()));
				tbdu_sharee.setDeviceGuid(deviceGuid);
				tbdu_sharee.setPermission(Integer.parseInt(constProperty.SHARE));
				tbdu_sharee.setUserid(userId_sharee);
				tdu.add(tbdu_sharee);
			}
		}
		else
		{
			//单个被分享者
			try 
			{
				//远程account转为userId
				userIdArr = account2userId(userIdArr);
			} 
			catch (RemotErrorCodeException rece)
			{
				logger.error("userShare接口异常！",rece);
				root = new RootData("false",rece.getMessage().split("_")[0],"",rece.getMessage().split("_")[1]);
				return SUCCESS;
			}
			catch (RemoteConnectException rce)
			{
				//rce.printStackTrace();
				logger.error("userShare接口异常！",rce);
				root = new RootData("false","远程连接账号库失败！","",ErrorCodeDef.REMOTE_EXCEPTION);
				return SUCCESS;
			}
			
			/*TbUserDevice*/
			TbUserDevice tbud_sharee = new TbUserDevice();
			tbud_sharee.setAddtime(new Timestamp((new Date()).getTime()));
			tbud_sharee.setDeviceGuid(deviceGuid);
			tbud_sharee.setPermission(Integer.parseInt(constProperty.SHARE));
			tbud_sharee.setUserid(userIdArr);
			String dev_nickname =null;
			try {
				dev_nickname = redisFactory.hget(8, deviceGuid, "dev_nickname");
			} catch (RedisException e) {
				logger.error(e,e);
			}
			
			if(!Tools.strIsNull(dev_nickname))
			{
				tbud_sharee.setNickname(dev_nickname);
			}
			
			tud.add(tbud_sharee);
			
			/*TbDeviceUser*/
			TbDeviceUser tbdu_sharee = new TbDeviceUser();
			tbdu_sharee.setAddtime(new Timestamp((new Date()).getTime()));
			tbdu_sharee.setDeviceGuid(deviceGuid);
			tbdu_sharee.setPermission(Integer.parseInt(constProperty.SHARE));
			tbdu_sharee.setUserid(userIdArr);
			tdu.add(tbdu_sharee);
		}
		
		
		try 
		{
			service.userShareDevice(userId,tud,tdu,deviceGuid,constProperty.SHARE);
		}
		catch (DeviceExistException e) 
		{
			root = new RootData("false",e.getMessage(),"",ErrorCodeDef.DEVICE_NOT_EXIST);
			return SUCCESS;
		}
		catch (DeviceBindNoExistException e) 
		{   //无权分享
			root = new RootData("false",e.getMessage(),"",ErrorCodeDef.DEVICE_USER_NOTHAVE);
			return SUCCESS;
		}
		catch (Exception e) 
		{
			logger.error("userShare接口异常", e);
			root = new RootData("false","出现异常，分享失败！","","");
			return SUCCESS;
		}
		
		Map<String, String> data = new HashMap<String, String>();
		String message = "恭喜您，设备【"+deviceGuid+"】分享给用户：【"+userIdArr+"】成功！";
		root = new RootData("true",message,data,ErrorCodeDef.DO_SUCCESS);
		return SUCCESS;
	}

	/**
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-21 下午04:58:11
	 * @return
	 * @throws DeviceExistException
	 */
	public String userBind() throws DeviceExistException
	{
		
		/*BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("true","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String userId = bas.getUsername();*/
		//18888315602
		String userId = "3c4c3a3aab714af9852c1e254c554178";
		HttpServletRequest  request = getRequest();
		String deviceGuid = request.getParameter("deviceGuid");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String nickName = request.getParameter("nickname");
		
		if(Tools.strArrIsNull(userName,deviceGuid,password))
		{
			root = new RootData("false","传入参数不能为空！","",ErrorCodeDef.PARAMETER_IS_NULL);
			return SUCCESS;
		}
		
		try {
			if(!redisFactory.isExist(8, deviceGuid))
			{
				root = new RootData("false","设备不存在！","",ErrorCodeDef.DEVICE_NOT_EXIST);
				return SUCCESS;
			}
			//判断一下设备用户名密码是否有效 dev_type dev_version dev_username dev_password
			String deviceUserName  = redisFactory.hget(8, deviceGuid, "dev_username");
			String devicePassword  = redisFactory.hget(8, deviceGuid, "dev_password");
			if(!(userName.equals(deviceUserName) && password.equals(devicePassword)))
			{
				root = new RootData("false","用户名密码错误，请重新验证！","",ErrorCodeDef.DEVICE_VERIFY_FAIL);
				return SUCCESS;
			}
		} catch (Exception e1) {
			root = new RootData("false","redis异常！","",ErrorCodeDef.REDIS_EXCEPTION);
			return SUCCESS;
		}
		
		TbUserDevice tbud = new TbUserDevice();
		TbDeviceUser tbdu = new TbDeviceUser();
		
		/*TbUserDevice*/
		tbud.setAddtime(new Timestamp((new Date()).getTime()));
		tbud.setDeviceGuid(deviceGuid);
		tbud.setPermission(0);
		tbud.setUserid(userId);
		if(!Tools.strArrIsNull(nickName))
		{
			tbud.setNickname(nickName);
		}

		/*TbDeviceUser*/
		tbdu.setAddtime(new Timestamp((new Date()).getTime()));
		tbdu.setDeviceGuid(deviceGuid);
		tbdu.setPermission(0);
		tbdu.setUserid(userId);
		
		try {
			service.userDeviceBind(tbud,tbdu,"0");
		}catch (DeviceBindByOthersException e) {
			root = new RootData("false",e.getMessage()+"失败！","",ErrorCodeDef.DEVICE_BIND_BY_OTHERS);
			return SUCCESS;
		}catch (DeviceBindSelfExistException e) {
			root = new RootData("false",e.getMessage()+"失败！","",ErrorCodeDef.DEVICE_BIND_BY_SELF);
			return SUCCESS;
		}
		catch (Exception e) {
			logger.error("userBind接口异常！",e);
			root = new RootData("false","出现异常，绑定失败！","",ErrorCodeDef.MYSQL_ERROR);
			return SUCCESS;
		}
		
		
		Map<String, String> data = new HashMap<String, String>();
		String message = "恭喜您，设备【"+deviceGuid+"】绑定成功!";
		root = new RootData("true",message,data,ErrorCodeDef.DO_SUCCESS);
		return SUCCESS;
	}
	
	private boolean isUserIdExist(String userId) throws Exception {
		TbUser user = (TbUser)SerializeUtil.getObject(0,constProperty.Account_PREFIX+userId);
		if(user != null)
		{
			return true;
		}
		/*//远程查询
		try {
			String accountSer = getConfigBean().getAccountService()+"/accountManage/accountTouserId.do?plat=2";
			Map<String,String> params = new HashMap<String, String>();
			params.put("userId", userId);
			HttpGetData hgd = new HttpGetData(accountSer,params);
			AccountData ad = hgd.Do(AccountData.class);
			String result = ad.getRoot().getResult();
			if("false".equals(result)){
				throw new RemotErrorCodeException(ad.getRoot().getMsg()+"_"+ad.getRoot().getErrorCode());
			}
			return (String) ad.getRoot().getData();
			
		} catch (Exception e) {
			throw new RemoteConnectException("远程连接失败,"+e.getMessage());
		}*/
		return false;
	}

	
	
	/*
	 * 用户解绑设备
	 * */
	public String releaseBind()
	{
		/*BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String userId = bas.getUsername();*/
		
		String deviceGuid = getRequest().getParameter("deviceGuid");
		
		String userId = "3c4c3a3aab714af9852c1e254c554178";
			
		if(Tools.strArrIsNull(deviceGuid,userId))
		{
			root = new RootData("false","传入参数不能为空！","",ErrorCodeDef.PARAMETER_IS_NULL);
			return SUCCESS;
		}
		
		
		try {
			if(!isUserIdExist(userId))
			{
				root = new RootData("false","用户不存在","",ErrorCodeDef.ACCOUNT_NO_EXIT);
				return SUCCESS;
			}
			service.deviceReleaseBind(userId,deviceGuid);
		}
		catch(DeviceBindNoExistException e)
		{
			//e.printStackTrace();
			logger.error(e,e);
			root = new RootData("false",e.getMessage(),"",ErrorCodeDef.DEVICE_USER_NOTHAVE);
			return SUCCESS;
		}
		catch (Exception e) {
			//e.printStackTrace();
			logger.error(e,e);
			root = new RootData("false","出现异常，绑定失败！","",ErrorCodeDef.MYSQL_ERROR);
			return SUCCESS;
		}
		Map<String, String> data = new HashMap<String, String>();
		root = new RootData("true","设备【"+deviceGuid+"】解绑成功！",data,ErrorCodeDef.DO_SUCCESS);
		return SUCCESS;
	}
	
	//被分享者主动删除分享(单个删除)
	public String shareeRelease()
	{
		/*BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String shareeID = bas.getUsername();*/
		String shareeID = "b15054819e364463929fa4b37753feba";
		String deviceGuid = getRequest().getParameter("deviceGuid");
		
		if(Tools.strArrIsNull(deviceGuid,shareeID))
		{
			root = new RootData("false","传入参数不能为空！","",ErrorCodeDef.PARAMETER_IS_NULL);
			return SUCCESS;
		}
		
		try {
			service.delShare(shareeID, deviceGuid);
		} catch (Exception e) {
			logger.error(e,e);
			root = new RootData("false","【"+shareeID+"】，删除分享给您的设备【"+deviceGuid+"】失败！","",ErrorCodeDef.MYSQL_ERROR);
			return SUCCESS;
		}
		Map<String, String> data = new HashMap<String, String>();
		root = new RootData("true","恭喜您【"+shareeID+"】，删除分享给您的设备【"+deviceGuid+"】成功！",data,ErrorCodeDef.DO_SUCCESS);
		return SUCCESS;
	}
	
	/**
	 * @Title: 取消一个或多个分享
	 * @Package com.jovision.deviceManage.controller
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-23 下午02:13:09
	 * @return
	 */
	public String MultiDelShare()
	{
		
/*		BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		String userId = bas.getUsername();*/
		
		String userId = "3c4c3a3aab714af9852c1e254c554178";
		
		String shareeIdArrStr = getRequest().getParameter("userIdArr");
		String deviceGuid = getRequest().getParameter("deviceGuid");
		if(Tools.strArrIsNull(shareeIdArrStr,deviceGuid,userId))
		{
			root = new RootData("false","传入参数不能为空！","",ErrorCodeDef.PARAMETER_IS_NULL);
			return SUCCESS;
		}
		
			
		String[] shareeIdArr ;
		List<String> userList  = new ArrayList<String>();
		if(shareeIdArrStr.contains(","))
		{
			try 
			{
				//远程account转为userId
				shareeIdArr = account2userIds(shareeIdArrStr);
			} 
			catch (RemotErrorCodeException rece)
			{
				logger.error("MultiDelShare接口异常！",rece);
				root = new RootData("false",rece.getMessage().split("_")[0],"",rece.getMessage().split("_")[1]);
				return SUCCESS;
			}
			catch (RemoteConnectException rce)
			{
				logger.error("MultiDelShare接口异常！",rce);
				root = new RootData("false","远程连接账号库失败！","",ErrorCodeDef.REMOTE_EXCEPTION);
				return SUCCESS;
			}
			if(shareeIdArr==null)
			{
				root = new RootData("false","账号都不存在！","",ErrorCodeDef.ACCOUNT_NO_EXIT);
				return SUCCESS;
			}
			
			//删除多个分享者
			for(int i=0;i<shareeIdArr.length;i++)
			{
				userList.add(shareeIdArr[i]);
			}
		}
		else
		{
			//删除单个分享者
			try 
			{
				//远程account转为userId
				shareeIdArrStr = account2userId(shareeIdArrStr);
				if(Tools.strArrIsNull(shareeIdArrStr))
				{
					root = new RootData("false","分享的用户不存在！","",ErrorCodeDef.ACCOUNT_NO_EXIT);
					return SUCCESS;
				}
			} 
			catch (RemotErrorCodeException rece)
			{
				logger.error("MultiDelShare接口异常！",rece);
				root = new RootData("false",rece.getMessage().split("_")[0],"",rece.getMessage().split("_")[1]);
				return SUCCESS;
			}
			catch (RemoteConnectException rce)
			{
				logger.error("MultiDelShare接口异常！",rce);
				root = new RootData("false","远程连接账号库失败！","",ErrorCodeDef.REMOTE_EXCEPTION);
				return SUCCESS;
			}
			
			userList.add(shareeIdArrStr);
		}
		
		try {
			service.batchDelShare(userId,userList, deviceGuid);
		}
		catch (DeviceBindNoExistException e) 
		{
			logger.error("MultiDelShare接口异常！",e);
			root = new RootData("false",e.getMessage(),"",ErrorCodeDef.DEVICE_USER_NOTHAVE);
			return SUCCESS;
		}
		catch (Exception e) 
		{
			logger.error("MultiDelShare接口异常！",e);
			root = new RootData("false","【"+shareeIdArrStr+"】，删除分享给您的设备【"+deviceGuid+"】失败！","",ErrorCodeDef.MYSQL_ERROR);
			return SUCCESS;
		}
		root = new RootData("true","恭喜您【"+shareeIdArrStr+"】，删除分享给您的设备【"+deviceGuid+"】成功！",new HashMap(),ErrorCodeDef.DO_SUCCESS);
		return SUCCESS;
	}
	
	
	
	
	/** @Title: DeviceController.java 
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-21 下午05:01:38
	 * @param string
	 * @throws RemoteConnectException,RemotErrorCodeException 
	 * @throws Exception 
	*/
	private String account2userId(String account) throws RemoteConnectException,RemotErrorCodeException   {
		
		//通过redis将account转为userId
		TbUser user=null;
		
		try {
			user = (TbUser) SerializeUtil.getObject(0,constProperty.Account_PREFIX+account);
		} catch (Exception e) {
			logger.error("redis数据库异常,"+e.getMessage(), e);
		}
		if(user!=null)
		{
			return user.getUserid();
		}
		try {
			String accountSer = getConfigBean().getAccountService()+"/accountManage/accountTouserId.do?plat=2";
			Map<String,String> params = new HashMap<String, String>();
			params.put("account", account);
			HttpGetData hgd = new HttpGetData(accountSer,params);
			AccountData ad = hgd.Do(AccountData.class);
			String result = ad.getRoot().getResult();
			if("false".equals(result)){
				throw new RemotErrorCodeException(ad.getRoot().getMsg()+"_"+ad.getRoot().getErrorCode());
			}
			return (String) ad.getRoot().getData();
			
		} catch (Exception e) {
			throw new RemoteConnectException("远程连接失败,"+e.getMessage());
		}
	}

	/**
	 * 获取多个用户Id
	 * @author Hellon(刘海龙) 
	 * @param account
	 * @return
	 * @throws Exception
	 */
	private String[] account2userIds(String accountArr) throws RemoteConnectException,RemotErrorCodeException {
		
		String[] account = accountArr.split(",");
		
		List<String> list = new ArrayList<String>();
		//通过redis将account转为userId
		TbUser user=null;
		
		try {
			for(int i=0;i<account.length;i++){
				user = (TbUser) SerializeUtil.getObject(0,constProperty.Account_PREFIX+account[i]);
				if(user!=null)
				{
					list.add(user.getUserid());
				}
			}
		} catch (Exception e) {
			logger.error("redis数据库异常,"+e.getMessage(), e);
		}
		try {
			if(list.size() != account.length){
				String accountSer = getConfigBean().getAccountService()+"/accountManage/accountArr2userId.do?plat=2";
				Map<String,String> params = new HashMap<String, String>();
				params.put("accountArr", accountArr);
				HttpGetData hgd = new HttpGetData(accountSer,params);
				AccountData ad = hgd.Do(AccountData.class);
				String result = ad.getRoot().getResult();
				
				if("false".equals(result)){
					throw new RemotErrorCodeException(ad.getRoot().getMsg()+"_"+ad.getRoot().getErrorCode());
				}
				String userids = (String) ad.getRoot().getData();
				if(userids == null){
					return null;
				}
				System.out.println(userids);
				return userids.split(",");
			}else{
				return list.toArray(new String[account.length]);
			}
		} catch (Exception e) {
			throw new RemoteConnectException("远程连接失败,"+e.getMessage());
		}
	}
	
	public String SharedUsers(){
		
		/*BizAccSession bas = getBizAccSession();
		
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}*/
		
		String deviceGuid = getRequest().getParameter("deviceGuid");
		
		if(Tools.strIsNull(deviceGuid)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		List list = null;
		try {
			list = service.getUsers(deviceGuid);
			root = new RootData("true","设备用户列表获取成功",list,ErrorCodeDef.DO_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			root = new RootData("false","设备用户列表获取失败","",ErrorCodeDef.MYSQL_ERROR);
		}
		return SUCCESS;
	}	
	
	/**
	 * 设备修改
	 * @author Hellon(刘海龙) 
	 * @return
	 */
	public String Modify(){
		
		BizAccSession bas = getBizAccSession();
		
		if(bas == null){
			root = new RootData("false","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String deviceGuid = getRequest().getParameter("deviceGuid");
		String deviceName = getRequest().getParameter("deviceName");
		String deviceUsername = getRequest().getParameter("deviceUsername");
		String devicePassword = getRequest().getParameter("devicePassword");
		String userId = bas.getUsername();//用户名
		
		if(Tools.strIsNull(deviceGuid)||Tools.strIsNull(deviceName)||Tools.strIsNull(deviceUsername)||Tools.strIsNull(devicePassword)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		
		TbDeviceBasic tdb = new TbDeviceBasic();
		tdb.setDeviceGuid(deviceGuid);
		//tdb.setDeviceName(deviceName);
		tdb.setDeviceUsername(deviceUsername);
		tdb.setDevicePassword(devicePassword);
		root = new RootData("true","设备修改成功","",ErrorCodeDef.DO_SUCCESS);
		try {
			int re = service.modify(tdb,userId,deviceName);
			if(re == 1){
				root = new RootData("false","该设备不存在","",ErrorCodeDef.DEVICE_NOT_EXIST);
			}else if(re == 2){
				root = new RootData("false","用户没有绑定该设备","",ErrorCodeDef.DEVICE_USER_NOTHAVE);
			}
		} catch (Exception e) {
			root = new RootData("false","设备修改失败","",ErrorCodeDef.MYSQL_ERROR);
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * @Title: 设备是否在线
	 * @Package com.jovision.deviceManage.controller
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-29 下午04:39:00
	 * @return
	 */
	public String IsActive()
	{
		String deviceGuid = getRequest().getParameter("deviceGuid");
		try {
			Set set = redisFactory.getSet(0,deviceGuid);
			Map map = new HashMap();
			if(set!=null && set.size()>0)
			{
				map.put("isActive", true);
				root = new RootData("true","设备【"+deviceGuid+"】在线",map,ErrorCodeDef.DO_SUCCESS);
				return SUCCESS;
			}
			map.put("isActive", false);
			root = new RootData("true","设备【"+deviceGuid+"】不在线",map,ErrorCodeDef.DEVICE_IS_NOT_ACTIVE);
		} catch (RedisException e) {
			e.printStackTrace();
			logger.error(e, e);
			root = new RootData("false","出现异常，设备【"+deviceGuid+"】不在线","",ErrorCodeDef.DEVICE_IS_NOT_ACTIVE);
		}
		return SUCCESS;
	}
	
	public RootData getRoot() {
		return root;
	}

	public void setRoot(RootData root) {
		this.root = root;
	}
	
	public Timestamp timeFormat(String originalTime)
	{
		/*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date start = (Date) dateFormat.parse(originalTime);
			return  new Timestamp(start.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		return null;
	}
	
	
	/**
	 * 删除map对象里的字段
	 * @author Hellon(刘海龙) 
	 * @param map
	 */
	private void removeAttr(Map<String,String> map){
		map.remove("alarmSwitch");
		map.remove("cloudSwitch");
	}
	
	/*
	 * 设备添加（涉及两个表）TbDeviceBasic,TbDeviceIpc
	 */
	public String add(){
		HttpServletRequest  request = getRequest();
		TbDeviceBasic tdb = new TbDeviceBasic();
		TbDeviceIpc tdi =  new TbDeviceIpc();
		tdb.setDeviceGuid(request.getParameter("deviceGuid"));
		tdb.setDeviceIp(request.getParameter("deviceIp"));
		tdb.setDeviceName(request.getParameter("deviceName"));
		tdb.setDevicePassword(request.getParameter("devicePassword"));
		tdb.setDeviceType(Integer.parseInt(request.getParameter("deviceType")));
		tdb.setDeviceUsername(request.getParameter("deviceUsername"));
		tdb.setOnlineTime(timeFormat(request.getParameter("onlineTime")));
		tdb.setRegisterTime(timeFormat(request.getParameter("registerTime")));
		
		tdi.setAlarmSwitch(Integer.parseInt(request.getParameter("alarmSwitch")));
		tdi.setCloudSwitch(Integer.parseInt(request.getParameter("cloudSwitch")));
		tdi.setDeviceGuid(request.getParameter("deviceGuid"));
		tdi.setDeviceSubType(Integer.parseInt(request.getParameter("deviceSubType")));
		tdi.setDeviceVersion(request.getParameter("deviceVersion"));
		
		try {
			service.deviceAdd(tdb, tdi);
		} catch (DeviceExistException e) {
			root = new RootData("false",e.getMessage(),null,"");
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			root = new RootData("false","出现异常，添加设备失败！",null,"");
			return SUCCESS;
		}
		
		root = new RootData("true","恭喜您，添加设备成功！","","");
		return SUCCESS;
	}
	
}
