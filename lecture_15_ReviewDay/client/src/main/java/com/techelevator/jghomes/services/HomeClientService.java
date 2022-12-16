package com.techelevator.jghomes.services;

import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.model.Home;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class HomeClientService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();



    /**
     * Retrieve a home based off of its mlsId
     */
    public Home getHomeByMLSID(String mlsId) {
        //TODO
        Home home = new Home();

        try {
            home = restTemplate.getForObject(API_BASE_URL + "homes/" + mlsId, Home.class);
        } catch (RestClientResponseException e){
            e.printStackTrace();
        }
        return home;
    }


    /**
     * Retrieve a List of all homes
     * (hint: web service returns an array of homes... how could you convert it to an ArrayList so you don't have to change
     *  the return type to a Home[]? - If you don't know, ask Google... ie. Don't change return type to an Home[])
     */
    public List<Home> retrieveAllHomes() {
         //TODO
        Home[] homes = null;

        try {
            homes = restTemplate.getForObject(API_BASE_URL + "homes/", Home[].class);
        } catch (RestClientResponseException e){
            e.printStackTrace();
        }

        List<Home> homes1;
        homes1 = Arrays.asList(homes); //this converts the homes ARRAY into a LIST called homes1.
        return homes1;
    }



    public Home addHome(Home newHome) {
        //TODO
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // sets the header's application type to JSON
        HttpEntity<Home> entity = new HttpEntity<>(newHome, headers); // entity is a wrapper object that represents
                                                            // the HTTP packet request
        // new Home will get placed in the message body...
        Home returnedHome = null;
        //localhost:8080/homes

        try {
            returnedHome = restTemplate.postForObject(API_BASE_URL+ "homes", entity, Home.class);
        } catch (RestClientResponseException e) {
            e.printStackTrace(); //handles exceptions thrown by restTemplate and prints them on stacktrace
        }

        return returnedHome;
    }


    /**
     * Delete an existing home
     */
    public boolean deleteHome(String mlsId) throws ServiceException {
        //TODO

        boolean success = false;
        try{
            restTemplate. delete(API_BASE_URL + "homes/", mlsId);
            success = true;
        } catch(RestClientResponseException e){
            //handles exception thrown by restTemplate and prints them on StackTrace
            e.printStackTrace();
        }
        return success;
    }



}
