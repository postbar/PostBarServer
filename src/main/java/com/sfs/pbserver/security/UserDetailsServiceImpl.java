package com.sfs.pbserver.security;


import com.sfs.pbserver.entity.User;
import com.sfs.pbserver.entity.UserRole;
import com.sfs.pbserver.repo.UserRepo;
import com.sfs.pbserver.repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户登录认证信息查询
 * @author Louis
 * @date Nov 20, 2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserRoleRepo userRoleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        System.out.println(user);

        // 用户权限列表，根据用户拥有的权限标识与如 @PreAuthorize("hasAuthority('sys:menu:view')") 标注的接口对比，决定是否可以调用接口
        List<UserRole> adminRoleList = userRoleRepo.findUserRoleListByUserEmail(user.getEmail());
        Set<String> permissions = new HashSet<>();
        for (UserRole adminRole:adminRoleList){
            permissions.add(adminRole.getRole().getName());
        }

        System.out.println(permissions);

        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());

        System.out.println(String.format("grantedAuthorities=%s",grantedAuthorities));

        return new JwtUserDetails(user.getEmail(), user.getPassword(), user.getSalt(), grantedAuthorities);
    }

}
