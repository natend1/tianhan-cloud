import base.BaseTest;
import com.nieat.modules.admin.vo.SysUserVO;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Set;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/7 0007 下午 13:42
 */
public class RedisTemplateTest extends BaseTest {
    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void RedisZSet() {
        redisTemplate.delete(Arrays.asList("zset-1", "zset-2", "zset-i", "zset-u"));
        // 创建第一个有序集合
        BoundZSetOperations<String, Object> zset1 = redisTemplate.boundZSetOps("zset-1");
        zset1.add("a", 1);
        zset1.add("b", 2);
        zset1.add("c", 3);
        Set<Object> set1 = zset1.rangeByScore(0, -1);
        set1.stream().forEach(ob -> System.out.println(ob));

        // 创建第二个有序集合
        BoundZSetOperations<String, Object> zset2 = redisTemplate.boundZSetOps("zset-2");
        zset2.add("b", 4);
        zset2.add("c", 1);
        zset2.add("d", 0);

        // 交集运算 zset-i
        redisTemplate.opsForZSet().intersectAndStore("zset-1", "zset-2", "zset-i");

        // 并集运算 zset-u
        redisTemplate.opsForZSet().unionAndStore("zset-1", Arrays.asList("zset-2"), "zset-u", RedisZSetCommands.Aggregate.MIN);
    }

    @Test
    public void setValue() {
        SysUserVO vo = new SysUserVO().setUsername("admin");
        BoundValueOperations bound = redisTemplate.boundValueOps("com:nieat:modules:admin:admin");
        bound.set(vo);
    }
}
