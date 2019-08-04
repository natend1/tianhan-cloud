import base.BaseTest;
import com.alibaba.fastjson.JSON;
import com.nieat.modules.admin.entity.SysMenuEntity;
import com.nieat.modules.admin.service.ISysMenuService;
import com.nieat.modules.admin.vo.SysMenuVO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/22 0022 下午 20:52
 */
public class SysMenuTest extends BaseTest {
    @Resource
    private ISysMenuService service;

    @Test
    public void queryListService() {
        List<SysMenuVO> tree = service.listTree();
        System.out.println(JSON.toJSONString(tree));
    }

    @Test
    public void saveMenu() {
        SysMenuVO vo = new SysMenuVO()
                .setParentId("2").setMenuName("新增").setMenuUri("/role/save").setAuthority("sys:role:save");
        service.save(vo);
    }

    @Test
    public void updateMenu() {
        SysMenuEntity entity = service.getBaseMapper().selectById(6);
        entity.setParentId("1");
        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(entity, vo);
        service.edit(vo);
    }
}
