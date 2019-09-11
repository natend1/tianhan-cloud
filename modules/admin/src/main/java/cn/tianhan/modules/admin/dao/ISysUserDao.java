package cn.tianhan.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.modules.admin.entity.SysUserEntity;
import com.nieat.modules.admin.vo.SysRoleVO;
import com.nieat.modules.admin.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:50
 */
public interface ISysUserDao extends BaseMapper<SysUserEntity> {
    /**
     * 用户登录-按用户名查询
     *
     * @param username
     * @return
     */
    SysUserEntity loginUsername(@Param("username") String username);

    IPage<SysUserVO> list(Page page);

    /**
     * 获取用户关联角色信息
     * @param id 用户ID
     * @return 角色信息
     */
    List<SysRoleVO> detailUser(@Param("id") String id);

    /**
     * 保存用户与角色关联关系
     *
     * @param userId
     * @param ids
     */
    void insertRoleRelation(@Param("userId") String userId, @Param("roleIds") List<String> ids);

    void delRoleRelation(@Param("userId") String userId);
}
