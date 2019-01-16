package com.iu.acadia.microservice.service;

import com.iu.acadia.microservice.utils.HttpUtil;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class ConnectorService {

    private static final Logger logger = Logger.getLogger(ConnectorService.class);

    @Autowired
    HttpUtil httpUtil;

    @Value("${iu.admin.email}")
    private String email;

    @Value("${iu.admin.password}")
    private String password;

    public String login() throws IOException {

        /*
        Login by passing email and password and get the partial token
         */
        String token = httpUtil.login(email, password);

        JSONArray orgJsonArray = null;

        /*
        Call list organization api to list the organizations
         */
        if(!StringUtils.isEmpty(token)) {

            orgJsonArray = httpUtil.listOrganizations(token);
        }

        /*
        Iterate organizations and get IU organizationId if exists
         */
        String organizationId = "";

        if(orgJsonArray != null & orgJsonArray.length() > 0) {

            for (int i = 0; i < orgJsonArray.length(); i++) {

                JSONObject orgObject = orgJsonArray.getJSONObject(i);
                if(orgObject != null && orgObject.has("name") &&
                        orgObject.getString("name").equalsIgnoreCase("IU") ) {

                    organizationId = orgObject.getString("id");
                    break;
                }

            }
        }

        /*
        call change session organization and get token
         */
        if(!StringUtils.isEmpty(organizationId)) {
            token = httpUtil.changeSessionOrganization(token, organizationId);
        }

        return token;

    }

}