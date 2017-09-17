import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientAuthentication {
/*    public static void main(String[] args) throws Exception {
        String host = "http://apps.prorigo.com";
        int port = 80;
        String username = "abhisheks";
        String password = "abhiProJul17";
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(host, port),
                new UsernamePasswordCredentials(username, password));
        final BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .setDefaultCookieStore(cookieStore)
                .build();
        try {
            String urlToQuery ="/conference";
            HttpGet httpget = new HttpGet(urlToQuery);
            ResponseHandler<String> responseHandler = new MyResponseHandler<String>(cookieStore);
            httpclient.execute(httpget, responseHandler);
        } finally {
            httpclient.close();
        }
    }

    static class MyResponseHandler<T> implements ResponseHandler<T>{
        CookieStore cookieStore;

        MyResponseHandler(CookieStore cookieStore){
            this.cookieStore = cookieStore;
        }

        public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("- " + cookies.get(i).toString());
                }
            }
            return null;
        }
    }*/


  /*  public static void main(String[] args) throws Exception {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("apps.prorigo.com", 80),
                new UsernamePasswordCredentials("abhisheks", "abhiProJul17"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        try {
            HttpGet httpget = new HttpGet("http://apps.prorigo.com/conference");

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }*/

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