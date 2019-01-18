package com.iu.acadia.microservice.test;

import com.iu.acadia.microservice.BaseTest.TestBase;
import com.iu.acadia.microservice.utils.HttpUtil;
import com.iu.acadia.microservice.utils.ReadFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)

@SpringBootTest
public class CreateCampaignTest extends TestBase {
    @Value("${api.campaignservice.host}")
    private String campaignServiceHost;

    @Autowired
    HttpUtil httpUtil;


    @Test
    public void createcampaign() throws IOException {
        String fileurl = "D:\\CampaignServiceTest\\src\\main\\resources\\Call_Based_campaign_payload.json";
        String CreateCampaignURL =  campaignServiceHost+"/campaigns";

        //read from .json file
        ReadFile fileReader = new ReadFile();
        String payload=fileReader.read_JSON_file(fileurl);

        try {

            httpUtil.proces_post_request(token, CreateCampaignURL, payload);

        }
        catch (Exception e){

        }

    }

}
