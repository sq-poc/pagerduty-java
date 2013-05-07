package biz.neustar.pagerduty.util;

import java.security.Principal;

import org.apache.http.auth.Credentials;

public class TokenAuthCredentials implements Credentials {
    private final String token;
    
    public TokenAuthCredentials(final String token) {
        this.token = token;
    }

    @Override
    public String getPassword() {
        return token;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

}
