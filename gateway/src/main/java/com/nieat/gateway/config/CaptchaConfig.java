package com.nieat.gateway.config;

import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAt
 * @Description 验证码配置
 * @create 2019/7/1 0001 下午 15:53
 */
@Configuration
@AllArgsConstructor
public class CaptchaConfig {
    private final static String KEY = "nieat-gateway:captcha:";
    private final RedisTemplate redisTemplate;

    @Bean
    public RouterFunction<ServerResponse> captchaRouter() {
        return RouterFunctions.route(RequestPredicates.GET("/captcha/{salt}"), this::captchaDeal)
                .andRoute(RequestPredicates.GET("/captcha-error"), req -> {
                    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
                    HashMap<String, Object> body = new HashMap<>(2);
                    body.put("code", status.value());
                    body.put("error_message", "验证码错误,请重新刷新!");
                    return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
                            .body(BodyInserters.fromObject(JSON.toJSONString(body)));
                });
    }

    private Mono<ServerResponse> captchaDeal(ServerRequest request) {
        // 生成验证码片段
        LineCaptcha captcha = new LineCaptcha(200, 80);
        // 临时内存缓存
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // 写入
        captcha.write(bos);
        // 存放到Redis, 设置两分钟的生命周期
        String salt = request.pathVariable("salt");
        String code = captcha.getCode();
        String key = KEY + createHashCode(salt + code);
        redisTemplate.opsForValue().set(key, captcha.getCode(), 60 * 3, TimeUnit.SECONDS);
        return ServerResponse.ok().contentType(MediaType.IMAGE_PNG).
                body(BodyInserters.fromDataBuffers(Flux.just(new DefaultDataBufferFactory().wrap(bos.toByteArray()))));
    }

    /**
     * 验证码校验
     *
     * @param captcha
     * @param salt
     * @return
     */
    public boolean checkCaptcha(String captcha, String salt) {
        String key = KEY + createHashCode(captcha + salt);
        String value = (String) redisTemplate.opsForValue().get(key);
        if (value == null || !value.equals(captcha)) {
            return false;
        } else {
            return true;
        }
    }

    private String createHashCode(String str) {
        byte[] md = DigestUtils.md5(str);
        BigInteger code = new BigInteger(1, md);
        return code.toString(16);
    }
}
