package com.sfs.pbserver.vo;


import com.sfs.pbserver.security.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenVo {
    private String name;
    private List<String> authorities;

    public AuthenVo(String name, Collection<GrantedAuthorityImpl> grantedAuthoritieList) {
        this.name = name;
        this.authorities = new ArrayList<>();
        grantedAuthoritieList.stream().forEach((x)->{
            this.authorities.add(x.getAuthority());

        });
        //grantedAuthoritieList.stream().forEach(this.authorities::add);
    }

    public AuthenVo(String name, List<String> authorities) {
        this.name = name;
        this.authorities = authorities;
        //grantedAuthoritieList.stream().forEach(this.authorities::add);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "AuthenVo{" +
                "name='" + name + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
