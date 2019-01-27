package com.sfs.pbserver.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenClearLogoutHandler implements LogoutHandler {

    private UserDetailsService userDetailsService;

    public TokenClearLogoutHandler(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

    }

    private void clearToken(Authentication authentication){
        if(authentication ==null)
            return;

        UserDetails user = (UserDetails)authentication.getPrincipal();

        // TODO: 2018/12/10
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
