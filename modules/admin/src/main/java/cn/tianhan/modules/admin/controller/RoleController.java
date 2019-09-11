package cn.tianhan.modules.admin.controller;

import cn.tianhan.common.core.controller.BaseController;
import cn.tianhan.common.core.utils.ResponseBody;
import cn.tianhan.modules.admin.dto.SysRoleDTO;
import cn.tianhan.modules.admin.service.ISysRoleService;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
