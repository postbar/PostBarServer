package com.sfs.pbserver.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义令牌对象
 * @author Louis
 * @date Nov 21, 2018
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationToken(Object principal, Object credentials){
        super(principal, credentials);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, String token){
        super(principal, credentials);
        this.token = token;
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Collection<? extends GrantedAuthority> getAuthoritiesString() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}

