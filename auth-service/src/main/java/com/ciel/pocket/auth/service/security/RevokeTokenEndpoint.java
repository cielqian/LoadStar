package com.ciel.pocket.auth.service.security;

import com.ciel.pocket.auth.dto.output.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.*;
import uk.co.caeldev.springsecuritymongo.MongoTokenStore;

@FrameworkEndpoint
public class RevokeTokenEndpoint {
    @Autowired
    MongoTokenStore mongoTokenStore;

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public ReturnModel revokeToken(@RequestParam String accessToken) {
        OAuth2RefreshToken refreshToken = mongoTokenStore.readRefreshToken(accessToken);
        mongoTokenStore.removeAccessToken(new DefaultOAuth2AccessToken(accessToken));
        mongoTokenStore.removeRefreshToken(refreshToken);
        return ReturnModel.OK();
    }
}
