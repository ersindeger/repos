package com.techelevator.homes.controller;


import com.techelevator.homes.dao.HomeDAO;
import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private HomeDAO dao;

    public HomeController (HomeDAO dao) {
        this.dao = dao;
    }


    @RequestMapping(path="/hello", method= RequestMethod.GET)
    public String helloWorld() {
        return "Hello World";
    }

    /**
     * Return All Homes
     *
     * @return a list of all homes in the system
     */
    @RequestMapping(path = "/homes", method = RequestMethod.GET)
    public List<Home> list() {
        return dao.retrieveHomesForSale();
    }


    /**
     * Return home information
     *
     * @param mlsId the id of the home
     * @return all info for a given home
     */

    @RequestMapping(path = "/homes/{mlsId}", method = RequestMethod.GET)
    public Home retrieveHomeByMLSId(@Valid @PathVariable String mlsId) {
        Home home = null;

        try {
            return dao.retrieveHomeByMLSId(mlsId);
        } catch (HomeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found.");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/homes", method = RequestMethod.POST)
    public Home addHome(@Valid @RequestBody Home newHome) {
        return dao.addHome(newHome);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/homes/{mlsId}")
    public void deleteHome(@PathVariable String mlsId){

        try {
            dao.deleteHome(mlsId);
        } catch (HomeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Home not found.");        }
    }





}



