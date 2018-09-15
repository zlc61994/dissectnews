package com.zlc.dissectnews.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class JedisUtils {


    static  JedisPool pool;
    static {

        pool = new JedisPool();

    }


//    public static String get(String key){
//
//        String v=null;
//        Jedis jedis=null;
//        try {
//            jedis = pool.getResource();
//
//            v = jedis.get(key);
//        }catch (Exception e){
//
//            System.out.println("e"+e.getCause());
//
//        }
//        finally {
//
//            if (jedis!=null){
//                jedis.close();
//            }
//
//        }
//
//        return  v;
//    }

    public static Long add(String key,String value){

        Jedis jedis=null;
        Long set=null;
        try {
            jedis = pool.getResource();

              set = jedis.sadd(key, value);
        }catch (Exception e){

            System.out.println("e"+e.getCause());

        }
        finally {

            if (jedis!=null){
                jedis.close();
            }

        }

        return  set;
    }

    public static Long remove(String key,String value){
        Jedis jedis = null;
        Long count = null;
        try {
            jedis = pool.getResource();

            count  = jedis.srem(key,value);
        }catch (Exception e){

            System.out.println("e"+e.getCause());

        }
        finally {

            if (jedis!=null){
                jedis.close();
            }

        }

        return  count;
    }

    public static Long Count(String key){
        Jedis jedis = null;
        Long count = null;
        try {
            jedis = pool.getResource();

           count  = jedis.scard(key);
        }catch (Exception e){

            System.out.println("e"+e.getCause());

        }
        finally {

            if (jedis!=null){
                jedis.close();
            }

        }

        return  count;
    }
}
