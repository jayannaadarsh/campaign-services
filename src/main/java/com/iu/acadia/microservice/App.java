package com.iu.acadia.microservice;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Vinutha on 10/12/2018.
 */
@SpringBootApplication
public class App  {

	private static final Logger logger = Logger.getLogger(App.class);

    public static void main( String[] args ) {
    	/**
    	 * run Connector App using Spring boot run
    	 */
        SpringApplication.run(App.class, args);
    }

}
