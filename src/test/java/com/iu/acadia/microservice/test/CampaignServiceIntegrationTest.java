package com.iu.acadia.microservice.test;

import com.iu.acadia.microservice.service.ConnectorService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CampaignServiceIntegrationTest {

    private static final Logger logger = Logger.getLogger(CampaignServiceIntegrationTest.class);

    @Autowired
    ConnectorService connector;

    @Test
    public void testLogin() throws Exception{

        try {

            String token = connector.login();

            if(StringUtils.isEmpty(token)) {

                logger.info("Login failed");
            }

            logger.info("Logged in successfully with Token=="+token);


        } catch (IOException e) {

            logger.error(e.getMessage().toString());
        }

    }

}
