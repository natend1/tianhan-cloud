package cn.tianhan.modules.admin.service;

import cn.tianhan.modules.admin.dto.SysUserDTO;
import cn.tianhan.modules.admin.entity.SysUserEntity;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import cn.tianhan.modules.admin.vo.SysUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param dto
     * @return
     */
    void saveUser(SysUserDTO dto);

    void editUser(SysUserDTO dto);


    /**
     * 登录
     *
     * @param username
     * @return
     */
    SysUserVO loginUsername(String username);
}
