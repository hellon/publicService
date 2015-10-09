/**
 * 
 */
package com.jovision.ip.controller;

import java.util.List;

import com.jovision.controller.BaseController;
import com.jovision.dao.ControlDAO;
import com.jovision.domain.IpTables;
import com.jovision.util.IP2Long;
import com.jovision.util.IPv4Util;

/**
 * @Title: IpController.java 
 * @Package com.jovision.ip.controller
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-28 下午04:39:56
 */

public class IpController extends BaseController{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2334178191234019929L;
	private String provider;
	private ControlDAO cdao = new ControlDAO();
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	/**
	 * 获取ip运营商地址
	 */
	public String dns()
	{
		String ip = getRequest().getRemoteHost();
		//ip="58.56.25.106";
		int value = IPv4Util.ipToInt(ip);
		IpTables ipTables =null;
		try
		{
			String hql = "from IpTables as ip where ip.value=" +
					     "( select max(ip1.value) " +
					     "  from IpTables as ip1 " +
					     "  where ip1.value < '"+value+"')";
			List list = cdao.getList(hql);
			if(list!=null && list.size()>0)
			{
				//存在该ip端
				ipTables = (IpTables)list.get(0);
			}
			else
			{
				return "foreign";
			}
			int a = value & IP2Long.getMask(ipTables.getMask());
			int b = IPv4Util.ipToInt(ipTables.getIp());
			if(a==b)
			{
				System.out.println("客户端ip：" + ip + "所属网络：" + ipTables.getProvider());
				logger.info("客户端ip：" + ip + "所属网络：" + ipTables.getProvider());
			}
			else
			{
				return "foreign";
			}
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		provider =ipTables.getProvider();
		return provider.trim();
	}

}
