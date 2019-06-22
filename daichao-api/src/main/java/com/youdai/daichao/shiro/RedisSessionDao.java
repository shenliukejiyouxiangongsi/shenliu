package com.youdai.daichao.shiro;


import com.youdai.daichao.common.redis.RedisCache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by cww on 2017/2/26.
 */
@Component(value = "redisSessionDao")
public class RedisSessionDao extends AbstractSessionDAO {
    @Resource
    private RedisCache redisCache;

    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void update(Session session) throws UnknownSessionException {
        log.debug("更新seesion,id=[{}]", session.getId().toString());
        try {
            redisCache.putCache(session.getId().toString(), session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Session session) {
        log.debug("删除seesion,id=[{}]", session.getId().toString());
        try {
            redisCache.delCache(session.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Collection<Session> getActiveSessions() {
        log.debug("获取存活的session");
        return Collections.emptySet();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        log.debug("创建seesion,id=[{}]", session.getId().toString());
        try {
            redisCache.putCache(sessionId.toString(), session);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {

        log.debug("获取seesion,id=[{}]", sessionId.toString());
        Session session = null;
        try {
            session = redisCache.getCache(sessionId.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return session;
    }
}