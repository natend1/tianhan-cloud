package cn.tianhan.modules.admin.service.impl;

import cn.tianhan.modules.admin.dao.ISysMenuDao;
import cn.tianhan.modules.admin.dto.SysMenuDTO;
import cn.tianhan.modules.admin.entity.SysMenuEntity;
import cn.tianhan.modules.admin.service.ISysMenuService;
import cn.tianhan.modules.admin.vo.SysMenuVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void save(SysMenuDTO dto) {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtils.copyProperties(dto, entity);
        this.save(entity);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void edit(SysMenuDTO dto) {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void del(String ids) {
        this.removeByIds(Arrays.asList(ids.split(",")));
    }
}
