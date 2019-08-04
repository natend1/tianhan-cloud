package com.nieat.modules.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 16:34
 */
@TableName("sys_menu")
@Data
public class SysMenuEntity extends Model<SysMenuEntity> {
    @TableId(type = IdType.AUTO)
    private String id;
    private String parentId;
    private String menuName;
    private String menuUri;
    /**
     * 授权Code
     */
    private String authority;
    private Date createTime;
    private String createUser;
}
