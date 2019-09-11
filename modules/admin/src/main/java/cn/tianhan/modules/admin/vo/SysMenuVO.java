package cn.tianhan.modules.admin.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:13
 */
@Data
@Accessors(chain = true)
public class SysMenuVO implements Serializable {
    private String id;
    private String parentName;
    private String parentId;
    private String menuName;
    private String menuUri;
    private String authority;
    private Date createTime;
    private String createUser;
    private List<SysMenuVO> children;
}
