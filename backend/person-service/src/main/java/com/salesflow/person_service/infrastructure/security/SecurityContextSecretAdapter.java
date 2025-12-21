package com.salesflow.person_service.infrastructure.security;

import com.salesflow.person_service.application.porters.out.SecretAuthenticationPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
public class SecurityContextSecretAdapter implements SecretAuthenticationPort {

    @Override
    public boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated();
    }
}
