import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientAuthentication2 {
    public static void main(String... args) throws IOException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        List<String> authpref = new ArrayList<String>();
        authpref.add(AuthPolicy.NTLM);
        httpclient.getParams().setParameter(AuthPNames.TARGET_AUTH_PREF, authpref);
        NTCredentials creds = new NTCredentials("abhisheks", "abhiProJul17", "", "");
        httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
        HttpHost target = new HttpHost("apps.prorigo.com", 80, "http");

        // Make sure the same context is used to execute logically related requests
        HttpContext localContext = new BasicHttpContext();
        // Execute a cheap method first. This will trigger NTLM authentication
        HttpGet httpget = new HttpGet("/conference/Booking");
        HttpResponse response = httpclient.execute(target, httpget, localContext);
        HttpEntity entity = response.getEntity();
        System.out.println(EntityUtils.toString(entity));
    }
}