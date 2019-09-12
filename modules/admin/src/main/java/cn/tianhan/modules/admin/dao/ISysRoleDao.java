package cn.tianhan.modules.admin.dao;

import cn.tianhan.modules.admin.dto.SysMenuDTO;
import cn.tianhan.modules.admin.entity.SysRoleEntity;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    void insertRoleMenuRelation(@Param("roleId") String roleId, @Param("menus") List<SysMenuDTO> menus);

    void delRoleMenuRelation(@Param("roleId") String roleId);
}
