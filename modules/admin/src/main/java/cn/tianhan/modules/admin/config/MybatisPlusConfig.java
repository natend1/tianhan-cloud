package cn.tianhan.modules.admin.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:52
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 注册分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
