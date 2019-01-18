package com.iu.acadia.microservice.test;

import com.iu.acadia.microservice.BaseTest.TestBase;
import com.iu.acadia.microservice.utils.SetHeaders;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)

@SpringBootTest

public class campaignlist extends TestBase {


    @Value("${api.campaignservice.host}")
    private String campaignServiceHost;

    @Test
    public void CampaignList() {

        try {
            String campaignlistURL = campaignServiceHost + "/campaigns/v2/organizationid/" + OrgId;
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(campaignlistURL);

            //Set Headers
            SetHeaders header = new SetHeaders();
            header.GetHeaders(get,token);

            //Execute request
            HttpResponse response = httpClient.execute(get) ;
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }


}
