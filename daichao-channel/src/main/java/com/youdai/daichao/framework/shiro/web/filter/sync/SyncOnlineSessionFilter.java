package com.youdai.daichao.framework.shiro.web.filter.sync;

import com.youdai.daichao.common.constant.ShiroConstants;
import com.youdai.daichao.framework.shiro.session.SessionDao;
import com.youdai.daichao.project.monitor.online.domain.OnlineSession;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 同步Session数据到Db
 * 
 * @author user-xmp
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter
{
    @Autowired
    private SessionDao sessionDao;

    /**
     * 同步会话数据到DB 一次请求最多同步一次 防止过多处理 需要放到Shiro过滤器之前
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {
        OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
        // 如果session stop了 也不同步
        // session停止时间，如果stopTimestamp不为null，则代表已停止
        if (session != null && session.getUserId() != null && session.getStopTimestamp() == null)
        {
            sessionDao.syncToDb(session);
        }
        return true;
    }
}