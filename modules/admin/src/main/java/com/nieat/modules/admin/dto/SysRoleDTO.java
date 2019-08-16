package com.nieat.modules.admin.dto;

import com.nieat.modules.admin.vo.SysMenuVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:13
 */
@Data
public class SysRoleDTO {
    private String id;
    private String roleName;
    private Date createTime;
    private String createUser;
    /**
     * 关联菜单
     */
    private List<SysMenuDTO> menus;
}
