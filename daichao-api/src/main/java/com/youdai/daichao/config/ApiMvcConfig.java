package com.youdai.daichao.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Anthor: zhankui
 * @Date: 2019/3/14
 * @Description
 */
@Configuration
public class ApiMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${server.port}")
    int serverPort;
    @Value("${server.https.port}")
    int serverHttpsPort;

    @Bean
    public ApiInterceptor   getApiInterceptor(){

        return new ApiInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         /*addPathPatterns 用于添加拦截规则
         excludePathPatterns 用户排除拦截*/
        registry.addInterceptor(getApiInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/api/userRecord/put");
    }

//
//    @Bean
//    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector){
//        TomcatServletWebServerFactory tomcat=new TomcatServletWebServerFactory(){
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint=new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection=new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(connector);
//        return tomcat;
//    }
//
//    @Bean
//    public Connector initiateHttpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        //需要重定向的http端口
//        connector.setPort(serverHttpsPort);
//        connector.setSecure(false);
//        //设置重定向到https端口
//        connector.setRedirectPort(serverPort);
//        return connector;
//    }

}
