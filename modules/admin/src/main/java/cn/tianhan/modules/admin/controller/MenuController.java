package cn.tianhan.modules.admin.controller;

import cn.tianhan.common.core.controller.BaseController;
import cn.tianhan.common.core.utils.ResponseBody;
import cn.tianhan.modules.admin.dto.SysMenuDTO;
import cn.tianhan.modules.admin.service.ISysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
