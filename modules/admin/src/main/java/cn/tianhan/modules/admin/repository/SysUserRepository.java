package cn.tianhan.modules.admin.repository;

import cn.tianhan.modules.admin.entity.SysUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 2019/09/12 16:13
 */
public interface SysUserRepository extends JpaRepository<SysUserEntity, String> {

    /**
     * 用户登录-按用户名查询
     *
     * @param username
     * @return
     */
    SysUserEntity findByUsername(String username);
}
