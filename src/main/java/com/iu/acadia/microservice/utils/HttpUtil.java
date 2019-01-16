package com.iu.acadia.microservice.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.json.JSONObject;

/**
 * Created by Vinutha on 7/12/18.
 */
@Component
public class HttpUtil {

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

}
