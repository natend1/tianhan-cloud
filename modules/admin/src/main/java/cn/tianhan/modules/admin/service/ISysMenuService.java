package cn.tianhan.modules.admin.service;

import cn.tianhan.modules.admin.dto.SysMenuDTO;
import cn.tianhan.modules.admin.entity.SysMenuEntity;
import cn.tianhan.modules.admin.vo.SysMenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:07
 */
public interface ISysMenuService extends IService<SysMenuEntity> {
    /**
     * 树级结果菜单列表
     *
     * @return 树级结构菜单列表
     */
    List<SysMenuVO> listTree();

    void save(SysMenuDTO dto);

    void edit(SysMenuDTO dto);

    void del(String ids);
}
