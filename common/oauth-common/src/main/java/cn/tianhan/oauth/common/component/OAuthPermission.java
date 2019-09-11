package cn.tianhan.oauth.common.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.Collection;

@Slf4j
@Component("permit")
public class OAuthPermission {

    /**
     * 判断接口权限
     *
     * @param code
     * @return
     */
    public boolean hasAuthority(String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (StringUtils.isBlank(code) || authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> codes = (Collection<GrantedAuthority>) authentication.getAuthorities();
        return codes.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::isNotBlank)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(code, x));
    }
}
