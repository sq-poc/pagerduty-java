package biz.neustar.pagerduty.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.junit.Test;

public class TokenAuthSchemeTest {

    @Test
    public void testAuthenticateCredentialsHttpRequestHttpContext() throws AuthenticationException {
        TokenAuthScheme scheme = new TokenAuthScheme();
        TokenAuthCredentials creds = new TokenAuthCredentials("token_here");
        HttpRequest request = new HttpGet();
        HttpContext context = new BasicHttpContext();
        
        Header header = scheme.authenticate(creds, request, context);
        
        assertNotNull(header);
        assertEquals("Authorization: Token token=token_here", header.toString());
    }

}
