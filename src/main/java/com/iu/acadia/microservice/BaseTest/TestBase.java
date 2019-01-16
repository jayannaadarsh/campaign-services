package com.iu.acadia.microservice.BaseTest;

import com.iu.acadia.microservice.service.ConnectorService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class TestBase {
    private static final Logger logger = Logger.getLogger(TestBase.class);

    public String token;
    public String OrgId;
    @Autowired
    ConnectorService connector;

    @Before
    public void testLogin() {

        try {

            String[] TokenOrgID = connector.login();
            token = TokenOrgID[0];
            OrgId = TokenOrgID[1];

            if (StringUtils.isEmpty(token)) {

                logger.info("Login failed");
            }

            logger.info("Logged in successfully with Token==" + token);


        } catch (IOException e) {

            logger.error(e.getMessage().toString());
        }


    }

    @After
    public  void logout()
    {
        String logoutURL = "https://sdkv2-uat.imaginationunwired.com:3001/auth/sessions";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpDelete delete = new HttpDelete(logoutURL);

        delete.setHeader("Content-type", "application/json");
        delete.setHeader("Accept", "application/json");
        delete.setHeader("token", token);

        try{
            HttpResponse response = httpClient.execute(delete);
            HttpEntity responseEntity = response.getEntity();
            String responseString = EntityUtils.toString(responseEntity, "UTF-8");
            System.out.println(responseString);

        }
        catch (Exception e){

            e.printStackTrace();

        }


    }
}


