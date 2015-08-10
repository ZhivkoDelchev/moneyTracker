package com.jako.moneytracker.rest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jako on 13.4.2015 ã..
 */
public class RestRequestsTest {

    @Test
    public void createCategory() {
        String host = "127.0.0.1";
        int port = 8080;
        String protocol = "http";

        DefaultHttpClient client = new DefaultHttpClient();

        try {
            HttpHost httpHost = new HttpHost(host, port, protocol);
            client.getParams().setParameter(ClientPNames.DEFAULT_HOST, httpHost);

            HttpEntityEnclosingRequestBase securedResource = new HttpPost("/tracker/rest/category/firstRestCategory");
            org.apache.http.HttpResponse httpResponse = client.execute(securedResource);
            HttpEntity responseEntity = httpResponse.getEntity();
            String strResponse = EntityUtils.toString(responseEntity);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            EntityUtils.consume(responseEntity);

            System.out.println("Http status code for Unauthenticated Request: " + statusCode);// Statue code should be 200
            System.out.println("Response for Unauthenticated Request: \n" + strResponse); // Should be login page
            System.out.println("================================================================\n");

            HttpPost authpost = new HttpPost("/tracker/j_security_check");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("j_username", "jako"));
            nameValuePairs.add(new BasicNameValuePair("j_password", "123"));
            authpost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            httpResponse = client.execute(authpost);
            responseEntity = httpResponse.getEntity();
            strResponse = EntityUtils.toString(responseEntity);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            EntityUtils.consume(responseEntity);

            System.out.println("Http status code for Authenticattion Request: " + statusCode);// Status code should be 302
            System.out.println("Response for Authenticattion Request: \n" + strResponse); // Should be blank string
            System.out.println("================================================================\n");

            httpResponse = client.execute(securedResource);
            responseEntity = httpResponse.getEntity();
            strResponse = EntityUtils.toString(responseEntity);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            EntityUtils.consume(responseEntity);

            System.out.println("Http status code for Authenticated Request: " + statusCode);// Status code should be 200
            System.out.println("Response for Authenticated Request: \n" + strResponse);// Should be actual page
            System.out.println("================================================================\n");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
