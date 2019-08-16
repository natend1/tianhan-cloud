package com.nieat.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nieat.modules.admin.entity.SysMenuEntity;
import com.nieat.modules.admin.vo.SysMenuVO;

import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:51
 */
public interface ISysMenuDao extends BaseMapper<SysMenuEntity> {
    List<SysMenuVO> queryList();
}
