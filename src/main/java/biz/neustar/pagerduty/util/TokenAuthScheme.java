package biz.neustar.pagerduty.util;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.impl.auth.RFC2617Scheme;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

public class TokenAuthScheme extends RFC2617Scheme {
    public static final String TOKEN_HEADER_PREAMBLE = "Token token=";
    private boolean complete;
    
    public TokenAuthScheme() {
        this.complete = false;
    }

    @Override
    public Header authenticate(Credentials creds, HttpRequest request)
            throws AuthenticationException {
        return authenticate(creds, request, new BasicHttpContext());
    }

    @Override
    public String getSchemeName() {
        return "token";
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean isConnectionBased() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.apache.http.impl.auth.AuthSchemeBase#authenticate(org.apache.http.auth.Credentials, org.apache.http.HttpRequest, org.apache.http.protocol.HttpContext)
     */
    @Override
    public Header authenticate(Credentials credentials, HttpRequest request,
            HttpContext context) throws AuthenticationException {
        if (credentials instanceof TokenAuthCredentials) {
            TokenAuthCredentials tokenCredentials = (TokenAuthCredentials)credentials;
            CharArrayBuffer buffer = new CharArrayBuffer(32);
            buffer.append(TOKEN_HEADER_PREAMBLE);
            buffer.append(tokenCredentials.getPassword());

            return new BufferedHeader(buffer);
        } else {
            return super.authenticate(credentials, request, context);
        }
    }

}
