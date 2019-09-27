package com.nari.guangzhou.oauth2.security.oauth2;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

/**
 * ClientDetails的具体实现
 *
 * @author Zongwei
 * @date 2019/9/27 14:26
 */
@Setter
public class PlatformClientDetails implements ClientDetails {

    private String appId;
    private String secret;
    private String role;
    private Set<String> scope;
    private Set<String> types;
    private Set<String> autoApproveScopes;
    private Set<String> resourceIds;
    private Map<String, Object> additionalInformation;
    private Set<String> registeredRedirectUris;
    private List<GrantedAuthority> authorities;

    @Override
    public String getClientId() {
        return appId;
    }

    @Override
    public String getClientSecret() {
        return secret;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * values ["authorization_code", "password", "refresh_token", "implicit"]
     *
     * @return
     */
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.types;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 7200;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.registeredRedirectUris;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    /**
     * values ["read", "write"]
     *
     * @return
     */
    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (this.autoApproveScopes == null) {
            return false;
        } else {
            Iterator var2 = this.autoApproveScopes.iterator();
            String auto;
            do {
                if (!var2.hasNext()) {
                    return false;
                }
                auto = (String) var2.next();
            } while (!auto.equals("true") && !scope.matches(auto));
            return true;
        }
    }

    @Override
    public boolean isSecretRequired() {
        return this.secret != null;
    }

    @Override
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.types = authorizedGrantTypes;
    }
}
