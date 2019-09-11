package cn.tianhan.oauth.common.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/5 0005 下午 23:47
 */
@Data
@Accessors(chain = true)
public class UserPrincipal {
    private String username;
    private String nickname;
    private List<String> roles;
    private List<String> authorities;
}
