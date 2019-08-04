package com.nieat.security.auth.component;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Author: NieAnTai
 * @Description: 认证端服务器自定义用户类
 * @Date: 15:06 2019/3/28
 */
public class UserServer extends User {
    @Getter
    private String nickname;
    @Getter
    private List<String> roles;

    public UserServer(String username, String password, Collection<? extends GrantedAuthority> authorities, String nickname, List<String> roles) {
        super(username, password, authorities);
        this.nickname = nickname;
        this.roles = roles;
    }

    public UserServer(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String nickname, List<String> roles) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.nickname = nickname;
        this.roles = roles;
    }
}
