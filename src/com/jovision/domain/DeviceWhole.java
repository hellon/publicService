/**
 * 
 */
package com.jovision.domain;

import com.sun.corba.se.spi.ior.iiop.JavaCodebaseComponent;

/**
 * @Title: DeviceWhole.java 
 * @Package com.jovision.domain
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-10 下午04:50:56
 */

public class DeviceWhole implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8238858259128058873L;
	private TbDeviceBasic tdb;
	private TbDeviceIpc tdi;
	/**
	 * @param tdb
	 * @param tdi
	 */
	public DeviceWhole() {}
	
	public DeviceWhole(TbDeviceBasic tdb, TbDeviceIpc tdi) {
		this.tdb = tdb;
		this.tdi = tdi;
	}
	/**
	 * @return the tdb
	 */
	public TbDeviceBasic getTdb() {
		return tdb;
	}
	/**
	 * @param tdb the tdb to set
	 */
	public void setTdb(TbDeviceBasic tdb) {
		this.tdb = tdb;
	}
	/**
	 * @return the tdi
	 */
	public TbDeviceIpc getTdi() {
		return tdi;
	}
	/**
	 * @param tdi the tdi to set
	 */
	public void setTdi(TbDeviceIpc tdi) {
		this.tdi = tdi;
	}
	
	@Override
	public String toString()
	{
		/**
		 * {"deviceGuid":"zktest1",
		     "deviceUsername":null,
		     "deviceName":null,
		     "cloudSwitch":1,
		     "deviceType":2,
		     "alarmSwitch":1,
		     "deviceSubType":1,
		     "deviceVersion":null,
		     "devicePassword":null
		     }
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("deviceGuid:");
		sb.append(getTdb().getDeviceGuid()).append(",");
		sb.append("deviceUsername:");
		sb.append(getTdb().getDeviceUsername()).append(",");
		sb.append("deviceName:");
		sb.append(getTdb().getDeviceName()).append(",");
		sb.append("deviceType:");
		sb.append(getTdb().getDeviceType()).append(",");
		sb.append("devicePassword:");
		sb.append(getTdb().getDevicePassword()).append(",");
		
		sb.append("cloudSwitch:");
		sb.append(getTdi().getCloudSwitch()).append(",");
		sb.append("alarmSwitch:");
		sb.append(getTdi().getAlarmSwitch()).append(",");
		sb.append("deviceSubType:");
		sb.append(getTdi().getDeviceSubType()).append(",");
		sb.append("deviceVersion:");
		sb.append(getTdi().getDeviceVersion());
		sb.append("}");
		
		return sb.toString();
	}
}
