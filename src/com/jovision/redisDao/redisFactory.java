package com.jovision.redisDao;


import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.jovision.Exception.RedisException;
import com.jovision.system.ConfigBean;
/**
 * 
 * @Title: redisFactory.java 
 * @Package com.jovision.redisDao
 * @author Joker(张凯)
 * @Description: TODO() 
 * @date 2015-9-30 上午11:49:09
 */
public class redisFactory {
	private static Logger logger = Logger.getLogger(redisFactory.class); 
	
	public static ConfigBean configBean;
	public static String redisIP ;
	public static String redisPort ;
	public static JedisPool pool;
	static
	{
		//连接redis，连接失败时抛异常
		try 
		{
			configBean = ConfigBean.getInstace();
			redisIP = configBean.getRedisIP();
			redisPort = configBean.getRedisPort();
			if (pool == null)
			{
	            JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxActive(500);
	            config.setMaxIdle(5);
	            config.setMaxWait(1000 * 100);
	            config.setTestOnBorrow(true);
	            pool = new JedisPool(config, redisIP, Integer.parseInt(redisPort));
		     }
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("redis配置异常", e);
			//throw new RedisException("redis异常！");
		}
		
	}
	
	public static void main(String[] args) {
		/*System.out.println(redisIP);
		setSingleData("123","123");
		System.out.println(getSingleData("123"));*/
		put2Set(0,"test11", "123","456","789");
		//System.out.println(getOneSetData("test","123"));
	}
	
    /**
     * 返还到连接池
     * 
     * @param pool 
     * @param redis
     */
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    
    /**
     * 将数据存到集合
     * 
     * @param key
     * @return
     */
    public static boolean put2Set(int index,String key , String... value ){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.sadd(key, value);
            return true;
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
        
    }
    
    /**
     * 获取集合数据
     * 
     * @param key
     * @return
     * @throws RedisException 
     */
    public static Set<String> getSet(int index,String key) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            return jedis.smembers(key);
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            //e.printStackTrace();
            logger.error("getSet异常", e);
            throw new RedisException("redis异常！"+e.getMessage());
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
    }
    
    /**
     * @Title: hget
     * @Package com.jovision.redisDao
     * @author Joker(张凯)
     * @Description: TODO() 
     * @date 2015-9-30 上午11:44:58
     * @param index
     * @param key
     * @param field
     * @return
     * @throws RedisException
     */
    public static String hget(int index,String key,String field) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            return jedis.hget(key, field);
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            //e.printStackTrace();
            logger.error("getSet异常", e);
            throw new RedisException("redis异常！"+e.getMessage());
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
    }
    
    /**
     * @Title: hset 
     * @Package com.jovision.redisDao
     * @author Joker(张凯)
     * @Description: TODO() 
     * @date 2015-9-30 上午11:45:06
     * @param index
     * @param key
     * @param field
     * @param value
     * @throws RedisException
     */
    public static void hset(int index,String key,String field,String value) throws RedisException{
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.hset(key, field,value);
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            //e.printStackTrace();
            logger.error("getSet异常", e);
            throw new RedisException("redis异常！"+e.getMessage());
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 获取单个数据
     * 
     * @param key
     * @return
     */
    public static String getSingleData(int index,String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            return jedis.get(key);
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
		return null;
        
    }
    
    
    /**
     * 存入单个简单数据
     * 
     * @param key
     * @return
     */
    public static boolean setSingleData(int index,String key ,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
        	//失败就返回jedis
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
        	//释放jedis资源
            returnResource(pool, jedis);
        }
        
    }
    
    /**
     * 删除set中单个value
     * 
     * @param key
     * @return
     */
    public static boolean del1SetValue(int index,String key ,String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.srem(key, value);  
            return true;
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
            returnResource(pool, jedis);
        }
        
    }
    
    /**
     * 删除key对应整个set
     * 
     * @param key
     * @return
     */
    public static boolean del(int index ,String key){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.del(key);
            return true;
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            e.printStackTrace();
            return false;
        } finally {
            returnResource(pool, jedis);
        }
        
    }
    
    
    /**
     * 设置key失效时间 
     * @param key 
     * @param seconds
     * @throws Exception
     */
    public static void setTimeOut(int index,String key,int seconds) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.expire(key, seconds);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    public static byte[] getBytes(int index,byte[] key) throws Exception
    {
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            return jedis.get(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    public static void setBytes(int index,byte[] key,byte[] value) throws Exception
    {
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            jedis.set(key, value);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 
     * @author Hellon(刘海龙) 
     * @param key key值
     * @return 该key值存储的长度
     * @throws Exception
     */
    public static Long getLLength(int index,String key) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            Long len = jedis.llen(key);
            return len;
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 从list获取数据
     * @author Hellon(刘海龙) 
     * @param key 字节byte
     * @param start 查询的开始位置
     * @param end 查询的结束位置  -1 代表查询所有
     * @return 返回字节list列表
     * @throws Exception
     */
    public static List<byte[]>  lrange(int index,byte[] key,int start,int end) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            List<byte[]> list = jedis.lrange(key, start, end);
            return list;
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * @Title: 是否存在
     * @Package com.jovision.redisDao
     * @author Joker(张凯)
     * @Description: TODO() 
     * @date 2015-9-30 下午02:09:25
     * @param index
     * @param key
     * @return
     * @throws Exception
     */
    public static  boolean isExist(int index,String key) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            return jedis.exists(key);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 向list添加数据
     * @author Hellon(刘海龙) 
     * @param key
     * @param strings
     * @return
     * @throws Exception
     */
    public static  Long lpush(int index,byte[] key,byte[]... strings) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
            Long len = jedis.lpush(key, strings);
            return len;
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
    
    /**
     * 保留指定key 的值范围内的数据
     * @author Hellon(刘海龙) 
     * @param key 指定的key值
     * @param start 开始位置
     * @param end 结束位置
     * @throws Exception 
     */
    public static void ltrim(int index,byte[] key,int start,int end) throws Exception{
    	Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.select(index);
           jedis.ltrim(key, start, end);
        } catch (Exception e) {
            pool.returnBrokenResource(jedis);
            logger.error("redis数据库出现异常", e);
            throw e;
        } finally {
            returnResource(pool, jedis);
        }
    }
}
