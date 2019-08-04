package com.nieat.modules.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nieat.modules.admin.dao.ISysMenuDao;
import com.nieat.modules.admin.entity.SysMenuEntity;
import com.nieat.modules.admin.service.ISysMenuService;
import com.nieat.modules.admin.vo.SysMenuVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:08
 */
@Service("sysMenuService")
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<ISysMenuDao, SysMenuEntity>
        implements ISysMenuService {
    @Override
    public List<SysMenuVO> listTree() {
        List<SysMenuVO> data = super.baseMapper.queryList();
        return createTree(data);
    }

    private List<SysMenuVO> createTree(List<SysMenuVO> target) {
        String parent = "-1";
        List<SysMenuVO> parents = new ArrayList<>();
        HashMap<String, List<SysMenuVO>> children = new HashMap<>();
        for (SysMenuVO menu : target) {
            if (parent.equals(menu.getParentId())) {
                parents.add(menu);
            } else {
                if (!children.containsKey(menu.getParentId())) {
                    List<SysMenuVO> child = new ArrayList<>();
                    child.add(menu);
                    children.put(menu.getParentId(), child);
                } else {
                    children.get(menu.getParentId()).add(menu);
                }
            }
        }
        parents.forEach(menu -> findChildren(children, menu));
        return parents;
    }

    private void findChildren(HashMap<String, List<SysMenuVO>> children, SysMenuVO parent) {
        if (children.containsKey(parent.getId())) {
            List<SysMenuVO> child = children.get(parent.getId());
            parent.setChildren(child);
            children.remove(parent.getId());
            child.forEach(menu -> {
                menu.setParentName(parent.getMenuName());
                findChildren(children, menu);
            });
        }
    }

    @Override
    public void save(SysMenuVO vo) {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtils.copyProperties(vo,entity);
        this.save(entity);
    }

    @Override
    public void edit(SysMenuVO vo) {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtils.copyProperties(vo, entity);
        this.updateById(entity);
    }

    @Override
    public void del(String ids) {
        this.removeByIds(Arrays.asList(ids.split(",")));
    }
}
