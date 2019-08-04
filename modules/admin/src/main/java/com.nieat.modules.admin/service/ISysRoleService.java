package com.nieat.modules.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nieat.modules.admin.entity.SysRoleEntity;
import com.nieat.modules.admin.vo.SysRoleVO;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:07
 */
public interface ISysRoleService extends IService<SysRoleEntity> {
    IPage<SysRoleVO> list(Page page);

    SysRoleVO detail(String id);

    void save(SysRoleVO vo);

    void edit(SysRoleVO vo);

    void del(String ids);
}
