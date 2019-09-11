package cn.tianhan.security.auth.vo;

import lombok.Data;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 14:47 2019/3/21
 */
@Data
public class UserVO {
    private String username;
    private String password;
    private String nickname;
    private List<String> roles;
    /**
     * 资源编码
     */
    private LinkedHashSet<String> authorities;
}
