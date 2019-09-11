package cn.tianhan.modules.admin.entity;

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
 * @create 2019/7/4 0004 下午 16:33
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserEntity extends Model<SysUserEntity> {
    @TableId(type = IdType.AUTO)
    private String id;
    private String username;
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    private String email;
    private String phone;
    private Date createTime;
    private String createUser;
    /**
     * 角色列表 一对多
     */
    private List<SysRoleEntity> roles;
}
