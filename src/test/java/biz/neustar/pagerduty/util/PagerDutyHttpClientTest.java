package biz.neustar.pagerduty.util;

import static org.junit.Assert.*;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

public class PagerDutyHttpClientTest {

    @Test
    public void testBasicAuthConstructor() {
        AuthScope authScope = new AuthScope("example.pagerduty.com", 443);
        PagerDutyHttpClient client = new PagerDutyHttpClient("example", "user", "pass");
        CredentialsProvider provider = client.createCredentialsProvider();
        assertNotNull(provider);
        
        Credentials creds = provider.getCredentials(authScope);
        assertNotNull(creds);
        assertEquals("pass", creds.getPassword());
    }
    
    @Test
    public void testTokenAuthConstructor() {
        AuthScope authScope = new AuthScope("example.pagerduty.com", 443);
        PagerDutyHttpClient client = new PagerDutyHttpClient("example", "token");
        CredentialsProvider provider = client.createCredentialsProvider();
        assertNotNull(provider);
        
        Credentials creds = provider.getCredentials(authScope);
        assertNotNull(creds);
        assertEquals("token", creds.getPassword());
    }
    
    @Test
    public void testCreateHttpContextTokenAuth() {
        AuthScope authScope = new AuthScope("example.pagerduty.com", 443);
        PagerDutyHttpClient client = new PagerDutyHttpClient("example", "token");
        HttpContext httpContext = client.createHttpContext();
        assertNotNull(httpContext);
        
        AuthState state = (AuthState)httpContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
        
        assertNotNull(state.getAuthScheme());
        assertTrue(state.getAuthScheme() instanceof TokenAuthScheme);
        
        assertNotNull(state.getAuthScope());
        assertEquals(authScope, state.getAuthScope());
        
        assertNotNull(state.getCredentials());
        assertTrue(state.getCredentials() instanceof TokenAuthCredentials);
        TokenAuthCredentials tokenCreds = (TokenAuthCredentials)state.getCredentials();
        assertEquals("token", tokenCreds.getPassword());
    }

}
