package com.iu.acadia.microservice.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

public class CampaignUtil {

    @Value("${api.campaignservice.host}")
    private String campaignServiceHost;

    public void CreateCampaign(String token){
        try {
            String CreateCampaignURL = campaignServiceHost + "/campaigns";
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost();

            //Set Headers
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");
            post.setHeader("token", token);

            // Set Payload


            HttpResponse response = httpClient.execute(post) ;
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
