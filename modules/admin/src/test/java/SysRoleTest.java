import base.BaseTest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nieat.modules.admin.service.ISysRoleService;
import com.nieat.modules.admin.vo.SysMenuVO;
import com.nieat.modules.admin.vo.SysRoleVO;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/5 0005 下午 20:16
 */
public class SysRoleTest extends BaseTest {

    @Resource
    private ISysRoleService service;

    @Test
    public void readRoleTest() {
        IPage<SysRoleVO> pages = service.list(new Page());
        if (pages.getRecords() != null && pages.getRecords().size() > 0) {
            service.detail(pages.getRecords().stream()
                    .map(SysRoleVO::getId).findFirst().get()).getMenus().stream().map(SysMenuVO::getMenuName).forEach(m -> System.out.println(m));
        } else {
            System.out.println("pages 没有数据");
        }
    }

    @Test
    public void insertRoleTest() {
        SysRoleVO vo = new SysRoleVO();
        vo.setRoleName("测试");
        List<SysMenuVO> menuVOS = new ArrayList<>();
        menuVOS.add(new SysMenuVO().setId("5").setAuthority("sys_test"));
        vo.setMenus(menuVOS);
        service.save(vo);
    }

    @Test
    public void delRoleTest() {
        String ids = "11";
        service.del(ids);
    }
}
