package com.nieat.modules.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.modules.admin.service.ISysUserService;
import com.nieat.modules.admin.vo.SysUserVO;
import com.nieat.oauth.common.utils.UserPrincipal;
import com.nieat.oauth.common.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final ISysUserService userService;

    @PostMapping("/validator")
    public SysUserVO validator(String username) {
        SysUserVO user = userService.loginUsername(username);
        return user;
    }

    @GetMapping("list")
    public IPage<SysUserVO> list(Page page) {
        return userService.list(page);
    }

    /**
     * 获取用户关联角色信息
     * @param id 用户ID
     * @return 角色信息
     */
    @GetMapping("detail")
    public HashMap<String,Object> detail(String id){
        HashMap<String,Object> body = new HashMap<>(1);
        body.put("roles",userService.detail(id));
        return body;
    }

    @PostMapping("del")
    public void doDel(@RequestParam("ids") String ids) {
        userService.doDel(ids);
    }

    @PostMapping("save")
    public void saveUser(@RequestBody SysUserVO userVO) {
        userService.saveUser(userVO);
    }

    @PostMapping("edit")
    public void editUser(@RequestBody SysUserVO userVO) {
        userService.editUser(userVO);
    }

    @PreAuthorize("@permit.hasAuthority('sys_user')")
    @GetMapping("/info")
    public SysUserVO info() {
        UserPrincipal userPrincipal = UserUtils.currentUserInfo();
        return new SysUserVO()
                .setUsername(userPrincipal.getUsername())
                .setNickname(userPrincipal.getNickname())
                .setRoles(userPrincipal.getRoles())
                .setAuthorities(userPrincipal.getAuthorities());
    }
}
