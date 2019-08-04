package com.nieat.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nieat.gateway.utils.ResponseBody;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @Author NieAt
 * @Description
 * @create 2019/6/30 0030 下午 20:44
 */
@Configuration
public class RouteLocatorConfig {
    @Resource
    private CaptchaConfig captchaConfig;

    /**
     * 官方文档(BETA)
     * 验证码校验 与 修改认证服务器响应数据格式
     *
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator routesOAuth(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("checkRequestAndWrapperResponse", r -> r.path("/api/auth/oauth/token")
                        .filters(f -> f.rewritePath("/api/auth", "")
                                .modifyResponseBody(String.class, ResponseBody.class, (exchange, s) -> {
                                    // 此处响应数据封装
                                    ServerHttpResponse httpResponse = exchange.getResponse();
                                    HttpStatus status = httpResponse.getStatusCode();
                                    httpResponse.setStatusCode(HttpStatus.OK);

                                    JSONObject content = JSON.parseObject(s);
                                    Integer code = content.getInteger("code");
                                    code = code != null ? code : status.value();
                                    ResponseBody responseBody = new ResponseBody();
                                    responseBody.setCode(code);
                                    if (code == HttpStatus.OK.value()) {
                                        responseBody.setMessage("success");
                                    } else {
                                        String error = content.getString("error_message");
                                        String message = error != null ? error : "error";
                                        responseBody.setMessage(message);
                                    }
                                    responseBody.setData(content);
                                    return Mono.just(responseBody);
                                })).uri("lb://nieat-security-auth").order(-2)).build();
    }

    @Bean
    public RouteLocator routesAdmin(RouteLocatorBuilder builder) {
        return builder.routes().route("rewrite_admin_response", r -> r.path("/api/admin/**")
                .filters(f -> f.rewritePath("/api/admin", "")
                        .modifyResponseBody(String.class, ResponseBody.class, (exchange, s) -> {
                            ServerHttpResponse httpResponse = exchange.getResponse();
                            HttpStatus status = httpResponse.getStatusCode();
                            httpResponse.setStatusCode(HttpStatus.OK);

                            JSONObject content = JSON.parseObject(s);
                            Integer code = content.getInteger("code");
                            code = code != null ? code : status.value();
                            ResponseBody responseBody = new ResponseBody();
                            responseBody.setCode(code);
                            if (code == HttpStatus.OK.value()) {
                                responseBody.setMessage("success");
                            } else {
                                String error = content.getString("error_message");
                                String message = error != null ? error : "error";
                                responseBody.setMessage(message);
                            }
                            responseBody.setData(content);
                            return Mono.just(responseBody);
                        })).uri("lb://nieat-admin")).build();
    }
}
