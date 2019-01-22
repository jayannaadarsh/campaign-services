package com.iu.acadia.microservice.test;

import com.iu.acadia.microservice.BaseTest.TestBase;
import com.iu.acadia.microservice.utils.HttpUtil;
import com.iu.acadia.microservice.utils.ReadFile;
import org.json.JSONObject;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


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

       //change name, start date, end date
        String newPayload=httpUtil.set_date_and_time(fileurl);



        try {

          JSONObject responseJson= httpUtil.proces_post_request(token, CreateCampaignURL, newPayload);
          //System.out.println(responseJson);
            //Data
            JSONObject data=responseJson.getJSONObject("data");

           // System.out.println("-------------data"+data);

            //ID
            String campaign_id= data.getString("id");

           // System.out.println(campaign_id);

            String campaignid=String.valueOf(campaign_id);
           // System.out.println(campaignid);

            String change_status_url =campaignServiceHost + "/campaigns/"+campaignid+"/status";

            String change_status_payload = "{\n" +
                    "  \"reason\": \"campaign ready for approval\",\n" +
                    "  \"status\": \"READY\"\n" +
                    "}";
           // System.out.println(change_status_payload);

            httpUtil.process_put_request(token,change_status_url,change_status_payload);

        }
        catch (Exception e){

        }

    }

}
