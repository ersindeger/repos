package com.techelevator.jghomes.services;

import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.model.Home;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class HomeClientService {


    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public HomeClientService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Retrieve a home based off of its mlsId
     */
    public Home getHomeByMLSID(String mlsId) throws ServiceException {

        Home home = null;
        try {
            home = restTemplate.getForObject(baseUrl + "homes/" + mlsId, Home.class);
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
            homes = restTemplate.getForObject(baseUrl + "homes", Home[].class);
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

        Home returnedHome = null;
        try {
            returnedHome = restTemplate.postForObject(baseUrl + "/homes", makeHomeEntity(newHome), Home.class);
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
            restTemplate.exchange(baseUrl + "/homes/" + mlsId, HttpMethod.DELETE, makeAuthEntity(), Void.class);
            return true;
        } catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        } catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + ex1.getMessage());
        }

    }

    /**
     * Creates a new HttpEntity with the `Authorization: Bearer:` header and a home request body
     */
    private HttpEntity<Home> makeHomeEntity(Home home) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(home, headers);
    }

    /**
     * Returns an HttpEntity with the `Authorization: Bearer:` header
     */
    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }



}
