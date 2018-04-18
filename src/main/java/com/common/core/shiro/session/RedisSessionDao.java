package com.common.core.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 重写 SessionDao 实现 Session 的管理
 */
@Component
public class RedisSessionDao extends EnterpriseCacheSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    // session key 前缀
    private String sessionPrefix = "shiro-session:";
    // session 在 redis 过期时间是30分钟(1800秒)
    private int sessionExpireTime = 30;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 创建 session 保存到 redis 缓存
     *
     * @param session
     * @return
     */
    @Override
    protected Serializable doCreate(Session session) {
        System.out.println("---------------------- RedisSessionDao doCreate() ----------------------");

        Serializable sessionId = super.doCreate(session);
        logger.debug("创建session:{}", session.getId());
//        redisTemplate.opsForValue().set(sessionPrefix + sessionId, ObjectUtils.serialize(session));
        redisTemplate.opsForValue().set(sessionPrefix + sessionId, session);
        return sessionId;
    }

    /**
     * 获取 session
     *
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        System.out.println("---------------------- RedisSessionDao doReadSession() ----------------------");

        logger.debug("获取session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (null == session) {

            // FIXME 从 redis 中再次获取 session 会报错

            System.out.println(redisTemplate.opsForValue().get(sessionPrefix + sessionId));
            session = (Session) redisTemplate.opsForValue().get(sessionPrefix + sessionId);

        }
        return session;
    }

    /**
     * 更新 session 的最后一次访问时间
     *
     * @param session
     */
    @Override
    protected void doUpdate(Session session) {
        System.out.println("---------------------- RedisSessionDao doUpdate() ----------------------");


        super.doUpdate(session);
        logger.debug("获取session:{}", session.getId());
        String key = sessionPrefix + session.getId();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        // 设置过期时间(秒)
        redisTemplate.expire(key, sessionExpireTime, TimeUnit.SECONDS);

    }

    /**
     * 删除 session
     *
     * @param session
     */
    @Override
    protected void doDelete(Session session) {
        System.out.println("---------------------- RedisSessionDao doDelete() ----------------------");

        logger.debug("删除session:{}", session.getId());
        super.doDelete(session);
        redisTemplate.delete(sessionPrefix + session.getId().toString());
    }
}
