package cn.tianhan.oauth.common.component;

import cn.tianhan.oauth.common.utils.UserPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

import java.util.*;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/3 0003 下午 15:14
 */
public class ClientAccessTokenConverter extends DefaultUserAuthenticationConverter {
    /**
     * 自定义资源服务器User内容
     *
     * @param map
     * @return
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey("user_name")) {
            List<String> authorities = getAuthorities(map);
            UserPrincipal client = new UserPrincipal()
                    .setUsername((String) map.get("user_name"))
                    .setNickname((String) map.get("nickname"))
                    .setRoles((List<String>) map.get("roles"))
                    .setAuthorities(authorities);
            return new UsernamePasswordAuthenticationToken(client, "N/A", AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()])));
        } else {
            return null;
        }
    }

    private List<String> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey("authorities")) {
            return new ArrayList<>();
        } else {
            Object authorities = map.get("authorities");
            if (authorities instanceof String) {
                return Arrays.asList((String) authorities);
            } else if (authorities instanceof Collection) {
                return (List) authorities;
            } else {
                throw new IllegalArgumentException("Authorities must be either a String or a Collection");
            }
        }
    }
}
