package com.iu.acadia.microservice.utils;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {

    public String read_JSON_file(String FilePath){
        String payload = "";

       // String fileurl = "D:\\CampaignServiceTest\\src\\main\\resources\\Call_Based_campaign_payload.json";
        try {
            FileInputStream file = new FileInputStream(FilePath);
             payload = new String(Files.readAllBytes(Paths.get(FilePath)));
            System.out.println(payload);
        }

        catch(Exception e){
            e.printStackTrace();
        }
        return payload;

    }

}
