package com.youdai.daichao.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.youdai.daichao.common.redis.RedisCache;
import com.youdai.daichao.common.vo.FailException;
import com.youdai.daichao.common.vo.JsonCodeEnum;
import com.youdai.daichao.domain.AppUser;
import com.youdai.daichao.mapper.AppUserMapper;
import com.youdai.daichao.service.IAppUserService;
import com.youdai.daichao.util.Md5;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhankui
 * @since 2019-03-12
 */
@Service
public class AppUserServiceImpl extends ServiceImpl<AppUserMapper, AppUser> implements IAppUserService {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    AppUserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    /**
     * 登录
     *
     * @param userName 账号
     * @param password 密码
     * @return JsonResp
     */
    public AppUser loginByPhone(String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        String md5Pass = Md5.md5Encode(password);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, md5Pass);

        try {
            //踢除用户
            this.kickOutUser(token);
        } catch (Exception e) {
            String message = e.getMessage();
            if (!message.contains("There is no session with id [")) {
                e.printStackTrace();
            }
        }
        try {
            subject.login(token);
            Session session = subject.getSession();
            Serializable sessionId = session.getId();
            EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
            wrapper.eq("a_uphone", userName);
            AppUser user = userMapper.selectList(wrapper).get(0);
            if (user != null) {
                if (user.getStatus() == 5) {
                    String msg = "因您的综合信用不足，暂不能提供服务!";
                    throw new FailException(msg);
                }
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            //前端不调用退出登录接口时候 当前传递过来的sessionId 还是存活的，所以shiro 默认不更新sessionid,
            // 所以不能调用后续的删除redis中的清除之前session的操作

            //注销此段代码所有登录都需要删除其他人的登录状态
            if (request.getHeader("token") != null && user.getToken() != null) {
                if (redisCache.haveCache(user.getToken()) == true) {
                    return user;
                }
            }
            String token1 = user.getToken();
            if (token1 != null && redisCache.haveCache(token1)) {
                redisCache.delCache(token1);
            }
            user.setToken(sessionId.toString());
            user.setStatus(1);
            userMapper.updateById(user);
            return user;
        } catch (FailException e) {
            throw new FailException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "用户名或密码错误!";
            throw new FailException(msg, e);
        }
    }

    /**
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public AppUser phoneCodeLogin(String phone, String code) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(phone,code);

        try {
            //踢除用户
            this.kickOutUser(token);
        } catch (Exception e) {
            String message = e.getMessage();
            if (!message.contains("There is no session with id [")) {
                e.printStackTrace();
            }
        }
        try {
            subject.login(token);
            Session session = subject.getSession();
            Serializable sessionId = session.getId();
            EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
            wrapper.eq("a_uphone", phone);
            AppUser user = userMapper.selectList(wrapper).get(0);
            if(user != null){
                if(user.getStatus()==5){
                    String msg = "因您的综合信用不足，暂不能提供服务!";
                    throw new FailException(msg);
                }
            }

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
            //前端不调用退出登录接口时候 当前传递过来的sessionId 还是存活的，所以shiro 默认不更新sessionid,
            // 所以不能调用后续的删除redis中的清除之前session的操作

            String token1 = user.getToken();
            if (token1 != null && redisCache.haveCache(token1)) {
                redisCache.delCache(token1);
            }
            user.setToken(sessionId.toString());
            userMapper.updateById(user);
            return user;
        }catch (FailException e){
            throw new FailException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "用户名或密码错误!";
            throw new FailException(msg,e);
        }
    }

    /**
     * 踢除用户
     * http://www.ithao123.cn/content-7174367.html
     */
    private void kickOutUser(UsernamePasswordToken token) {
        String loginName = token.getUsername();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (loginName.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
                //设置session立即失效，即将其踢出系统
                sessionDAO.delete(session);
                logout(null);
            }
        }
    }

    /**
     * 退出登录
     */
    public void logout(AppUser user) {
        Subject subject = SecurityUtils.getSubject();
        //用户主动退出登陆，清空token
        if (user != null) {
            user.setToken(null);
            userMapper.updateById(user);
        }
        if (subject.isAuthenticated()) {
            // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            subject.logout();
        }
    }

    @Override
    public AppUser findLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection collection = subject.getPrincipals();
        AppUser user = null;
        if (null != collection && !collection.isEmpty()) {//web 端
            String userName = (String) collection.iterator().next();
            EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
            wrapper.eq("a_uphone", userName);
            user = userMapper.selectList(wrapper).get(0);
            if (user == null) throw new FailException(JsonCodeEnum.OVERTIME.getMessage());
            if (user != null) {
                if (user.getStatus() == 2) throw new FailException(JsonCodeEnum.STOP.getMessage());
                if (user.getStatus() == 5) throw new FailException(JsonCodeEnum.REFUSE.getMessage());
                return user;
            }
        }
        throw new FailException(JsonCodeEnum.OVERTIME.getMessage());
    }


    @Override
    public AppUser selectLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection collection = subject.getPrincipals();
        AppUser user = null;
        if (null != collection && !collection.isEmpty()) {//web 端
            String userName = (String) collection.iterator().next();
            EntityWrapper<AppUser> wrapper = new EntityWrapper<>();
            wrapper.eq("a_uphone", userName);
            user = userMapper.selectList(wrapper).get(0);
            if (user == null) throw new FailException(JsonCodeEnum.OVERTIME.getMessage());
            if (user != null) {
                if (user.getStatus() == 2) throw new FailException(JsonCodeEnum.STOP.getMessage());
                if (user.getStatus() == 5) throw new FailException(JsonCodeEnum.REFUSE.getMessage());
                return user;
            }
        }else {
            return null;
        }
        return null;
    }




}
