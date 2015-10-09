package com.jovision.dao;


import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.jovision.Interface.Rollback;
import com.jovision.common.constProperty;
import com.jovision.domain.DeviceWhole;
import com.jovision.domain.TbDeviceBasic;
import com.jovision.domain.TbDeviceIpc;
import com.jovision.domain.TbDeviceUser;
import com.jovision.domain.TbUserDevice;
import com.jovision.redisDao.redisFactory;
import com.jovision.util.SerializeUtil;
import com.sun.xml.internal.bind.v2.model.core.ID;

public class ControlDAO {

	/**
	 * 添加
	 * 
	 * @param obj
	 */
	public void save(Object obj) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(obj);
			tx.commit();
			session.flush();
			

		} catch (HibernateException re) {
			throw re;
		}finally{
			session.close();
		}
	}
	
	/**
	 * 插入数据
	 * 
	 * @param obj
	 * @return
	 */
	public boolean insert(Object obj) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(obj);
			tx.commit();
			session.flush();
			session.close();

		} catch (HibernateException re) {
			session.close();
			System.out.println("错误："+re.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 删除
	 * 
	 * @param obj
	 */
	public void delete(Object obj) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.delete(obj);
			tx.commit();
			session.flush();
		} catch (HibernateException re) {
			throw re;
		} finally{
			session.close();
		}
	}

	/**
	 * 修改
	 * 
	 * @param obj
	 */
	public void update(Object obj) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.merge(obj);
			tx.commit();
			session.flush();
		} catch (HibernateException re) {
			throw re;
		}finally{
			session.close();
		}
	}

	/**
	 * 查询
	 * 
	 * @param obj
	 * @param id
	 * @return
	 */
	public Object getObjById(Object obj, long id) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			Object object=session.get(obj.getClass(), id);
			tx.commit();
			session.flush();
			session.close();
			return object;

		} catch (HibernateException re) {
			session.close();
			return null;
		}
	}

	 /**
	  * 查询
	  * 
	  * @param obj
	  * @param id
	  * @return
	  */
	public Object getObjById(Object obj, int id) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			Object object=session.get(obj.getClass(), id);
			tx.commit();
			session.flush();
			session.close();
			return object;
		} catch (HibernateException re) {
			session.close();
			return null;
		}
	}
	
	
	
	 /**
	  * 查询
	  * 
	  * @param obj
	  * @param id
	  * @return
	  */
	public Object getObjById(Object obj, String id) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			Object object=session.get(obj.getClass(), id);
			tx.commit();
			session.flush();
			session.close();
			return object;
		} catch (HibernateException re) {
			session.close();
			return null;
		}
	}

	/**
	 * 对象查询
	 * 
	 * @param obj
	 * @return
	 */
	public Object getObjByObj(Object obj){
		
		Session session = HibernateSessionFactory.getSession();
		try{
			Criteria criteria = session.createCriteria(obj.getClass());
			criteria.add(Example.create(obj));
			obj= (Object) criteria.list().get(0);
			session.close();
			return obj;
			
		}catch(Exception e){
			session.close();
			return null;
		}
	}
	
	/**
	 * 对象查询
	 * 
	 * @param obj
	 * @return
	 */
	public List getListByObj(Object obj, int pagesize, int pagenum){
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;
		
		try{
			Criteria criteria = session.createCriteria(obj.getClass());
			criteria.add(Example.create(obj));
			criteria.setFirstResult(pagesize * pagenum);
			criteria.setMaxResults(pagesize);
			L=criteria.list();
			
			tx.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			session.close();
			return null;
		}
		return L;
	}
	
	/**
	 * 查询
	 * 
	 * @param hql
	 * @param pagesize
	 * @param pagenum
	 * @return
	 */
	public List getList(String hql, int pagesize, int pagenum) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(pagesize * pagenum);
			query.setMaxResults(pagesize);
			L = query.list();

			tx.commit();
			session.flush();
		} catch (HibernateException re) {
			throw re;
		}finally{
			session.close();
		}
		return L;
	}

	
	/**
	 * 查询
	 * 
	 * @param hql
	 * @param pagesize
	 * @param pagenum
	 * @return
	 */
	public List getListByLimit(String hql, int position, int limit) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(position);
			query.setMaxResults(limit);
			L = query.list();

			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L;
	}
	
	/**
	 * 查询
	 * 
	 * @param hql
	 * @param pagesize
	 * @param pagenum
	 * @param offset
	 * @return
	 */
	public List getList(String hql, int pagesize, int pagenum,int offset) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			query.setFirstResult(offset+pagesize * pagenum);
			query.setMaxResults(pagesize);
			L = query.list();

			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L;
	}
	
	
	/**
	 * 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List getList(String hql) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			L = query.list();

			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L;
	}

	/**
	 * 执行hql语句，关联的不行
	 * 
	 * @param hql
	 */
	public void executeHql(String hql) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			Query q = session.createQuery(hql);
			q.executeUpdate();
			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
	}

	/**
	 * 查询数量
	 * 
	 * @param hql
	 * @return
	 */
	public String getSize(String hql) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;
		
		try {
			Query query = session.createQuery(hql);
			L = query.list();
			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L.get(0) + "";

	}

	/**
	 * 查询数量，纯sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public String getNumber(String sql) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;
		
		try {
			SQLQuery query = session.createSQLQuery(sql);
			L = query.list();
			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			System.out.println(re.getMessage());
			throw re;
		}
		return L.get(0) + "";
	}
	
	
	/**
	 * 查询数量
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	public String getSize(String hql ,Map<String, Object> paramlist) {
		
		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;
		
		try {
			Query query = session.createQuery(hql);
			query.setProperties(paramlist);
			L = query.list();
			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L.get(0) + "";

	}
	
	/** 
	 * 查询
	 * @param hql
	 * @param pagesize
	 * @param pagenum
	 * @param paramlist
	 * @return
	 */
	 
	public List getListAndParms(String hql, int pagesize, int pagenum,Map<String, Object> paramlist) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			query.setProperties(paramlist);
			query.setFirstResult(pagesize * pagenum);
			query.setMaxResults(pagesize); 
			L = query.list();

			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L;
	}
	/**
	 * 查询
	 * @param hql 
	 * @param paramlist
	 * @return
	 */
	public List getListAndParms(String hql,Map<String, Object> paramlist) {

		Session session = HibernateSessionFactory.getSession();
		Transaction tx = session.beginTransaction();
		List L = null;

		try {
			Query query = session.createQuery(hql);
			query.setProperties(paramlist);
			L = query.list();

			tx.commit();
			session.flush();
			session.close();
		} catch (HibernateException re) {
			session.close();
			throw re;
		}
		return L;
	}
	
	/**
	 * 返回连接以便生成控制
	 * 
	 * @return
	 */
	public Connection getConn() {
		Session session = HibernateSessionFactory.getSession();
		return session.connection();
	}

	
	/**
	 * 对象批量添加
	 * @param <T>
	 * @param list
	 * @throws Exception 
	 */
	public <T> void  batchSave(List<T> list,Rollback... rollback) throws Exception {
		Session session = null;
		Transaction tx  = null;
		if (list != null && list.size() > 0) {  
            try {  
            	session = HibernateSessionFactory.getSession();
            	tx = session.beginTransaction();
                // 循环获取对象  
                for (int i = 0; i < list.size(); i++) {  
                    session.save(list.get(i)); // 保存对象  
                    
                    for(Rollback rollbackON:rollback)
                    {
                    	rollbackON.deviceInfosave2redis();
                    }
                    
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
        }  
	}

	
	public <T> void  batchDel(List<T> list,String deviceGuid) throws Exception {
		Session session = null;
		Transaction tx  = null;
		if (list != null && list.size() > 0) {  
            try {  
            	session = HibernateSessionFactory.getSession();
            	tx = session.beginTransaction();
                // 循环获取对象  
                for (int i = 0; i < list.size(); i++) 
                {  
                   //删除
                	String hql_tud = " delete from TbUserDevice tbud " +
					 			     " where deviceGuid= '" + deviceGuid + "' " +
					 			     " and  userId='" +list.get(i) +"'";
                	
                	String hql_tdu = " delete from TbDeviceUser tbdu " +
		 			                 " where deviceGuid= '" + deviceGuid + "' " +
		 			                 " and  userId='" +list.get(i) +"'";
                	
                	Query q_tud = session.createQuery(hql_tud);
                	Query q_tdu = session.createQuery(hql_tdu);
                	
                	q_tud.executeUpdate();
                	q_tdu.executeUpdate();
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
        }  
	}
	
	public <T> void  batchSave4Bind(List<T> list,DeviceWhole dw,String permission,boolean isDeviceExist) throws Exception {
		Session session = null;
		Transaction tx  = null;
		if (list != null && list.size() > 0) {  
            try {  
            	session = HibernateSessionFactory.getSession();
            	tx = session.beginTransaction();
            	if(constProperty.BIND.equals(permission))
            	{//1.绑定设备
            		 TbUserDevice tud =(TbUserDevice)list.get(0);
            		 session.save(list.get(0));
            		 if(!isDeviceExist)
            		 {//设备不存在，插入数据库
            			 session.save(dw.getTdb());
            			 session.save(dw.getTdi());
            			 SerializeUtil.setObject(0,constProperty.DEVICE+dw.getTdb().getDeviceGuid(),dw );
            		 }
                     redisFactory.put2Set(0,constProperty.USERID_PREFIX+tud.getUserid()+"_"+permission, dw.getTdb().getDeviceGuid());
                     redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+tud.getDeviceGuid(),tud.getUserid()+"_"+permission);
                     
                     session.flush();  
                     session.clear();  
            	}
            	else if(constProperty.SHARE.equals(permission))
            	{//2.分享设备
            		 for (int i = 0; i < list.size(); i++) 
            		 {  
                         session.save(list.get(i)); // 保存对象  
                         TbUserDevice tud =(TbUserDevice)list.get(i);
                       
                         redisFactory.put2Set(0,constProperty.USERID_PREFIX+tud.getUserid()+"_"+permission, dw.getTdb().getDeviceGuid());
                         redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+tud.getDeviceGuid(),tud.getUserid()+"_"+permission);
                         // 批插入的对象立即写入数据库并释放内存  
                         if (i % 10 == 0) 
                         {  
                             session.flush();  
                             session.clear();  
                         }  
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
        }  
	}
	
	public <T> void  batchSave4Share(List<TbUserDevice> activeTbudList,List<TbDeviceUser> activeTbduList,String deviceGuid,String permission) throws Exception {
		Session session = null;
		Transaction tx  = null;
		if (activeTbudList != null && activeTbudList.size() > 0) {  
            try {  
            	session = HibernateSessionFactory.getSession();
            	tx = session.beginTransaction();
            		 for (int i = 0; i < activeTbudList.size(); i++) 
            		 {  
                         session.save(activeTbudList.get(i)); // 保存tud
                         session.save(activeTbduList.get(i)); // 保存tdu
                         
                         TbUserDevice tud =(TbUserDevice)activeTbudList.get(i);
                       
                         redisFactory.put2Set(0,constProperty.USERID_PREFIX+tud.getUserid()+"_"+permission, deviceGuid);
                         redisFactory.put2Set(0,constProperty.DEVICEGUID_PREFIX+tud.getDeviceGuid(),tud.getUserid()+"_"+permission);
                         // 批插入的对象立即写入数据库并释放内存  
                         if (i % 10 == 0) 
                         {  
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
        }  
	}
	
	
	public Object getObject(String objectName,String deviceGuid,Session session)
	{
		String hql ="from " + objectName + " as "+objectName+"  where "+objectName+".deviceGuid = '" + deviceGuid + "'";
		Query query = session.createQuery(hql);
		List list = getList(hql);
		if(list!=null && list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String args[]){
		

	}
}
