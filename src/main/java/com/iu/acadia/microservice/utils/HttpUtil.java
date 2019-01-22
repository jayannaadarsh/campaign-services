package com.iu.acadia.microservice.utils;

import ch.qos.logback.core.net.server.Client;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.HTTP;
import org.json.JSONArray;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Vinutha on 7/12/18.
 */
@Component
public class HttpUtil {

    JSONObject responseJson;

    @Value("${api.campaignservice.host}")
    private String campaignServiceHost;

    @Value("${api.campaignservice.login}")
    private String loginApi;

    @Value("${api.campaignservice.changesessionorganization}")
    private String changeSessionOrganizationApi;

    @Value("${api.campaignservice.listorganizations}")
    private String listOrganizationsApi;

    private static final Logger logger = Logger.getLogger(HttpUtil.class);

    public String login(String email, String password) {

        String token = "";

        try {

            String loginUrl = campaignServiceHost + loginApi;

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(loginUrl);

            //Set header
            post.setHeader("Content-type", "application/json");
            post.setHeader("Accept", "application/json");

            //Constructing request body to create empty Cluster
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);

            StringEntity entity = new StringEntity(jsonBody.toString());

            //Set to request body
            post.setEntity(entity) ;

            //Send request
            HttpResponse response = httpClient.execute(post) ;

            /*
            Creating a JSON object to hold the response.
             */
            JSONObject responseObject = null;

            //Verify response if any
            if (response != null) {

                System.out.println(response.getStatusLine().getStatusCode());

                /*
                Getting the response and converting it into a JSON object.
                 */
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");

                responseObject = new JSONObject(responseString);

                JSONObject dataObject = null;

                if (responseObject.has("status") && responseObject.get("status") != null) {

                    if (responseObject.getString("status").equals("0") &&
                            response.getStatusLine().getStatusCode() == 200) {

                        dataObject = responseObject.getJSONObject("data");

                        token = dataObject.getString("token");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    public JSONArray listOrganizations(String token) {

        JSONArray dataArray = null;

        try {

            String listOrganizationsUrl = campaignServiceHost + listOrganizationsApi;

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(listOrganizationsUrl);

            //Set header
            get.setHeader("Content-type", "application/json");
            get.setHeader("Accept", "application/json");
            get.setHeader("token", token);

            //Send request
            HttpResponse response = httpClient.execute(get) ;

            /*
            Creating a JSON object to hold the response.
             */
            JSONObject responseObject = null;

            //Verify response if any
            if (response != null) {

                System.out.println(response.getStatusLine().getStatusCode());

                /*
                Getting the response and converting it into a JSON object.
                 */
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");

                responseObject = new JSONObject(responseString);


                if (responseObject.has("status") && responseObject.get("status") != null) {

                    if (responseObject.getString("status").equals("0") &&
                            response.getStatusLine().getStatusCode() == 200) {

                        dataArray = responseObject.getJSONArray("data");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataArray;
    }

    public String changeSessionOrganization(String token, String organizationId) {

        String newToken = "";
        try {

            String changeSessionOrganizationUrl = campaignServiceHost+changeSessionOrganizationApi+"/"+organizationId;

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(changeSessionOrganizationUrl);

            //Set header
            put.setHeader("Content-type", "application/json");
            put.setHeader("Accept", "application/json");
            put.setHeader("token", token);

            //Send request
            HttpResponse response = httpClient.execute(put) ;

            /*
            Creating a JSON object to hold the response.
             */
            JSONObject responseObject = null;

            //Verify response if any
            if (response != null) {

                System.out.println(response.getStatusLine().getStatusCode());

                /*
                Getting the response and converting it into a JSON object.
                 */
                HttpEntity responseEntity = response.getEntity();
                String responseString = EntityUtils.toString(responseEntity, "UTF-8");

                responseObject = new JSONObject(responseString);

                JSONObject dataObject = null;

                if (responseObject.has("status") && responseObject.get("status") != null) {

                    if (responseObject.getString("status").equals("0") &&
                            response.getStatusLine().getStatusCode() == 200) {

                        dataObject = responseObject.getJSONObject("data");

                        newToken = dataObject.getString("token");
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newToken;
    }



    public String set_date_and_time (String filepath){
        //name uniqueness
        Date date = new Date();
        String name="Call based Rich notification "+date.toString();
        //Start Date
        SimpleDateFormat sdf= new SimpleDateFormat("yyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String startdate = sdf.format(cal.getTime());
       // System.out.println("StartDate:"+startdate);

        //End and Expiration date
        cal.add(Calendar.DAY_OF_MONTH,7);
        String enddate = sdf.format(cal.getTime());
       // System.out.println(enddate);

        ReadFile fileReader = new ReadFile();
        String payload=fileReader.read_JSON_file(filepath);

        //replace name
        String name_payload= payload.replace("Call based Rich notification",name);
        String startdate_payload = name_payload.replace("2019-01-17",startdate);
        String enddate_payload= startdate_payload.replace("2019-01-18",enddate);
        String final_payload=enddate_payload;

        return final_payload;


    }
    public JSONObject proces_post_request(String token, String URI , String payload){
        try {
            // String CreateCampaignURL =  "http://18.217.149.21:8080/campaigns";
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(URI);

            //Set Headers
            SetHeaders headers = new SetHeaders();
            headers.PostHeaders(post, token);


            StringEntity entity = new StringEntity(payload);

            post.setEntity(entity);



            CloseableHttpResponse closablehttpresponse = httpClient.execute(post);// hit the url

            int statuscode= closablehttpresponse.getStatusLine().getStatusCode();
            System.out.println(statuscode);


            String responsestring= EntityUtils.toString(closablehttpresponse.getEntity(), "UTF-8");
            responseJson= new JSONObject(responsestring);
            //System.out.println(responseJson);




        }

        catch (Exception e) {
            e.printStackTrace();
        }


        return responseJson;

    }


    public void process_put_request(String token, String URI , String payload){

        try{
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPut put = new HttpPut(URI);

            //Set Headers
            SetHeaders headers = new SetHeaders();
            headers.PutHeaders(put,token);

            StringEntity entity = new StringEntity(payload);
            put.setEntity(entity);

            CloseableHttpResponse closablehttpresponse = httpClient.execute(put);

           int status= closablehttpresponse.getStatusLine().getStatusCode();
           System.out.println(status);

            String responsestring= EntityUtils.toString(closablehttpresponse.getEntity(), "UTF-8");
            responseJson= new JSONObject(responsestring);
            System.out.println(responseJson);



        }
        catch (Exception e){

        }

    }




}
