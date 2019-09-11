package cn.tianhan.security.auth.component;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/5 0005 下午 23:22
 */
@Component
public class ServerTokenEnhancer implements TokenEnhancer {
    /**
     * 添加自定义内容
     *
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        UserServer userInfo = (UserServer) oAuth2Authentication.getPrincipal();
        Map<String, Object> info = new HashMap<>(2);
        info.put("roles", userInfo.getRoles());
        info.put("nickname", userInfo.getNickname());
        accessToken.setAdditionalInformation(info);
        return accessToken;
    }
}
