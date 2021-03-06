package cn.tianhan.modules.admin.service.impl;

import cn.tianhan.common.cache.annotation.CacheString;
import cn.tianhan.modules.admin.dao.ISysUserDao;
import cn.tianhan.modules.admin.dto.SysUserDTO;
import cn.tianhan.modules.admin.entity.SysMenuEntity;
import cn.tianhan.modules.admin.entity.SysRoleEntity;
import cn.tianhan.modules.admin.entity.SysUserEntity;
import cn.tianhan.modules.admin.service.ISysUserService;
import cn.tianhan.modules.admin.vo.SysRoleVO;
import cn.tianhan.modules.admin.vo.SysUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:08
 */
@AllArgsConstructor
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<ISysUserDao, SysUserEntity>
        implements ISysUserService {
    @Override
    public IPage<SysUserVO> list(Page page) {
        return super.baseMapper.list(page);
    }

    @Override
    public List<SysRoleVO> detail(String id) {
        return this.baseMapper.detailUser(id);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void doDel(String ids) {
        this.removeByIds(Arrays.asList(ids.split(",")));
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void saveUser(SysUserDTO dto) {
        SysUserEntity userEntity = new SysUserEntity()
                .setUsername(dto.getUsername())
                .setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .setNickname(dto.getNickname());
        this.save(userEntity);
        super.baseMapper.insertRoleRelation(userEntity.getId(), dto.getRoleIds());
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void editUser(SysUserDTO dto) {
        SysUserEntity userEntity = new SysUserEntity()
                .setId(dto.getId())
                .setUsername(dto.getUsername())
                .setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()))
                .setNickname(dto.getNickname());
        this.updateById(userEntity);
        super.baseMapper.delRoleRelation(userEntity.getId());
        super.baseMapper.insertRoleRelation(userEntity.getId(), dto.getRoleIds());
    }

    @Override
    @CacheString(key = "com:nieat:modules:admin:{#0}", param = "0")
    public SysUserVO loginUsername(String username) {
        SysUserEntity userEntity = super.baseMapper.loginUsername(username);
        List<SysRoleEntity> roleEntities = userEntity.getRoles();
        List<String> roles = roleEntities.stream().map(SysRoleEntity::getRoleName).collect(Collectors.toList());

        LinkedHashSet<String> authorities = new LinkedHashSet<>();
        roleEntities.stream().map(SysRoleEntity::getMenus).forEach(menuEntities -> {
            authorities.addAll(menuEntities.stream().map(SysMenuEntity::getAuthority)
                    .filter(authority -> !authorities.contains(authority)).collect(Collectors.toList()));
        });

        SysUserVO vo = new SysUserVO()
                .setUsername(userEntity.getUsername())
                .setPassword(userEntity.getPassword())
                .setNickname(userEntity.getNickname())
                .setRoles(roles)
                .setAuthorities(authorities);
        return vo;
    }
}
