package com.nieat.modules.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nieat.modules.admin.dao.ISysRoleDao;
import com.nieat.modules.admin.entity.SysRoleEntity;
import com.nieat.modules.admin.service.ISysRoleService;
import com.nieat.modules.admin.vo.SysRoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:08
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<ISysRoleDao, SysRoleEntity>
        implements ISysRoleService {
    @Override
    public IPage<SysRoleVO> list(Page page) {
        return super.baseMapper.queryList(page);
    }

    @Override
    public SysRoleVO detail(String id) {
        return super.baseMapper.queryRoleMenu(id);
    }

    @Transactional
    @Override
    public void save(SysRoleVO vo) {
        SysRoleEntity entity = new SysRoleEntity()
                .setRoleName(vo.getRoleName());
        this.save(entity);
        super.baseMapper.insertRoleMenuRelation(entity.getId(), vo.getMenus());
    }

    @Transactional
    @Override
    public void edit(SysRoleVO vo) {
        SysRoleEntity entity = new SysRoleEntity()
                .setId(vo.getId())
                .setRoleName(vo.getRoleName());
        this.updateById(entity);
        super.baseMapper.delRoleMenuRelation(entity.getId());
        super.baseMapper.insertRoleMenuRelation(entity.getId(), vo.getMenus());
    }

    @Transactional
    @Override
    public void del(String ids) {
        this.removeByIds(Arrays.asList(ids.split(",")));
    }
}
