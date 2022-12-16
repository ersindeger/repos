package com.techelevator.homes.controller;


import com.techelevator.homes.dao.HomeDAO;
import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    private HomeDAO dao;

    @RequestMapping(path="/hello", method= RequestMethod.GET)
    public String helloWorld() {
        return "Hello World";
    }


    /**
     * Return all homes
     *
     * @return List<Home>  -  a list of all homes in the system
     */
    @RequestMapping(path = "/homes", method = RequestMethod.GET)
    public List<Home> getHomes() {
        return dao.retrieveHomesForSale();
    }


    /**
     * Add a new home
     *
     * @return 201 Created if successful
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/homes", method = RequestMethod.POST)
    public Home addHome(@RequestBody Home home) {
        return dao.addHome(home);
    }

    @RequestMapping(path = "/homes/{mlsId}", method = RequestMethod.GET)
    public Home retrieveHomeByMLSID(@PathVariable String mlsId) throws HomeNotFoundException {
        return dao.retrieveHomeByMLSId(mlsId);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/homes/{mlsId}", method = RequestMethod.DELETE)
    public void deleteHome(@PathVariable String mlsId) throws HomeNotFoundException {
        dao.deleteHome(mlsId);
    }


}
