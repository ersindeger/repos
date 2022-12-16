package com.techelevator.jghomes.services;

import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.model.Home;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class HomeClientService {


    private final String baseUrl = "localhost:8080/homes/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


//    public HomeClientService(String url) {
//        this.baseUrl = url;
//    }


    /**
     * Retrieve a home based off of its mlsId
     */
    public Home getHomeByMLSID(String mlsId) throws ServiceException {

        Home home = null;
        try {
            //TODO - IMPLEMENT ME
            ResponseEntity<Home> response = restTemplate.exchange(baseUrl + mlsId, HttpMethod.GET, makeAuthEntity(), Home.class);
            home = response.getBody();
        }
        catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        }
        catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + " " + ex1.getStatusText());
        }
        return home;
    }


//     * List all reservations by hotel id.
//     */
//    public Reservation[] listReservationsByHotel(int hotelId) {
//        Reservation[] reservations = null;
//        try {
//            ResponseEntity<Reservation[]> response =
//                    restTemplate.exchange(API_BASE_URL + "hotels/" + hotelId + "/reservations",
//                            HttpMethod.GET, makeAuthEntity(), Reservation[].class);
//            reservations = response.getBody();
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return reservations;
//    }



    /**
     * Retrieve a List of all homes
     * (hint: web service returns an array of homes... how could you convert it to an ArrayList so you don't have to change
     *  the return type to a Home[]? - If you don't know, ask Google... ie. Don't change return type to an Home[])
     */
    public List<Home> retrieveAllHomes() throws ServiceException{

        Home[] homes = null;
        try {
            //TODO - IMPLEMENT ME
            ResponseEntity<Home[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, makeAuthEntity(), Home[].class);
            homes = response.getBody();
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
        HttpEntity<Home> entity = makeHomeEntity(newHome);

        Home returnedHome = null;
        try {
            //TODO - IMPLEMENT ME
            returnedHome = restTemplate.postForObject(baseUrl, entity, Home.class);
        }
        catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        }
        catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + " " + ex1.getStatusText());
        }

        return returnedHome;
    }



//    public Location add(Location newLocation) {
//        HttpEntity<Location> entity = makeLocationEntity(newLocation);
//
//        Location returnedLocation = null;
//        try {
//            returnedLocation = restTemplate.postForObject(API_BASE_URL, entity, Location.class);
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return returnedLocation;
//    }



    /**
     * Delete an existing home
     */
    public boolean deleteHome(String mlsId) throws ServiceException {

        boolean success = false;

        try {
            //TODO - IMPLEMENT ME
            restTemplate.exchange(baseUrl + mlsId, HttpMethod.DELETE, makeAuthEntity(), Void.class);
            success = true;
        } catch (ResourceAccessException ex) {
            throw new ServiceException(ex.getMessage());
        } catch (RestClientResponseException ex1) {
            throw new ServiceException(ex1.getRawStatusCode() + ex1.getMessage());
        }
        return success;
    }

//    public boolean delete(int id) {
//        boolean success = false;
//        try {
//            restTemplate.exchange(API_BASE_URL + id, HttpMethod.DELETE, makeAuthEntity(), Void.class);
//            success = true;
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return success;
//    }




    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Home> makeHomeEntity(Home home) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(home, headers);
    }




}
