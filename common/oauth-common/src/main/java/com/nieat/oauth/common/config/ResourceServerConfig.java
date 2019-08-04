package com.nieat.oauth.common.config;

import com.alibaba.fastjson.JSON;
import com.nieat.oauth.common.component.ClientAccessTokenConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @Author NieAt
 * @create 2019/6/21 0021 下午 16:16
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${oauth2.client.id:nieat}")
    private String clientId;
    @Value("${oauth2.client.secret:nieat}")
    private String clientSecret;
    @Value("${oauth2.client.ignore:''}")
    private String ignore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        http.headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers(ignore.split(",")).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(remoteTokenService())
                .authenticationEntryPoint((request, response, e) -> {
                    // AccessToken 校验失败异常处理
                    HttpStatus status = HttpStatus.UNAUTHORIZED;
                    HashMap<String, Object> body = new HashMap<>(3);
                    body.put("code", status.value());
                    body.put("error", "Fail Check Token!!");
                    body.put("error_message", "TOKEN校验失败!!");
                    response.setStatus(HttpStatus.OK.value());
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter printWriter = response.getWriter();
                    printWriter.append(JSON.toJSONString(body));
                })
                .accessDeniedHandler((request, response, e) -> {
                    // 权限异常处理
                    HttpStatus status = HttpStatus.FORBIDDEN;
                    HashMap<String, Object> body = new HashMap<>(3);
                    body.put("code", status.value());
                    body.put("error", "access_denied");
                    body.put("error_message", "权限不足,不允许访问!!");
                    response.setStatus(HttpStatus.OK.value());
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter printWriter = response.getWriter();
                    printWriter.append(JSON.toJSONString(body));
                });
    }

    /**
     * Spring OAuth 自带类。如有特殊定制实现 ResourceServerTokenServices 接口
     *
     * @return
     */
    @Bean
    public RemoteTokenServices remoteTokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        // 设置客户端基本信息
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/api/auth/oauth/check_token");
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new ClientAccessTokenConverter());
        tokenServices.setAccessTokenConverter(defaultAccessTokenConverter);
        return tokenServices;
    }
}
