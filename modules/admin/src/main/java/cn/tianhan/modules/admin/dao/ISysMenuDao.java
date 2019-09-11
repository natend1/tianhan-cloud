package cn.tianhan.modules.admin.dao;

import cn.tianhan.modules.admin.entity.SysMenuEntity;
import cn.tianhan.modules.admin.vo.SysMenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:51
 */
public interface ISysMenuDao extends BaseMapper<SysMenuEntity> {
    List<SysMenuVO> queryList();
}
