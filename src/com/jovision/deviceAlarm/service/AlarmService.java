/**
 * 
 */
package com.jovision.deviceAlarm.service;

import java.util.ArrayList;
import java.util.List;

import com.jovision.Exception.DeviceBindNoExistException;
import com.jovision.deviceAlarm.dao.AlarmDao;
import com.jovision.domain.TbDeviceAlarm;
import com.jovision.domain.TbDeviceIpc;

/**
 * @Title: AlarmService.java 
 * @Package com.jovision.deviceAlarm.service
 * @author Hellon(刘海龙) Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-15 上午10:34:10
 */
public class AlarmService {

	public List<TbDeviceAlarm> ListData(String deviceGuid,String userId,int pageSize,int pageNum) throws Exception{
		/*由于架构问题，redis暂时不考虑*/
		/*//1.查询redis
		long totalNum = redisFactory.getLLength(constProperty.DEVICE_ALARM+deviceGuid+"_"+userId);
		//1.1一共几页
		int totalPageNum = (int)totalNum/pageSize+1;
		if(totalPageNum < pageSize+1)
		{
			throw new DeviceExistException("参数有误！");
		}
		//1.2查看是否是最后一页
		int start,end;
		if((totalNum-pageNum*pageSize)<pageSize)
		{
			start = (int) (totalNum-pageNum*pageSize);
			end = (int)totalNum - 1; 
		}*/
		
		//2.redis不存在查数据库
		String hql = " select new map(" +
					 " tda.deviceGuid as deviceGuid," +
					 " tda.userId as userId," +
					 " date_format(tda.alarmTime,'%Y-%m-%d %H:%i:%s') as alarmTime," +
					 " tda.alarmType as alarmType ) " +
					 " from TbDeviceAlarm as tda " +
					 " where tda.deviceGuid = '" + deviceGuid + "' and " +
					 " tda.userId = '" + userId + "' " +
					 " order by tda.alarmTime desc";
		AlarmDao alarmDao = new AlarmDao();
		List<TbDeviceAlarm> alarmList = new ArrayList<TbDeviceAlarm>();
		try {
			 alarmList = alarmDao.getList(hql, pageSize, pageNum-1);
			
		} catch (Exception e) {
			throw e;
		}
		return alarmList;
	}
	
	/**
	 * 报警开关切换
	 * @author Hellon(刘海龙) 
	 * @param deviceGuid 设备号
	 * @param userId 用户Id
	 * @param switchFlag 开关标记 0关 1开
	 * @return 0 成功 1 该设备不存在 2 用户没有绑定该设备
	 * @throws Exception
	 */
	public int changSwitch(String deviceGuid,String userId,String switchFlag) throws Exception{
		AlarmDao alarmDao = new AlarmDao();
		String userHql = "from TbUserDevice tud where tud.deviceGuid = '" + deviceGuid+"' and tud.userid='" + userId +"'";
		try {
			List userList =  alarmDao.getList(userHql);
			if(userList.size() == 0){
				return 2;//该用户没有绑定该设备
			}
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常;"+e.getMessage());
		}
		
		String hql = "from TbDeviceIpc tdi where tdi.deviceGuid = '" + deviceGuid+"'";
		List list = null;
		try {
			list =  alarmDao.getList(hql);
			if(list.size()>0){
				TbDeviceIpc tdi = (TbDeviceIpc) list.get(0);
				tdi.setAlarmSwitch(Integer.parseInt(switchFlag));
				alarmDao.update(tdi);
				return 0;
			}else{
				return 1;//设备不存在
			}
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常;"+e.getMessage());
		}
	}
	
	public TbDeviceIpc SwitchQuery(String deviceGuid,String userId) throws Exception{
		AlarmDao alarmDao = new AlarmDao();
		String userHql = "from TbUserDevice tud where tud.deviceGuid = '" + deviceGuid+"' and tud.userid='" + userId +"'";
		try {
			List userList =  alarmDao.getList(userHql);
			if(userList.size() == 0){//用户没有绑定该设备
				return null;
			}
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常;"+e.getMessage());
		}
		
		String hql = "from TbDeviceIpc tdi where tdi.deviceGuid = '" + deviceGuid+"'";
		List list = null;
		try {
			list =  alarmDao.getList(hql);
			TbDeviceIpc tdi = (TbDeviceIpc) list.get(0);
			return tdi;
		} catch (Exception e) {
			throw new Exception("用户修改设备操作异常;"+e.getMessage());
		}
	}
}
