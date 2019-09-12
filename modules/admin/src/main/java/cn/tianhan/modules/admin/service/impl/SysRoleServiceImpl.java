package cn.tianhan.modules.admin.service.impl;

import cn.tianhan.modules.admin.dao.ISysRoleDao;
import cn.tianhan.modules.admin.dto.SysRoleDTO;
import cn.tianhan.modules.admin.entity.SysRoleEntity;
import cn.tianhan.modules.admin.service.ISysRoleService;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void save(SysRoleDTO dto) {
        SysRoleEntity entity = new SysRoleEntity()
                .setRoleName(dto.getRoleName());
        this.save(entity);
        super.baseMapper.insertRoleMenuRelation(entity.getId(), dto.getMenus());
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void edit(SysRoleDTO dto) {
        SysRoleEntity entity = new SysRoleEntity()
                .setId(dto.getId())
                .setRoleName(dto.getRoleName());
        this.updateById(entity);
        super.baseMapper.delRoleMenuRelation(entity.getId());
        super.baseMapper.insertRoleMenuRelation(entity.getId(), dto.getMenus());
    }

    @Transactional
    @Override
    public void del(String ids) {
        this.removeByIds(Arrays.asList(ids.split(",")));
    }
}
