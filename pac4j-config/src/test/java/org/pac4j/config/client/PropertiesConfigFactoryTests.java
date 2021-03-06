package org.pac4j.config.client;

import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import org.junit.Test;
import org.pac4j.cas.client.CasClient;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.util.TestsConstants;
import org.pac4j.oauth.client.FacebookClient;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.saml.client.SAML2Client;

import java.util.HashMap;
import java.util.Map;

import static org.pac4j.config.client.PropertiesConfigFactory.*;
import static org.junit.Assert.*;

/**
 * Tests {@link PropertiesConfigFactory}.
 *
 * @author Jerome Leleu
 * @since 1.8.1
 */
public final class PropertiesConfigFactoryTests implements TestsConstants {

    @Test
    public void test() {
        final Map<String, String> properties = new HashMap<>();
        properties.put(FACEBOOK_ID, ID);
        properties.put(FACEBOOK_SECRET, SECRET);
        properties.put(TWITTER_ID, ID);
        properties.put(TWITTER_SECRET, SECRET);
        properties.put(CAS_LOGIN_URL, CALLBACK_URL);
        properties.put(CAS_PROTOCOL, CasClient.CasProtocol.CAS20.toString());
        properties.put(SAML_KEYSTORE_PASSWORD, PASSWORD);
        properties.put(SAML_PRIVATE_KEY_PASSWORD, PASSWORD);
        properties.put(SAML_KEYSTORE_PATH, PATH);
        properties.put(SAML_IDENTITY_PROVIDER_METADATA_PATH, PATH);
        properties.put(OIDC_ID, ID);
        properties.put(OIDC_SECRET, SECRET);
        properties.put(OIDC_DISCOVERY_URI, CALLBACK_URL);
        properties.put(OIDC_USE_NONCE, "true");
        properties.put(OIDC_PREFERRED_JWS_ALGORITHM, "RS384");
        properties.put(OIDC_MAX_CLOCK_SKEW, "60");
        properties.put(OIDC_CLIENT_AUTHENTICATION_METHOD, "CLIENT_SECRET_POST");
        properties.put(OIDC_CUSTOM_PARAM_KEY1, KEY);
        properties.put(OIDC_CUSTOM_PARAM_VALUE1, VALUE);
        final PropertiesConfigFactory factory = new PropertiesConfigFactory(CALLBACK_URL, properties);
        final Config config = factory.build();
        final Clients clients = config.getClients();
        assertEquals(5, clients.getClients().size());
        final FacebookClient fbClient = (FacebookClient) clients.findClient("FacebookClient");
        assertEquals(ID, fbClient.getKey());
        assertEquals(SECRET, fbClient.getSecret());
        final TwitterClient twClient = (TwitterClient) clients.findClient("TwitterClient");
        assertEquals(ID, twClient.getKey());
        assertEquals(SECRET, twClient.getSecret());
        final CasClient casClient = (CasClient) clients.findClient("CasClient");
        assertEquals(CALLBACK_URL, casClient.getCasLoginUrl());
        assertEquals(CasClient.CasProtocol.CAS20, casClient.getCasProtocol());
        final SAML2Client saml2client = (SAML2Client) clients.findClient("SAML2Client");
        assertNotNull(saml2client);
        final OidcClient oidcClient = (OidcClient) clients.findClient("OidcClient");
        assertNotNull(oidcClient);
        assertEquals(ClientAuthenticationMethod.CLIENT_SECRET_POST.toString(), oidcClient.getClientAuthenticationMethod().toString().toLowerCase());
    }
}
