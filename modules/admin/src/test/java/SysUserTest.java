import base.BaseTest;
import com.nieat.modules.admin.dao.ISysUserDao;
import com.nieat.modules.admin.service.ISysUserService;
import com.nieat.modules.admin.vo.SysRoleVO;
import com.nieat.modules.admin.vo.SysUserVO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/4 0004 下午 17:27
 */
public class SysUserTest extends BaseTest {
    @Resource
    private ISysUserService service;
    @Resource
    private ISysUserDao userDao;

    /**
     * 单个用户查询
     */
    @Test
    public void queryOneUser() {
        SysUserVO user = service.loginUsername("admin");
        user.getRoles().forEach(roles -> {
            System.out.println(roles);
        });
    }

    @Test
    public void insertRoleRelationTest() {
        userDao.insertRoleRelation("2", Arrays.asList("3", "4"));
    }

    @Test
    public void applicationContext() {
        System.out.println("11");
    }

    @Test
    public void detailUserTest(){
        List<SysRoleVO> vo = service.detail("1");
        vo.stream().map(SysRoleVO::getRoleName).forEach(role -> System.out.println(role));
    }
}
