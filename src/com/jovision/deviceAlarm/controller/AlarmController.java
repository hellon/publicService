/**
 * 
 */
package com.jovision.deviceAlarm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jovision.aes.BizAccSession;
import com.jovision.common.ErrorCodeDef;
import com.jovision.common.ZeroClient;
import com.jovision.common.constProperty;
import com.jovision.controller.BaseController;
import com.jovision.deviceAlarm.service.AlarmService;
import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.http.modelBean.RootData;
import com.jovision.utils.Tools;

/**
 * @Title: AlarmController.java 
 * @Package com.jovision.deviceAlarm.controller
 * @author Hellon(刘海龙)
 * @Description: TODO() 
 * @date 2015-9-15 上午10:32:29
 */
public class AlarmController extends BaseController {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RootData root = new RootData();
	private AlarmService service = new AlarmService();
	
	public String ListData(){
		
		BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("true","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String deviceGuid = getRequest().getParameter("deviceGuid");
		String userId = bas.getUsername();
		String pageNum = getRequest().getParameter("pageNum");
		String pageSize = getRequest().getParameter("pageSize");
		
		if(Tools.strIsNull(pageNum)){
			pageNum = "1";
		}
		
		if(Tools.strIsNull(pageSize)){
			pageSize = "100";
		}
		
		
		List list;
		try {
			
			list = service.ListData(deviceGuid,userId, Integer.parseInt(pageSize), Integer.parseInt(pageNum));
			root = new RootData("true","报警列表获取成功",list,ErrorCodeDef.DO_SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			root = new RootData("false","报警列表获取失败","",ErrorCodeDef.MYSQL_ERROR);
		}
		
		return SUCCESS;
		
	}

	/**
	 * 报警开关切换
	 * @author Hellon(刘海龙) 
	 * @return
	 */
	public String SwitchChange(){
		
		BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("true","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String deviceGuid = getRequest().getParameter("deviceGuid");//设备号
		String switchFlag = getRequest().getParameter("switchFlag");//开关标记 1开 0关
		
		String userId = bas.getUsername();
		
		if(Tools.strIsNull(deviceGuid)||Tools.strIsNull(switchFlag)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		if(constProperty.ALARM_ON.equals(switchFlag.trim()) || constProperty.ALARM_OFF.equals(switchFlag.trim())){//报警开关
			try {
				int re = service.changSwitch(deviceGuid,userId,switchFlag);
				if(re == 1){
					root = new RootData("false","该设备不存在","",ErrorCodeDef.DEVICE_NOT_EXIST);
				}else if(re == 2){
					root = new RootData("false","用户没有绑定该设备","",ErrorCodeDef.DEVICE_USER_NOTHAVE);
				}else{
					if(constProperty.ALARM_ON.equals(switchFlag.trim())){
						root = new RootData("true","报警开关打开成功","",ErrorCodeDef.DO_SUCCESS);
					}else{
						root = new RootData("true","报警开关关闭成功","",ErrorCodeDef.DO_SUCCESS);
					}
				}
			} catch (Exception e) {
				if(constProperty.ALARM_ON.equals(switchFlag.trim())){
					root = new RootData("false","报警开关打开失败","",ErrorCodeDef.MYSQL_ERROR);
				}else{
					root = new RootData("false","报警开关关闭失败","",ErrorCodeDef.MYSQL_ERROR);
				}
				logger.error(e.getMessage(), e);
			}
		}else{
			root = new RootData("false","报警开关参数出入错误","",ErrorCodeDef.ALARM_SWITCH);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 报警开关查询
	 * @author Hellon(刘海龙) 
	 * @return
	 */
	public String SwitchQuery(){
		
		BizAccSession bas = getBizAccSession();
		if(bas == null){
			root = new RootData("true","用户未登录或登陆超时","",ErrorCodeDef.LOGIN_ERROR);
			return SUCCESS;
		}
		
		String deviceGuid = getRequest().getParameter("deviceGuid");//设备号
		
		String userId = bas.getUsername();
		
		if(Tools.strIsNull(deviceGuid)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		try {
			TbDeviceIpc  tdi = service.SwitchQuery(deviceGuid, userId);
			if(tdi == null){
				root = new RootData("false","用户没有绑定该设备","",ErrorCodeDef.DEVICE_USER_NOTHAVE);
				return SUCCESS;
			}else{
				Map<String,String> map = new HashMap<String,String>();
				map.put("deviceGuid", tdi.getDeviceGuid());
				map.put("alarmSwitch", tdi.getAlarmSwitch()+"");
				root = new RootData("true","设备报警开关查询成功",map,ErrorCodeDef.DO_SUCCESS);
				return SUCCESS;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			root = new RootData("false",e.getMessage(),"",ErrorCodeDef.MYSQL_ERROR);
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
	}
	
	
	public RootData getRoot() {
		return root;
	}

	public void setRoot(RootData root) {
		this.root = root;
	}
	
}
