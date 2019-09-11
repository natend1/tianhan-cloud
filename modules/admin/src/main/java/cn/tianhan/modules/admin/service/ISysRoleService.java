package cn.tianhan.modules.admin.service;

import cn.tianhan.modules.admin.dto.SysRoleDTO;
import cn.tianhan.modules.admin.entity.SysRoleEntity;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:07
 */
public interface ISysRoleService extends IService<SysRoleEntity> {
    IPage<SysRoleVO> list(Page page);

    SysRoleVO detail(String id);

    void save(SysRoleDTO dto);

    void edit(SysRoleDTO dto);

    void del(String ids);
}
