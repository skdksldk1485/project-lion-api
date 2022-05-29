package com.shop.projectlion.global.config.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String modifiedBy = "";
        if(authentication instanceof AnonymousAuthenticationToken) {
            modifiedBy = httpServletRequest.getRequestURI();
        } else {
            modifiedBy = authentication.getName();
        }
        return Optional.of(modifiedBy);
    }

}