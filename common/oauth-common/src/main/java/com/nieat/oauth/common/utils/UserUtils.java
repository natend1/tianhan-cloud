package com.nieat.oauth.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/3 0003 上午 0:02
 */
public class UserUtils {
    /**
     * 获取当前用户信息
     *
     * @return
     */
    public static UserPrincipal currentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserPrincipal) authentication.getPrincipal();
    }
}
