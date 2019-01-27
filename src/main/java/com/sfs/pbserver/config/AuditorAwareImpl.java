package com.sfs.pbserver.config;

import com.sfs.pbserver.security.JwtUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object object = authentication.getPrincipal();
        if(object instanceof String)
            return Optional.of((String)object);
        JwtUserDetails currentUser = (JwtUserDetails) object;
        return Optional.of(currentUser.getUsername());
    }
}

