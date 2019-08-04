import base.BaseTest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.modules.admin.service.ISysUserService;
import com.nieat.modules.admin.vo.SysUserVO;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/5 0005 下午 21:03
 */

public class UserControllerTest extends BaseTest {
    @Resource
    private ISysUserService userService;

    @Test
    public void loginUserNameTest() {
        String username = "admin";
        SysUserVO vo = userService.loginUsername(username);
        System.out.println(vo);
    }

    @Test
    public void queryList() {
        IPage<SysUserVO> vos = userService.list(new Page());
        System.out.println(vos);
    }
}
