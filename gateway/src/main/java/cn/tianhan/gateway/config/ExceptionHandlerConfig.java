package cn.tianhan.gateway.config;

import cn.tianhan.gateway.component.WrapperExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/2 0002 下午 15:18
 */
@Configuration
public class ExceptionHandlerConfig {

    @Primary
    @Bean
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
                                                             ServerCodecConfigurer serverCodecConfigurer) {
        WrapperExceptionHandler handler = new WrapperExceptionHandler();
        handler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        handler.setMessageWriters(serverCodecConfigurer.getWriters());
        handler.setMessageReaders(serverCodecConfigurer.getReaders());
        return handler;
    }
}
