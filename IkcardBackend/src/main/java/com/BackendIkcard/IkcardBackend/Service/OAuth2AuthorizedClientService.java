package com.BackendIkcard.IkcardBackend.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

public interface OAuth2AuthorizedClientService {
    <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName);

    void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal);

    void removeAuthorizedClient(String clientRegistrationId, String principalName);
}
