import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/1 0001 上午 11:25
 */
public class CaptchaTest {
    @Test
    public void test() {
        System.out.println(new String(DigestUtils.md5("okls" + "zlztf" )));
        System.out.println(new String(DigestUtils.md5("okls" + "zlztf")));
    }
}
