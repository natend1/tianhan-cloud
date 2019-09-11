import cn.tianhan.gateway.GatewayApplication;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/1 0001 下午 18:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GatewayApplication.class)
public class RedisTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        String key = new String(DigestUtils.md5("okls" + "zlztf"));
        System.out.println(key);
        String value = "zlztf";
        redisTemplate.opsForValue().set(key, value, 60 * 2, TimeUnit.SECONDS);
        System.out.println(redisTemplate.boundValueOps(key).get());
        //redisTemplate.delete("nieat");
    }
}
