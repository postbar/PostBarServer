package com.sfs.pbserver.vo;

import com.sfs.pbserver.security.JwtAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class TokenVo {
    private String name;
    private String token;
    private List<String> authorities;

    public TokenVo(JwtAuthenticationToken jwtAuthenticationToken) {
        this.token = jwtAuthenticationToken.getToken();
        this.authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority: jwtAuthenticationToken.getAuthoritiesString()){
            authorities.add(grantedAuthority.getAuthority());
        }
        //this.name = jwtAuthenticationToken.getName();
    }
    public TokenVo(JwtAuthenticationToken jwtAuthenticationToken,String name) {
        this.token = jwtAuthenticationToken.getToken();
        this.authorities = new ArrayList<String>();
        for (GrantedAuthority grantedAuthority: jwtAuthenticationToken.getAuthoritiesString()){
            authorities.add(grantedAuthority.getAuthority());
        }
        this.name = name;
        //this.name = jwtAuthenticationToken.getName();
    }

    public TokenVo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        return "TokenVo{" +
                "token='" + token + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
