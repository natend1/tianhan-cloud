package com.nieat.modules.admin.controller;

import com.nieat.modules.admin.service.ISysMenuService;
import com.nieat.modules.admin.vo.SysMenuVO;
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
public class MenuController {
    private final ISysMenuService service;

    @GetMapping("/tree")
    public HashMap<String, Object> listTree() {
        HashMap<String, Object> body = new HashMap<>(1);
        body.put("tree", service.listTree());
        return body;
    }

    @PostMapping("/save")
    public void doSave(SysMenuVO vo) {
        service.save(vo);
    }

    @PostMapping("/edit")
    public void doEdit(SysMenuVO vo) {
        service.edit(vo);
    }

    @PostMapping("/del")
    public void doDel(String ids) {
        service.del(ids);
    }
}
