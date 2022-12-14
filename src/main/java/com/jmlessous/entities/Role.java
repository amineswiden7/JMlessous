package com.jmlessous.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    CLIENT,
    CONSEILLER_CLIENTELE,
    GESTIONNAIRE_CLIENTELE,
    MEMBRE_DIRECTOIRE,
    AGENT_RH,
    AGENT_MARKETING,
    FINANCIER,
    EMPLOYE_ASSURANCE;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
