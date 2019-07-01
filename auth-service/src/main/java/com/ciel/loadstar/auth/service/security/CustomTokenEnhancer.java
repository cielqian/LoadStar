package com.ciel.loadstar.auth.service.security;

import com.ciel.loadstar.auth.entity.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/3/29 11:22
 */

public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();

        User principal = (User)oAuth2Authentication.getPrincipal();

        additionalInfo.put(
                "id", principal.getId());

        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) oAuth2AccessToken;

        defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);

        Long oneDay = 1000 * 60 * 60 *  24L;

        defaultOAuth2AccessToken.setExpiration(new Date(System.currentTimeMillis() + oneDay * 7));
        return oAuth2AccessToken;
    }
}
