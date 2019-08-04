package com.nieat.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.modules.admin.entity.SysRoleEntity;
import com.nieat.modules.admin.vo.SysMenuVO;
import com.nieat.modules.admin.vo.SysRoleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:51
 */
public interface ISysRoleDao extends BaseMapper<SysRoleEntity> {
    /**
     * 查询角色
     *
     * @return
     */
    SysRoleVO queryRoleMenu(@Param("id") String id);

    IPage<SysRoleVO> queryList(Page page);

    void insertRoleMenuRelation(@Param("roleId") String roleId, @Param("menus") List<SysMenuVO> menus);

    void delRoleMenuRelation(@Param("roleId") String roleId);
}
