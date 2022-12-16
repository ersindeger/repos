package com.techelevator.jghomes.services;

import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.exceptions.ServiceException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.rmi.ServerException;
import java.util.Arrays;
import java.util.List;


public class HomeClientService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();



    /**
     * Retrieve a home based off of its mlsId
     */
    public Home getHomeByMLSID(String mlsId) throws ServiceException {

        Home home = null;
        try {
            home = restTemplate.getForObject(API_BASE_URL + "homes/" + mlsId, Home.class);
        }
        catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        }
        catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + " " + ex1.getStatusText());
        }
        return home;
    }

    /**
     * Retrieve a List of all homes
     * (hint: web service returns an array of homes... how could you convert it to an ArrayList so you don't have to change
     *  the return type to a Home[]? - If you don't know, ask Google... ie. Don't change return type to an Home[])
     */
    public List<Home> retrieveAllHomes() throws ServiceException{

        Home[] homes = null;
        try {
            homes = restTemplate.getForObject(API_BASE_URL + "homes", Home[].class);
        }
        catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        }
        catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + " " + ex1.getStatusText());
        }

        //convert array into a List
        return  Arrays.asList(homes);

    }

    public Home addHome(Home newHome) throws ServiceException{

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Home> entity = new HttpEntity<Home>(newHome, header);

        Home returnedHome = null;
        try {
             returnedHome = restTemplate.postForObject(API_BASE_URL + "/homes", entity, Home.class);
        }
        catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        }
        catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + " " + ex1.getStatusText());
        }

        return returnedHome;
    }

    /**
     * Delete an existing home
     */
    public boolean deleteHome(String mlsId) throws ServiceException {

        try {
            restTemplate.delete(API_BASE_URL + "/homes/" + mlsId);
            return true;
        } catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        } catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + ex1.getStatusText());
        }


    }

}
