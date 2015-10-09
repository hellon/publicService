/**
 * 
 */
package com.jovision.other.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jovision.common.ErrorCodeDef;
import com.jovision.common.constProperty;
import com.jovision.controller.BaseController;
import com.jovision.dao.ControlDAO;
import com.jovision.domain.TbAdvertisementVersion;
import com.jovision.domain.TbAppVersion;
import com.jovision.domain.TbDeviceVersion;
import com.jovision.domain.TbWeburl;
import com.jovision.http.modelBean.RootData;
import com.jovision.redisDao.redisFactory;
import com.jovision.util.SerializeUtil;
import com.jovision.utils.Tools;

/**
 * @Title: OtherController.java 
 * @Package com.jovision.other.controller
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-22 下午04:37:38
 */

public class OtherController extends BaseController {

	private static final long serialVersionUID = 3677355546933818872L;
	private ControlDAO cdao = new ControlDAO();
	private RootData root = new RootData();
	
	/**
	 * @Title: 获取webUrl
	 * @Package com.jovision.other.controller
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-22 下午04:41:42
	 * @return
	 * @throws Exception 
	 */
	public String WebUrl() throws Exception
	{
		//先从redis获取
		List list_redis4return = new ArrayList();
		String language = getRequest().getParameter("language");
		language = Tools.strIsNull(language)?"ch":language;
		try {
			Set<String>  urlSet = redisFactory.getSet(1,constProperty.WEBURL_PREFIX + "_" + language);
			if(urlSet.size()>0){
				
				Iterator<String> it = urlSet.iterator();
				while(it.hasNext()){
					String type = it.next();
					TbWeburl weburl = (TbWeburl) SerializeUtil.getObject(1,constProperty.WEBURL_TYPE_PREFIX+type);
					if(weburl!= null){
						list_redis4return.add(bean2map(weburl));
					}
				}
				root = new RootData("true","恭喜您，获取webUrl成功！",list_redis4return,ErrorCodeDef.DO_SUCCESS);
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("WebUrl方法使用redis数据库操作失败", e);
		}
				
				
		//之获取一个
		String type = getRequest().getParameter("type");
		//select new map(webURL.name as webName,webURL.url as webUrl)
		String hql = " from TbWeburl as webURL where language= '"+ language +"'" ;
		try
		{
			List<TbWeburl> list_weburl = cdao.getList(hql);
			List list4return = new ArrayList();
			for(int i=0;i<list_weburl.size();i++){
				TbWeburl weburl =  list_weburl.get(i);
				list4return.add(bean2map(weburl));
				redisFactory.put2Set(1,constProperty.WEBURL_PREFIX + "_" + language, weburl.getType()+ "_" + language);
				SerializeUtil.setObject(1,constProperty.WEBURL_TYPE_PREFIX+weburl.getType()+ "_" + language,weburl );
			}
			
			
			root = new RootData("true","恭喜您，获取webUrl成功！",list4return,ErrorCodeDef.DO_SUCCESS);
			return SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			root = new RootData("false","数据库异常！","",ErrorCodeDef.MYSQL_ERROR);
			return SUCCESS;
		}
		
	}
	/**
	 * @Title: bean转换成map
	 * @Package com.jovision.other.controller
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-23 上午08:58:54
	 * @param dw
	 * @return
	 */
	private Map<String,String> bean2map(TbWeburl weburl) {
		Map<String,String> returnMap = new HashMap<String, String>();
		returnMap.put("webName", weburl.getName());
		returnMap.put("webUrl", weburl.getUrl());
		return returnMap;
	}
	/**
	 * 获取广告地址
	 * @author Hellon(刘海龙) 
	 * @return
	 */
	public String AdsURL()
	{
		String version = getRequest().getParameter("version");
		
		if(Tools.strIsNull(version)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		try {
			//从redis数据库中取数据
			Object obj = SerializeUtil.getObject(1,"AdsURL_"+TbAdvertisementVersion.class.getSimpleName());
			if(obj != null){
				TbAdvertisementVersion tav = (TbAdvertisementVersion) obj;
				int versionInt = Integer.parseInt(version);
				Map<String,String> map = new HashMap<String, String>();
				map.put("url", tav.getUrl());
				map.put("version", tav.getVersionValue()+"");
				if(tav.getVersionValue()>versionInt){
					map.put("refresh", "true");
					root = new RootData("true","最新广告图片获取成功",map,ErrorCodeDef.DO_SUCCESS);
				}else{
					map.put("refresh", "false");
					root = new RootData("true","广告图片未更新",map,ErrorCodeDef.OTHER_VERSION_NOTUPDATED);
				}
				return SUCCESS;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			logger.error(e1,e1);
		}
		
		String hql = "from TbAdvertisementVersion tav order by tav.versionValue desc";
		try {
			List list = cdao.getList(hql, 1, 0);
			if(list.size() == 1){
				TbAdvertisementVersion tav = (TbAdvertisementVersion) list.get(0);
				
				//存储到redis数据库中
				SerializeUtil.setObject(1,"AdsURL_"+TbAdvertisementVersion.class.getSimpleName(), tav);
				
				int versionInt = Integer.parseInt(version);
				Map<String,String> map = new HashMap<String, String>();
				map.put("url", tav.getUrl());
				map.put("version", tav.getVersionValue()+"");
				if(tav.getVersionValue()>versionInt){
					map.put("refresh", "true");
					root = new RootData("true","最新广告图片获取成功",map,ErrorCodeDef.DO_SUCCESS);
				}else{
					map.put("refresh", "false");
					root = new RootData("true","广告图片未更新",map,ErrorCodeDef.DO_SUCCESS);
				}
			}
		}catch (NumberFormatException e) {
			logger.error("字符转换为数字错误,"+e.getMessage(), e);
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
		}catch (Exception e) {
			logger.error("数据库异常,"+e.getMessage(), e);
			root = new RootData("false","数据库出现异常","",ErrorCodeDef.MYSQL_ERROR);
		}
		return SUCCESS;
	}
	
	
	
	public String APPUpdate()
	{
		String version = getRequest().getParameter("version");
		String appKey = getRequest().getParameter("appKey");
		String language = getRequest().getParameter("language");
		language = Tools.strIsNull(language)?"ch":language;
		
		if(Tools.strArrIsNull(version,appKey,language)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		try {
			//从redis数据库中取数据
			Object obj = SerializeUtil.getObject(1,"APPUpdate_" + TbAppVersion.class.getSimpleName() + "_" + language);
			if(obj != null){
				TbAppVersion tav = (TbAppVersion) obj;
				appOutMap(version, tav);
				return SUCCESS;
			}
		} catch (Exception e1) {
			logger.error(e1,e1);
		}
		
		String hql = " from TbAppVersion tav where tav.appKey = "+appKey+"" +
					 " and language = '" + language + "'" +
					 " order by tav.versionValue desc";
		try {
			List list = cdao.getList(hql, 1, 0);
			if(list.size() == 1){
				TbAppVersion tav = (TbAppVersion) list.get(0);
				
				//存储到redis数据库中
				SerializeUtil.setObject(1,"APPUpdate_"+TbAppVersion.class.getSimpleName() + "_" + language, tav);
				appOutMap(version, tav);
			}
			else
			{
				root = new RootData("false","未找到该APP的升级信息","",ErrorCodeDef.RESULT_IS_NULL);
			}
		}catch (NumberFormatException e) {
			logger.error("字符转换为数字错误,"+e.getMessage(), e);
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
		}catch (Exception e) {
			logger.error("数据库异常,"+e.getMessage(), e);
			root = new RootData("false","数据库出现异常","",ErrorCodeDef.MYSQL_ERROR);
		}
		return SUCCESS;
	}
	
	/**
	 * 设置app要展示的值
	 * @author Hellon(刘海龙) 
	 * @param version
	 * @param tav
	 */
	private void appOutMap(String version, TbAppVersion tav) {
		int versionInt = Integer.parseInt(version);
		Map<String,String> map = new HashMap<String, String>();
		map.put("url", tav.getUrl());
		map.put("versionInt", tav.getVersionValue()+"");
		map.put("version", tav.getVersion());
		map.put("log", tav.getUpdateLog());
		map.put("force", tav.getForceUpdate()+"");
		if(tav.getVersionValue()>versionInt){
			map.put("refresh", "true");
			root = new RootData("true","最新App获取成功",map,ErrorCodeDef.DO_SUCCESS);
		}else if(tav.getVersionValue() == versionInt){
			map.put("refresh", "false");
			root = new RootData("true","App未更新",map,ErrorCodeDef.DO_SUCCESS);
		}else{
			map.put("refresh", "false");
			root = new RootData("true","传入的版本号过高",map,ErrorCodeDef.DO_SUCCESS);
		}
	}
	


	/**
	 * @Title: 设备一键升级
	 * @Package com.jovision.other.controller
	 * @author Joker(张凯)
	 * @Description: TODO() 
	 * @date 2015-9-23 上午10:50:17
	 * @return
	 */
	public String DeviceUpdate()
	{
		String version = getRequest().getParameter("version");
		String deviceGuid = getRequest().getParameter("deviceGuid");
		String language = getRequest().getParameter("language");
		language = Tools.strIsNull(language)?"ch":language;
		
		if(Tools.strIsNull(version)||Tools.strIsNull(deviceGuid)){
			root = new RootData("false","传入参数不正确","",ErrorCodeDef.PARAM_ERROR);
			return SUCCESS;
		}
		
		try {
			//从redis数据库中取数据
			Object obj = SerializeUtil.getObject(1,"DeviceUpdate_" + TbAppVersion.class.getSimpleName() + "_" + language);
			if(obj != null){
				TbDeviceVersion tdv = (TbDeviceVersion) obj;
				Map<String,String> map = new HashMap<String, String>();
				map.put("versionInt", tdv.getVersionValue()+"");
				map.put("version", tdv.getVersion());
				map.put("log", tdv.getUpdateLog());
				map.put("refresh", "true");
				root = new RootData("true","最新【设备】获取成功",map,ErrorCodeDef.DO_SUCCESS);
				return SUCCESS;
			}
		} catch (Exception e1) {
			logger.error(e1,e1);
		}
		
		//TbDeviceVersion
		String hql = " from TbDeviceVersion tdv where tdv.deviceGuid = '"+deviceGuid+
					 "' and language = '" + language +             
					 "' order by tdv.versionValue desc";
		try {
			List list = cdao.getList(hql, 1, 0);
			if(list.size() == 1){
				TbDeviceVersion tdv = (TbDeviceVersion) list.get(0);
				int versionInt = Integer.parseInt(version);
				Map<String,String> map = new HashMap<String, String>();
				map.put("versionInt", tdv.getVersionValue()+"");
				map.put("version", tdv.getVersion());
				map.put("log", tdv.getUpdateLog());
				if(tdv.getVersionValue()>versionInt){
					map.put("refresh", "true");
					root = new RootData("true","最新【设备】获取成功",map,ErrorCodeDef.DO_SUCCESS);
					//存储到redis数据库中
					SerializeUtil.setObject(1,"DeviceUpdate_"+TbAppVersion.class.getSimpleName() + "_" + language, tdv);
					
				}else if(tdv.getVersionValue() == versionInt){
					map.put("refresh", "false");
					root = new RootData("true","【设备】版本未更新",null,ErrorCodeDef.DO_SUCCESS);
				}else{
					map.put("refresh", "false");
					root = new RootData("true","【设备】传入的版本号过高",null,ErrorCodeDef.DO_SUCCESS);
				}
			}
			else
			{
				root = new RootData("false","未找到该设备【"+deviceGuid+"】的升级信息","",ErrorCodeDef.RESULT_IS_NULL);
			}
		}catch (NumberFormatException e) {
			logger.error("字符转换为数字错误,"+e.getMessage(), e);
			root = new RootData("false","传入版本对应数值不正确","",ErrorCodeDef.PARAM_ERROR);
		}catch (Exception e) {
			logger.error("数据库异常,"+e.getMessage(), e);
			root = new RootData("false","数据库出现异常","",ErrorCodeDef.MYSQL_ERROR);
		}
		return SUCCESS;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(RootData root) {
		this.root = root;
	}

	/**
	 * @return the root
	 */
	public RootData getRoot() {
		return root;
	}
}
