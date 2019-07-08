package com.youdai.daichao.framework.shiro.service;


import com.youdai.daichao.common.constant.Constants;
import com.youdai.daichao.common.constant.ShiroConstants;
import com.youdai.daichao.common.constant.UserConstants;
import com.youdai.daichao.common.exception.user.*;
import com.youdai.daichao.common.utils.DateUtils;
import com.youdai.daichao.common.utils.MessageUtils;
import com.youdai.daichao.common.utils.ServletUtils;
import com.youdai.daichao.common.utils.security.ShiroUtils;
import com.youdai.daichao.framework.manager.AsyncManager;
import com.youdai.daichao.framework.manager.factory.AsyncFactory;
import com.youdai.daichao.project.system.user.domain.SysUser;
import com.youdai.daichao.project.system.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 登录校验方法
 * 
 * @author user-xmp
 */
@Component
public class LoginService
{
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    public SysUser login(String username, String password)
    {
        // 验证码校验
        if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA)))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }

        // 查询用户信息
        SysUser user = sysUserService.selectUserByLoginName(username);



        if (user == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.not.exists")));
            throw new UserNotExistsException();
        }

        passwordService.validate(user, password);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        return user;
    }

    private boolean maybeEmail(String username)
    {
        if (!username.matches(UserConstants.EMAIL_PATTERN))
        {
            return false;
        }
        return true;
    }

    private boolean maybeMobilePhoneNumber(String username)
    {
        if (!username.matches(UserConstants.MOBILE_PHONE_NUMBER_PATTERN))
        {
            return false;
        }
        return true;
    }


}
