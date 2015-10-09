package com.jovision.deviceManage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jovision.Exception.DeviceBindByOthersException;
import com.jovision.Exception.DeviceBindSelfExistException;
import com.jovision.Exception.DeviceBindNoExistException;
import com.jovision.Exception.DeviceExistException;
import com.jovision.Exception.RedisException;
import com.jovision.common.constProperty;
import com.jovision.dao.IpcDeviceDao;
import com.jovision.domain.DeviceWhole;
import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.domain.TbDeviceUser;
import com.jovision.domain.TbUserDevice;
import com.jovision.redisDao.redisFactory;
import com.jovision.util.SerializeUtil;
import com.jovision.utils.Tools;

/**
 * 
 * @Title: DeviceService.java 
 * @Package com.jovision.service
 * @author Hellon(刘海龙) Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午04:09:43
 */
public class DeviceService {
	private static Logger logger = Logger.getLogger(DeviceService.class);  
	
	private IpcDeviceDao cdao = new IpcDeviceDao();
	
	/**
	 * 用户获取设备或被分享设备列表
	 * @author Hellon(刘海龙) 
	 * @param userId 用户ID
	 * @param flag 获取用户拥有设备还是被分享设备列表标记 0用户拥有的设备 1 用户被分享的设备
	 * @param pagesize 每页查询的条数
	 * @param pagenum 第几页以0页开始 
	 * @return 获取的设备列表List
	 * @throws Exception
	 * */
	public List deviceList(String userId,String permission, int pagesize, int pagenum) throws Exception{
	
		boolean redisError = false;
		List redisList = new ArrayList();
		try {
			Set<String>  deviceSet0 = redisFactory.getSet(0,constProperty.USERID_PREFIX+userId+"_0");
			redisDo(redisList, deviceSet0,"0");
			Set<String>  deviceSet1 = redisFactory.getSet(0,constProperty.USERID_PREFIX+userId+"_1");
			redisDo(redisList, deviceSet1,"1");
			
			if(redisList.size()>0){
				return redisList;
			}
			
		} catch (RedisException e) {
			logger.error("deviceList方法使用redis数据库操作失败", e);
			redisError = true;
		}
		
		String queyTud = "from TbUserDevice tud where tud.userid = '" + userId +"'"; 
		List<Map<String,String>> deviceList = new ArrayList<Map<String,String>>();
		try {
			List tudList = cdao.getList(queyTud, pagesize, pagenum);
			StringBuilder sb = new StringBuilder("(");
			
			Map<String,TbUserDevice> tdbMap = new HashMap<String, TbUserDevice>();
			
			for(int i=0;i<tudList.size();i++){
				TbUserDevice tud = (TbUserDevice) tudList.get(i);
				
				tdbMap.put(tud.getDeviceGuid(), tud);
				
				if(i == tudList.size()-1){
					sb.append("'").append(tud.getDeviceGuid()).append("')");
				}else{
					sb.append("'").append(tud.getDeviceGuid()).append("',");
				}
			}
			
			if(tudList.size()>0){
				String hql = "from TbDeviceBasic tdb where tdb.deviceGuid in " + sb.toString();
				
				List list = cdao.getList(hql, pagesize, pagenum);
				for(int i=0;i<list.size();i++){
					TbDeviceBasic tdb = (TbDeviceBasic) list.get(i);
					//TbDeviceBasic表里的字段
					Map<String,String> returnMap = new HashMap<String, String>();
					returnMap.put("deviceGuid", tdb.getDeviceGuid());
					returnMap.put("deviceType", tdb.getDeviceType()+"");
					returnMap.put("deviceUsername", tdb.getDeviceUsername());
					returnMap.put("devicePassword", tdb.getDevicePassword());
					returnMap.put("deviceVersion", tdb.getDeviceVersion());
					//TbUserDevice表里的字段
					returnMap.put("permission", tdbMap.get(tdb.getDeviceGuid()).getPermission()+"");
					returnMap.put("deviceName", tdbMap.get(tdb.getDeviceGuid()).getNickname());
					
					deviceList.add(returnMap);
					if(!redisError){
						redisFactory.put2Set(0,constProperty.USERID_PREFIX+userId+"_"+tdbMap.get(tdb.getDeviceGuid()).getPermission(), tdb.getDeviceGuid());
						// dev_type dev_version dev_username dev_password
						redisFactory.hset(8, tdb.getDeviceGuid(), "dev_type", tdb.getDeviceType()==null?"":tdb.getDeviceType()+"");
						redisFactory.hset(8, tdb.getDeviceGuid(), "dev_username", tdb.getDeviceUsername()==null?"":tdb.getDeviceUsername());
						redisFactory.hset(8, tdb.getDeviceGuid(), "dev_password", tdb.getDevicePassword()==null?"":tdb.getDevicePassword());
						redisFactory.hset(8, tdb.getDeviceGuid(), "dev_version", tdb.getDeviceVersion()==null?"":tdb.getDeviceVersion());
						redisFactory.hset(8, tdb.getDeviceGuid(), "dev_nickname", tdbMap.get(tdb.getDeviceGuid()).getNickname()==null?"":tdbMap.get(tdb.getDeviceGuid()).getNickname());
					}
					
				}
			}
			
		} catch (Exception e) {
			//logger.error("查询用户设备列表失败", e);
			throw new Exception("查询用户设备列表失败"+e.getMessage());
		}
	
		return deviceList;
	}

	/**
	 * 从redis数据库中获取用户设备列表信息
	 * @author Hellon(刘海龙) 
	 * @param redisList
	 * @param deviceSet
	 * @param flag
	 * @throws RedisException
	 */
	private void redisDo(List redisList, Set<String> deviceSet,String permission) throws RedisException {
		if(deviceSet.size()>0){
			Iterator<String> it = deviceSet.iterator();
			while(it.hasNext()){
				String dguid = it.next();
				// dev_type dev_version dev_username dev_password
				String deviceUserName  = redisFactory.hget(8, dguid, "dev_username");
				String devicePassword  = redisFactory.hget(8, dguid, "dev_password");
				String deviceType  = redisFactory.hget(8, dguid, "dev_type");
				String deviceVersion  = redisFactory.hget(8, dguid, "dev_version");
				String deviceNickName  = redisFactory.hget(8, dguid, "dev_nickname");
				
				Map<String,String> returnMap = new HashMap<String, String>();
				returnMap.put("deviceGuid", dguid);
				returnMap.put("deviceType", deviceType);
				returnMap.put("deviceUsername", deviceUserName);
				returnMap.put("devicePassword", devicePassword);
				returnMap.put("deviceVersion", deviceVersion);
				//TbUserDevice表里的字段
				returnMap.put("permission", permission);
				returnMap.put("deviceName", deviceNickName);
				
				redisList.add(returnMap);
			}
		}
	}

	/**
	 * 设备对象转为map对象
	 * @author Hellon(刘海龙) 
	 * @param dw
	 * @return
	 */
	private Map<String,String> bean2ap(DeviceWhole dw) {
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("deviceGuid", dw.getTdb().getDeviceGuid());
		returnMap.put("deviceType", dw.getTdb().getDeviceType()+"");
		returnMap.put("deviceName", dw.getTdb().getDeviceName());
		returnMap.put("deviceUsername", dw.getTdb().getDeviceUsername());
		returnMap.put("devicePassword", dw.getTdb().getDevicePassword());
		returnMap.put("deviceSubType", dw.getTdi().getDeviceSubType()+"");
		returnMap.put("deviceVersion", dw.getTdi().getDeviceVersion());
		returnMap.put("alarmSwitch", dw.getTdi().getCloudSwitch()+"");
		returnMap.put("cloudSwitch", dw.getTdi().getCloudSwitch()+"");
		return returnMap;
	}
	
	
	public void deviceAdd(TbDeviceBasic tdb,TbDeviceIpc tdi) throws DeviceExistException{
		IpcDeviceDao ipcDeviceDao = new IpcDeviceDao();
		if("exist".equals(redisFactory.getSingleData(0,constProperty.EXIST_PREFIX+tdb.getDeviceGuid())))
		{
			throw new DeviceExistException("设备已存在");
		}
		else 
		{
			//不存在，走数据库
			String hql = "select count(*) from TbDeviceBasic as tdb where tdb.deviceGuid ='"+tdb.getDeviceGuid()+"'";
			List list = cdao.getList(hql);
			if(list!=null && (Long)list.get(0)>0)
			{
				//同时将设备信息放入redis
				redisFactory.setSingleData(0,constProperty.EXIST_PREFIX+tdb.getDeviceGuid(), "exist");
				throw new DeviceExistException("设备已存在");
			}
		}
		
		ipcDeviceDao.addDevice(tdb, tdi);
		redisFactory.setSingleData(0,constProperty.EXIST_PREFIX+tdb.getDeviceGuid(), "exist");
	}
	
	//用户添加设备
	public void userDeviceBind(TbUserDevice tud,TbDeviceUser tdu,String  permission) throws Exception{
		
			String deviceGuid = tud.getDeviceGuid();
			String userID = tud.getUserid();
			
			//该设备是否已经被其他人绑定过
			if(isDeviceBindByOther(userID,deviceGuid))
			{
				throw new DeviceBindByOthersException("该设备已被其他用户绑定！");
			}
			
			//自己是否绑定过改设备
			if(isUserDeviceBindExist(userID,deviceGuid,permission))
			{
				throw new DeviceBindSelfExistException("您已绑定过该设备！");
			}
		
		try {
			/*List<TbUserDevice> active_tbud_list =new ArrayList<TbUserDevice>();
			active_tbud_list.add(tud);
			cdao.batchSave4Bind(active_tbud_list,dw,permission,true);*/
			
			cdao.save(tdu);
			cdao.save(tud);
			redisFactory.put2Set(0,constProperty.USERID_PREFIX+tud.getUserid()+"_"+permission, tud.getDeviceGuid());
            redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+tud.getDeviceGuid(),tud.getUserid()+"_"+permission);	
            if (!Tools.strIsNull(tud.getNickname()))
            {
            	redisFactory.hset(8, tud.getDeviceGuid(), "dev_nickname", tud.getNickname());
            }
		} catch (Exception e) {
			throw e;
		}

	}
	
	/**
	 * 判断设备是否被其他人绑定
	 * @param userID
	 * @param deviceGuid
	 */
	private boolean isDeviceBindByOther(String userID, String deviceGuid) {
		String hql = "from TbUserDevice as tud " +
			 	     " where tud.deviceGuid ='" + deviceGuid + "' and " +
			         " tud.userid != '" + userID +"' and tud.permission=0";
		List<TbUserDevice> list=cdao.getList(hql);
		if(list!=null && list.size()>0)
		{
			return true;
		}
		return false;
	}

	public void userShareDevice(String userId,List<TbUserDevice> tbud_list,List<TbDeviceUser> tbdu_list, String deviceGuid,String  permission) throws Exception{
		
		if(!redisFactory.isExist(8, deviceGuid))
	    {
	    	throw new DeviceExistException("分享的设备不存在！");
	    }
		
		List<TbUserDevice> active_tbud_list = new ArrayList<TbUserDevice>();
		List<TbDeviceUser> active_tbdu_list = new ArrayList<TbDeviceUser>();
		
		if(!isUserDeviceBindExist(userId,tbud_list.get(0).getDeviceGuid(),constProperty.BIND))
		{
			throw new DeviceBindNoExistException("该设备不属于您！你不能分享");
		}
		
		for(int i=0;i<tbud_list.size();i++)
		{
			TbUserDevice tud = tbud_list.get(i);
			TbDeviceUser tdu = tbdu_list.get(i);
			String userID = tud.getUserid();
			
			//如果以分享过，pass
			if(isUserDeviceBindExist(userID,deviceGuid,permission))
			{
				continue;
			}
			
			active_tbud_list.add(tud);
			active_tbdu_list.add(tdu);
		}
		
		if(active_tbud_list==null || active_tbud_list.size()==0)
		{
			//如果都已分享过，依然提醒分享成功
			return;
		}
		
		
		try {
			cdao.batchSave4Share(active_tbud_list, active_tbdu_list, deviceGuid, permission);
		} catch (Exception e) {
			throw e;
		}

	}
	
	/*
	 * 用户解绑设备,
	 * 分享者解绑，删除该设备对应的所有用户（包括分享者和被分享者）
	 * */
	public void deviceReleaseBind(String userId, String deviceGuid) throws Exception {
		
		if(!isUserDeviceBindExist(userId, deviceGuid, constProperty.BIND))
		{
			throw new DeviceBindNoExistException("该设备不属于您！");
		}
		
		
		
		//0.先查询出设备对应的用户,
		String hql_UserDeviceList =" from TbUserDevice as tud" +
								   " where tud.deviceGuid ='"+deviceGuid+"'";
		List<TbUserDevice> UserDeviceList = new ArrayList<TbUserDevice>();
		UserDeviceList = cdao.getList(hql_UserDeviceList);
		
		if(UserDeviceList != null && UserDeviceList.size()>0)
		{
			//循环删除userFindDevice redis
			for(TbUserDevice tud:UserDeviceList)
			{
				if(tud!=null)
				{
					redisFactory.del1SetValue(0,constProperty.USERID_PREFIX+tud.getUserid()+"_"+tud.getPermission(), deviceGuid);
				}
			}
		}
		
		
		//1.数据库中删除
		String hql_tud = " delete from TbUserDevice tbud " +
					     " where deviceGuid= '" + deviceGuid + "'";
		cdao.executeHql(hql_tud);
		
		String hql_tdu = " delete from TbDeviceUser tbdu " +
		                 " where deviceGuid= '" + deviceGuid + "'";
		cdao.executeHql(hql_tdu);
		
		//2.redis删除设备
		redisFactory.del(0,constProperty.DEVICEGUID_PREFIX+deviceGuid);
		redisFactory.del(0,constProperty.DEVICE+deviceGuid);
		
		
	}
	
	/*
	 * 被分享用户主动删除分享
	 * */
	public void delShare(String userID, String deviceGuid) throws Exception {
		//0.看一下设备是否分享给了自己
		Set user_set = redisFactory.getSet(0, constProperty.DEVICEGUID_PREFIX+deviceGuid);
		if(!user_set.contains(userID+"_"+constProperty.SHARE))
		{
			throw new DeviceBindNoExistException("该设备没有分享给你，无权取消分享！");
		}
		
		//1.数据库中删除
		String hql_tbud = " delete from TbUserDevice tbud " +
					      " where tbud.userid= '" + userID + "'" + " and " +
					      " deviceGuid= '" + deviceGuid+"'";
		
		String hql_tbdu = " delete from TbDeviceUser tbdu " +
		 				  " where tbdu.userid= '" + userID + "'" + " and " +
		                  " deviceGuid= '" + deviceGuid+"'";
		
		try {
			cdao.executeHql(hql_tbud);
			cdao.executeHql(hql_tbdu);
			//2.redis删除deviceFinduser
			redisFactory.del1SetValue(0,constProperty.DEVICEGUID_PREFIX+deviceGuid, userID+"_"+constProperty.SHARE);
			//3.redis删除userFindDevice
			redisFactory.del1SetValue(0,constProperty.USERID_PREFIX+userID+"_"+constProperty.SHARE, deviceGuid);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	//用户批量解除分享
	public void batchDelShare(String userId,List<String> userList,String deviceGuid) throws Exception
	{
		if(!isUserDeviceBindExist(userId, deviceGuid, constProperty.BIND))
		{
			throw new DeviceBindNoExistException("该设备不属于您，您无权取消分享！");
		}
		
		//1.数据库中批量删除
		cdao.batchDel(userList,deviceGuid);
		//2.redis中循环删除
		for(String userID:userList)
		{
			//2.1 redis删除deviceFinduser
			redisFactory.del1SetValue(0,constProperty.DEVICEGUID_PREFIX+deviceGuid, userID+"_"+constProperty.SHARE);
			//2.2 redis删除userFindDevice
			redisFactory.del1SetValue(0,constProperty.USERID_PREFIX+userID+"_"+constProperty.SHARE, deviceGuid);
		}
	}
	
	/**
	 * 根据设备号查询分享的用户ID
	 * @author Hellon(刘海龙) 
	 * @param dguid
	 * @return
	 * @throws Exception 
	 */
	public List getUsers(String dguid) throws Exception{
		
		boolean redisError = false;
		try {
			Set<String>  deviceSet = redisFactory.getSet(0,constProperty.DEVICEGUID_PREFIX+dguid);
			if(deviceSet.size()>0){
				List list = new ArrayList();
				Iterator<String> it = deviceSet.iterator();
				while(it.hasNext()){
					String[] userid = it.next().split("_");
					Map<String,String> map = new HashMap<String,String>();
					map.put("userid", userid[0]);
					map.put("permission", userid[1]);
					list.add(map);
				}
				return list;
			}
		} catch (RedisException e) {
			// TODO Auto-generated catch block
			redisError = true;
			logger.error(DeviceService.class.getName()+" getUsers方法,redis异常", e);
		}
		
		String hql = "select new map(tud.userid as userid," +
					"tud.permission as permission) " +
					"from TbDeviceUser tdu " +
					"where tdu.deviceGuid = '" + dguid + "'";
		
		List list = null;
		try {
			list = cdao.getList(hql, 100, 0);
			String[] strs = new String[list.size()];
			for(int i=0;i<list.size();i++){
				Map<String,Object> map = (Map<String, Object>) list.get(i);
				String jsonStr = map.get("userid") + "_" + map.get("permission");
				strs[i] = jsonStr;
			}
			
			if(strs.length>0){
				redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+dguid, strs);
			}
			
			return list;
		} catch (Exception e) {
			//logger.error("查询用户设备列表失败",e);
			throw new Exception("设备获取用户列表失败;"+e.getMessage());
		}
	}
	
	
	/**
	 * 设备修改信息
	 * @author Hellon(刘海龙)
	 * @throws Exception 
	 */
	public int modify(TbDeviceBasic tdb,String userId,String deviceName) throws Exception{
		
		String userHql = "from TbUserDevice tud where tud.deviceGuid = '" + tdb.getDeviceGuid()+"' and tud.userid='" + userId +"'";
		try {
			List userList =  cdao.getList(userHql);
			if(userList.size() == 0){
				return 2;//该用户没有绑定该设备
			}
			/*新加  向用tb_user_device表修改昵称  2015.10.08*/
			TbUserDevice tud = (TbUserDevice)userList.get(0);
			tud.setNickname(deviceName);
			cdao.update(tud);
			redisFactory.hset(8, tud.getDeviceGuid(), "dev_nickname", deviceName);
		
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常!"+e.getMessage());
		}
		
		String hql = "from TbDeviceBasic tdb where tdb.deviceGuid = '" + tdb.getDeviceGuid()+"'";
		List list = null;
		List list_tdi = null;
		try {
			list =  cdao.getList(hql);
			
			if(list.size()>0){
					TbDeviceBasic mofifyTdb = (TbDeviceBasic) list.get(0);
					//mofifyTdb.setDeviceName(tdb.getDeviceName());
					mofifyTdb.setDevicePassword(tdb.getDevicePassword());
					mofifyTdb.setDeviceUsername(tdb.getDeviceUsername());
					cdao.update(mofifyTdb);
					String hql_tdi = " from TbDeviceIpc as tdi where tdi.deviceGuid ='"+tdb.getDeviceGuid()+"'";
					list_tdi = cdao.getList(hql_tdi);
			}else{
				return 1;//设备不存在
			}
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常;"+e.getMessage());
		}
		if(list!=null && list.size()>0)//设备不存在
		{
			TbDeviceBasic tdb1 = (TbDeviceBasic)list.get(0);
			TbDeviceIpc   tdi =  (TbDeviceIpc)list_tdi.get(0);
			DeviceWhole dw = new DeviceWhole(tdb1, tdi);
			
			//将整个设备合成对象存入redis
			try {
				SerializeUtil.setObject(0,constProperty.DEVICE+tdb.getDeviceGuid(), dw);
			} catch (Exception e) {
				logger.error(this.getClass().getName()+".modify()方法操作redis数据库失败",e);
			}
			return 0;//设备修改成功
		}else{
			return 1;//设备不存在
		}
	}
	
	
    public boolean isDeviceExist4Bind(String deviceGuid) throws Exception
    {
    	DeviceWhole dw=(DeviceWhole)SerializeUtil.getObject(0,constProperty.DEVICE+deviceGuid);
    	if(dw == null)
		{
    		//不存在，走数据库
			String hql_tdb = " from TbDeviceBasic as tdb where tdb.deviceGuid ='"+deviceGuid+"'";
			String hql_tdi = " from TbDeviceIpc as tdi where tdi.deviceGuid ='"+deviceGuid+"'";
			List list_tdb = cdao.getList(hql_tdb);
			List list_tdi = cdao.getList(hql_tdi);
			if(list_tdb!=null && list_tdb.size()>0)
			{
				TbDeviceBasic tdb = (TbDeviceBasic)list_tdb.get(0);
				TbDeviceIpc   tdi =  (TbDeviceIpc)list_tdi.get(0);
				dw = new DeviceWhole(tdb, tdi);
				//将整个设备合成对象存入redis
				SerializeUtil.setObject(0,constProperty.DEVICE+deviceGuid, dw);
				return true;
			}
			else
			{
				return false;
			}
		}
		
    	return true;
		
    	
    }

    public DeviceWhole isDeviceExist(String deviceGuid) throws Exception
    {
    	DeviceWhole dw=(DeviceWhole)SerializeUtil.getObject(0,constProperty.DEVICE+deviceGuid);
    	if(dw == null)
		{
    		//不存在，走数据库
			String hql_tdb = " from TbDeviceBasic as tdb where tdb.deviceGuid ='"+deviceGuid+"'";
			String hql_tdi = " from TbDeviceIpc as tdi where tdi.deviceGuid ='"+deviceGuid+"'";
			List list_tdb = cdao.getList(hql_tdb);
			List list_tdi = cdao.getList(hql_tdi);
			if(list_tdb!=null && list_tdb.size()>0)
			{
				TbDeviceBasic tdb = (TbDeviceBasic)list_tdb.get(0);
				TbDeviceIpc   tdi =  (TbDeviceIpc)list_tdi.get(0);
				dw = new DeviceWhole(tdb, tdi);
				//将整个设备合成对象存入redis
				SerializeUtil.setObject(0,constProperty.DEVICE+deviceGuid, dw);
				return dw;
			}
			else
			{
				return null;
			}
		}
		
    	return dw;
		
    	
    }

	
	private boolean isUserDeviceBindExist(String userID,String deviceGuid,String permission) throws RedisException
	{
		//1.看redis中二者关系是否存在
		
		Set deviceSet = redisFactory.getSet(0,constProperty.USERID_PREFIX+userID+"_"+permission);
		if(deviceSet != null && deviceSet.contains(deviceGuid))
		{
			return true;
		}

		//2.redis不存在，看数据库中是否存在
		String hql = " select count(*) from TbUserDevice as tud " +
					 " where tud.deviceGuid ='"+deviceGuid+"' and "+
					 " tud.userid = '" + userID +"'";
		List list = cdao.getList(hql);
		if(list!=null && (Long)list.get(0)>0)
		{
			redisFactory.put2Set(0,constProperty.USERID_PREFIX+userID+"_"+permission, deviceGuid);
			redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+deviceGuid, userID+"_"+permission);
			return true;
		}
		return false;
		
	}	
	

	
	
}
