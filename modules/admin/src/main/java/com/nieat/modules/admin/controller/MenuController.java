package com.nieat.modules.admin.controller;

import com.nieat.common.core.controller.BaseController;
import com.nieat.common.core.utils.ResponseBody;
import com.nieat.modules.admin.dto.SysMenuDTO;
import com.nieat.modules.admin.service.ISysMenuService;
import com.nieat.modules.admin.vo.SysMenuVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/13 0013 下午 22:40
 */
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController extends BaseController {
    private final ISysMenuService service;

    @GetMapping("/tree")
    public ResponseBody listTree() {
        HashMap<String, Object> body = new HashMap<>(1);
        body.put("tree", service.listTree());
        return doJsonData(body);
    }

    @PostMapping("/save")
    public ResponseBody doSave(SysMenuDTO dto) {
        service.save(dto);
        return doJsonDefault();
    }

    @PostMapping("/edit")
    public ResponseBody doEdit(SysMenuDTO dto) {
        service.edit(dto);
        return doJsonDefault();
    }

    @PostMapping("/del")
    public ResponseBody doDel(String ids) {
        service.del(ids);
        return doJsonDefault();
    }
}
