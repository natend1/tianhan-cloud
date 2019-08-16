package com.nieat.modules.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.List;

@Data
@Accessors(chain = true)
public class SysUserDTO {
    private String id;
    private String username;
    private String password;
    private String nickname;
    /**
     * 角色ID
     */
    private List<String> roleIds;
    private List<String> roles;
    /**
     * 资源编码
     */
    private Collection<String> authorities;
}
