package cn.tianhan.security.auth.config;

import cn.tianhan.security.auth.component.ServerTokenEnhancer;
import cn.tianhan.security.auth.service.IUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final IUserDetailService userDetail;
    /**
     * 认证服务器-AccessToken内容增强
     */
    private final ServerTokenEnhancer tokenEnhancer;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate redisTemplate;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 客户端信息存放到内存当中
        clients.inMemory()
                .withClient("nieat")
                .secret("$2a$10$fP/LjmRd/fw2MXapmIr0H.y9/YE1onpg9XfWmtIMQW/ejzlIyxlPW")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("all");
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // redis 持久化保持Token
                .tokenStore(new RedisTokenStore(redisTemplate.getConnectionFactory()))
                .tokenEnhancer(tokenEnhancer)
                .userDetailsService(userDetail)
                .authenticationManager(authenticationManager)
                // 禁止刷新TOKEN
                .reuseRefreshTokens(false)
                // 自定义异常处理信息
                .exceptionTranslator((e) -> {
                    HttpStatus status;
                    String error = "", errorMessage = e.getMessage();
                    if (e instanceof InvalidTokenException) {
                        // AccessToken 验证失败异常
                        status = HttpStatus.FORBIDDEN;
                        error = "Bad Token!!";
                        errorMessage = "无效凭证";
                    } else if (e instanceof InternalAuthenticationServiceException) {
                        // AccessToken 请求异常
                        status = HttpStatus.UNAUTHORIZED;
                        error = "Get Token Fail!!";
                        errorMessage = "用户名或密码错误";
                    } else {
                        status = HttpStatus.NOT_ACCEPTABLE;
                    }
                    OAuth2Exception ex = new OAuth2Exception(error);
                    ex.addAdditionalInformation("code", String.valueOf(status.value()));
                    ex.addAdditionalInformation("error_message", errorMessage);
                    return ResponseEntity.status(HttpStatus.OK).body(ex);
                });
    }
}
