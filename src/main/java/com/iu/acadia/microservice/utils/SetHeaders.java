package com.iu.acadia.microservice.utils;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;

public class SetHeaders {

    public void PostHeaders(HttpPost post, String token)
    {
        post.setHeader("Content-type", "application/json");
        post.setHeader("Accept", "application/json");
        post.setHeader("token", token);

    }


    public  void GetHeaders(HttpGet get, String token){

        get.setHeader("Content-type", "application/json");
        get.setHeader("Accept", "application/json");
        get.setHeader("token", token);

    }

    public  void PutHeaders(HttpPut put, String token){

        put.setHeader("Content-type", "application/json");
        put.setHeader("Accept", "application/json");
        put.setHeader("token", token);

    }
}

