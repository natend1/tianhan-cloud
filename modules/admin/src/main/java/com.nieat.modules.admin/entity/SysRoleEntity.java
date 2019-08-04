package com.nieat.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:34
 */
@Data
@TableName("sys_role")
@Accessors(chain = true)
public class SysRoleEntity extends Model<SysRoleEntity> {
    @TableId(type = IdType.AUTO)
    private String id;
    private String roleName;
    private Date createTime;
    private String createUser;
    /**
     * 关联菜单
     */
    private List<SysMenuEntity> menus;
}
