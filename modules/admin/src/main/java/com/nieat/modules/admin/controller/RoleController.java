package com.nieat.modules.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.common.core.controller.BaseController;
import com.nieat.common.core.utils.ResponseBody;
import com.nieat.modules.admin.dto.SysRoleDTO;
import com.nieat.modules.admin.service.ISysRoleService;
import com.nieat.modules.admin.vo.SysRoleVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/13 0013 下午 22:40
 */
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController extends BaseController {
    private final ISysRoleService service;

    @GetMapping("/list")
    public IPage<SysRoleVO> list(Page page) {
        return service.list(page);
    }

    @GetMapping("/detail")
    public SysRoleVO doDetail(String id) {
        return service.detail(id);
    }

    @PostMapping("/save")
    public ResponseBody doSave(@RequestBody SysRoleDTO dto) {
        service.save(dto);
        return doJsonDefault();
    }

    @PostMapping("/edit")
    public ResponseBody doEdit(@RequestBody SysRoleDTO dto) {
        service.edit(dto);
        return doJsonDefault();
    }

    @PostMapping("/del")
    public ResponseBody doDel(String ids) {
        service.del(ids);
        return doJsonDefault();
    }
}
