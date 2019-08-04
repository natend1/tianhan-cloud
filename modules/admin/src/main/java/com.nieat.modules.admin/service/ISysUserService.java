package com.nieat.modules.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nieat.modules.admin.entity.SysUserEntity;
import com.nieat.modules.admin.vo.SysRoleVO;
import com.nieat.modules.admin.vo.SysUserVO;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:06
 */
public interface ISysUserService extends IService<SysUserEntity> {
    /**
     * 查询接口
     *
     * @param page
     * @return
     */
    IPage<SysUserVO> list(Page page);


    /**
     * 获取用户关联角色信息
     * @param id 用户ID
     * @return 角色信息
     */
    List<SysRoleVO> detail(String id);

    void doDel(String ids);

    /**
     * 保存
     *
     * @param vo
     * @return
     */
    void saveUser(SysUserVO vo);

    void editUser(SysUserVO vo);


    /**
     * 登录
     *
     * @param username
     * @return
     */
    SysUserVO loginUsername(String username);
}
