package com.ciel.pocket.user.service.security;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.*;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/8/24
 * @Comment
 */
public class CustomUserInfoTokenServices implements ResourceServerTokenServices {
    private static final String[] PRINCIPAL_KEYS = new String[] { "user", "username",
            "userid", "user_id", "login", "id", "name" };

    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();

    @Override
    public OAuth2Authentication loadAuthentication(String s) throws AuthenticationException, InvalidTokenException {
        Map<String, Object> map = getMap(this.userInfoEndpointUrl, s);
        if (map.containsKey("error")) {
            throw new InvalidTokenException(s);
        }
        return extractAuthentication(map);
    }

    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = getPrincipal(map);
        OAuth2Request request = getRequest(map);
        List<GrantedAuthority> authorities = this.authoritiesExtractor
                .extractAuthorities(map);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                principal, "N/A", authorities);
        token.setDetails(map);
        return new OAuth2Authentication(request, token);
    }

    private Object getPrincipal(Map<String, Object> map) {
        for (String key : PRINCIPAL_KEYS) {
            if (map.containsKey(key)) {
                return map.get(key);
            }
        }
        return "unknown";
    }

    @SuppressWarnings({ "unchecked" })
    private OAuth2Request getRequest(Map<String, Object> map) {
        Map<String, Object> request = (Map<String, Object>) map.get("oauth2Request");

        String clientId = (String) request.get("clientId");
        Set<String> scope = new LinkedHashSet<>(request.containsKey("scope") ?
                (Collection<String>) request.get("scope") : Collections.<String>emptySet());

        return new OAuth2Request(null, clientId, null, true, new HashSet<>(scope),
                null, null, null, null);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private final String clientId;

    private OAuth2RestOperations restTemplate;

    private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;

    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String userInfoEndpointUrl;

    public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.clientId = clientId;
    }

    @SuppressWarnings({ "unchecked" })
    private Map<String, Object> getMap(String path, String accessToken) {
        try {
            OAuth2RestOperations restTemplate = this.restTemplate;
            if (restTemplate == null) {
                BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
                resource.setClientId(this.clientId);
                restTemplate = new OAuth2RestTemplate(resource);
            }
            OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
                    .getAccessToken();
            if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
                        accessToken);
                token.setTokenType(this.tokenType);
                restTemplate.getOAuth2ClientContext().setAccessToken(token);
            }
            return restTemplate.getForEntity(path, Map.class).getBody();
        }
        catch (Exception ex) {
            return Collections.<String, Object>singletonMap("error",
                    "Could not fetch user details");
        }
    }
}
