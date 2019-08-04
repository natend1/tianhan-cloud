package com.nieat.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * @Author NieAt
 * @Description
 * @create 2019/6/30 0030 下午 15:39
 */
@Configuration
@Slf4j
public class FilterConfig {
    private final static String OAUTH_URI = "/oauth/token";
    @Resource
    private CaptchaConfig captchaConfig;

    @Bean
    public GlobalFilter oauthFilter() {
        return ((exchange, chain) -> {
            LinkedHashSet requiredAttribute = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
            ServerHttpRequest request = exchange.getRequest();
            String requestUri = request.getPath().pathWithinApplication().value();
            if (requiredAttribute != null) {
                Iterator<URI> iterator = requiredAttribute.iterator();
                while (iterator.hasNext()) {
                    URI uri = iterator.next();
                }
            }
            if (OAUTH_URI.equals(requestUri)) {
                String captcha = request.getQueryParams().getFirst("captcha");
                String salt = request.getQueryParams().getFirst("salt");
                // 验证码校验
                if (!captchaConfig.checkCaptcha(captcha, salt)) {
                    ServerHttpRequest mutateRequest = exchange.getRequest().mutate().method(HttpMethod.GET).path("/captcha-error").build();
                    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                    Route mutateRoute = Route.async()
                            .id(route.getId())
                            .order(route.getOrder())
                            .uri(mutateRequest.getURI())
                            .asyncPredicate(route.getPredicate()).build();
                    Map<String, Object> map = exchange.getAttributes();
                    // 动态修改请求路由
                    map.put(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR, mutateRoute);
                    map.put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, mutateRequest.getURI());
                    exchange = exchange.mutate().request(mutateRequest).build();
                    return chain.filter(exchange);
                }
            }
            return chain.filter(exchange);
        });
    }

    /**
     * Gateway 解决跨域
     *
     * @return
     */
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (!CorsUtils.isCorsRequest(request)) {
                return chain.filter(ctx);
            }

            HttpHeaders requestHeaders = request.getHeaders();
            ServerHttpResponse response = ctx.getResponse();
            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
            HttpHeaders headers = response.getHeaders();
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
            if (requestMethod != null) {
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
            }
            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            // headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "18000L");
            if (request.getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return Mono.empty();
            }
            return chain.filter(ctx);
        };
    }
}
