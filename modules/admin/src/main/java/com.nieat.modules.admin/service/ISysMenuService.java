package com.nieat.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nieat.modules.admin.entity.SysMenuEntity;
import com.nieat.modules.admin.vo.SysMenuVO;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:07
 */
public interface ISysMenuService extends IService<SysMenuEntity> {
    List<SysMenuVO> listTree();

    void save(SysMenuVO vo);

    void edit(SysMenuVO vo);

    void del(String ids);
}
