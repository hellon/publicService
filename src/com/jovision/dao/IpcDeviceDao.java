package com.jovision.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jovision.common.constProperty;
import com.jovision.domain.DeviceWhole;
import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.domain.TbUserDevice;
import com.jovision.redisDao.redisFactory;

/**
 * 
 * @author Hellon(刘海龙) Joker(张凯)
 *	ipc设备添加dao
 */
public class IpcDeviceDao extends ControlDAO {
	
	/**
	 * ipc设备添加
	 * @param tdb 基本设备信息
	 * @param tdi ipc专有设备信息
	 * @throws HibernateException
	 */
	public void addDevice(TbDeviceBasic tdb,TbDeviceIpc tdi) throws HibernateException{
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(tdb);
			session.save(tdi);
			tx.commit();
			session.flush();
		} catch (HibernateException re) {
			throw re;
		}finally{
			session.close();
		}
	}

	/**
	 * @param userID
	 * @param deviceGuidList
	 */
	public void batchDelete4bind(String userID, List deviceGuidList,String permission) {
		/*Session session = null;
		Transaction tx  = null;
		if (deviceGuidList != null && deviceGuidList.size() > 0) {  
            try {  
            	session = HibernateSessionFactory.getSession();
            	tx = session.beginTransaction();
                // 循环获取对象  
                for (int i = 0; i < deviceGuidList.size(); i++) {  
                    String hql = " delete from TbUserDevice tbud " +
                    			 " where tbud.userid= '" + userID + "'" + " and " +
                    			 " deviceGuid= '" + deviceGuidList.get(i)+"'";
                    Query query_tdb = session.createQuery(hql);
                    
                    redisFactory.del1SetValue(constProperty.USERID_PREFIX+userID+"_"+permission, );
                    redisFactory.put2Set(constProperty.DEVICEGUID_PREFIX+tud.getDeviceGuid(),tud.getUserid()+"_"+permission);
                    // 批插入的对象立即写入数据库并释放内存  
                    if (i % 10 == 0) {  
                        session.flush();  
                        session.clear();  
                    }  
                }  
                session.getTransaction().commit(); // 提交事物  
            } catch (Exception e) {  
                e.printStackTrace(); // 打印错误信息  
                tx.rollback(); // 出错将回滚事物  
                throw e;
            } finally {  
                session.close(); // 关闭Session  
            }  
        }  */
	}
	
	
	
}
