package cn.tianhan.security.auth.service.impl;

import cn.tianhan.security.auth.feign.UserServiceFeign;
import cn.tianhan.security.auth.vo.UserVO;
import cn.tianhan.security.auth.component.UserServer;
import cn.tianhan.security.auth.service.IUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 16:58 2019/3/20
 */
@Service("userDetailService")
@AllArgsConstructor
public class UserDetailServiceImpl implements IUserDetailService {
    private UserServiceFeign userServiceFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVO = userServiceFeign.validator(username);
        if (userVO == null) throw new UsernameNotFoundException(username);
        List<SimpleGrantedAuthority> authorityList = userVO.getAuthorities()
                .stream().map(permit -> new SimpleGrantedAuthority(permit)).collect(Collectors.toList());
        return new UserServer(userVO.getUsername(), userVO.getPassword(), authorityList
                , userVO.getNickname(), userVO.getRoles());
    }
}
