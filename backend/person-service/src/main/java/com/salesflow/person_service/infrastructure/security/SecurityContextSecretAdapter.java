package com.salesflow.plan_service.infrastructure.security;

import com.salesflow.plan_service.domain.port.SecretAuthenticationPort.SecretAuthenticationPort;
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
