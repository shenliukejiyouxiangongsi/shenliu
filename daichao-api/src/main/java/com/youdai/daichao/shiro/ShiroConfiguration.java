package com.youdai.daichao.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import com.youdai.daichao.config.ApiSignFilter;
import com.youdai.daichao.domain.AppUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by z on 2018/4/21.
 */
@Configuration
@ComponentScan("com.youdai.daichao.shiro")
public class ShiroConfiguration {
    @Autowired
    private PasswordRealm passwordRealm;
    @Autowired
    private RedisSessionDao redisSessionDao;


    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * <p>
     * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

//        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        // 未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        filtersMap.put("apiSignFilter",new ApiSignFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被拦截的链接 顺序判断
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        // 从数据库获取动态的权限
        // filterChainDefinitionMap.put("/add", "perms[权限添加]");
        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //logout这个拦截器是shiro已经实现好了的。
        // 从数据库获取
        /*List<SysPermissionInit> list = sysPermissionInitService.selectAll();

        for (SysPermissionInit sysPermissionInit : list) {
            filterChainDefinitionMap.put(sysPermissionInit.getUrl(),
                    sysPermissionInit.getPermissionInit());
        }*/
        filterChainDefinitionMap.put("/**", "apiSignFilter");

        shiroFilterFactoryBean
                .setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(passwordRealm);
        // 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
//         自定义session管理 使用redis
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDao);
        sessionManager.setGlobalSessionTimeout(-1000);
        securityManager.setSessionManager(sessionManager);
//        //注入记住我管理器;
//        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * cookie对象;
     *
     * @return
     */
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

//    /**
//     * cookie管理对象;记住我功能
//     *
//     * @return
//     */
//    public CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
//        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
//        return cookieRememberMeManager;
//    }

    class AuthRealm extends AuthorizingRealm {
        /**
         * 为当前登录的用户授予权限
         */
        @Override
        public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
            //String phone = (String)principals.getPrimaryPrincipal();
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
            return authorizationInfo;
        }

        /**
         * 验证当前登录的用户
         */
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

            String userName = (String) token.getPrincipal();

            /*第二种取得applicationContext的方式，使用Spring提供的工具类，传入应用上下文对象，就可以获取IOC容器*/
            ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ra.getRequest();
            ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            DruidDataSource druidDataSource = applicationContext.getBean(DruidDataSource.class);
            Connection connection = null;
            Statement stmt = null;
            try {
                connection = druidDataSource.getConnection();
                stmt = connection.createStatement();
                //准备sql
                String sql = "select * from user where phone = '" + userName + "' and status = 1";
                ResultSet rs = stmt.executeQuery(sql);
                List<AppUser> users = populate(rs, AppUser.class);
                if (users.size() == 0) {
                    sql = "select * from admin where user_name = '" + userName + "' and status = 1";
                    rs = stmt.executeQuery(sql);
                }
                if (users.size() == 1) {
                    AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userName, users.get(0).getPassword(), getName());
                    return authcInfo;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    stmt.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        private List populate(ResultSet rs, Class cc) {
            List list = new ArrayList();
            try {
                //结果集 中列的名称和类型的信息
                ResultSetMetaData rsm = rs.getMetaData();
                int colNumber = rsm.getColumnCount();
                Field[] fields = cc.getDeclaredFields();
                //遍历每条记录
                while (rs.next()) {
                    //实例化对象
                    Object obj = cc.newInstance();
                    //取出每一个字段进行赋值
                    for (int i = 1; i <= colNumber; i++) {
                        Object value = rs.getObject(i);
                        //匹配实体类中对应的属性
                        for (int j = 0; j < fields.length; j++) {
                            Field f = fields[j];
                            if (f.getName().equals(rsm.getColumnName(i))) {
                                boolean flag = f.isAccessible();
                                f.setAccessible(true);
                                f.set(obj, value);
                                f.setAccessible(flag);
                                break;
                            }
                        }

                    }
                    list.add(obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}
