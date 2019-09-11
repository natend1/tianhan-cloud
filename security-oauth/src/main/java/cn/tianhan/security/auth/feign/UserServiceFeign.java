package cn.tianhan.security.auth.feign;

import cn.tianhan.security.auth.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: NieAnTai
 * @Description: 调用用户模块
 * @Date: 14:45 2019/3/21
 */
@FeignClient(name = "nieat-admin", configuration = {})
public interface UserServiceFeign {
    @PostMapping("/user/validator")
    public UserVO validator(@RequestParam String username);
}
