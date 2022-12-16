package com.techelevator.jghomes.controller;


import com.techelevator.jghomes.dao.HomeDAO;
import com.techelevator.jghomes.exception.HomeNotFoundException;
import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@PreAuthorize("permitAll")
public class HomeController {

    @Autowired
    private HomeDAO dao;
    private User user;



    @RequestMapping(path="/hello", method= RequestMethod.GET)
    public String helloWorld() {
        return "Hello World";
    }


    /**
     * Return all homes
     *
     * @return List<Home>  -  a list of all homes in the system
     */

    @PreAuthorize("permitAll")
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



    @PreAuthorize("permitAll")
    @RequestMapping(path = "/homes/{mlsId}", method = RequestMethod.GET)
    public Home retrieveHomeByMLSID(@PathVariable String mlsId) throws HomeNotFoundException {
        return dao.retrieveHomeByMLSId(mlsId);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/homes/{mlsId}", method = RequestMethod.DELETE)
    public void deleteHome(@PathVariable String mlsId) throws HomeNotFoundException {
        //TODO - Print out the current logged-in user to System.out.println - normally bad, but it shows you can get to it.
        System.out.println("The user" + " : " + user.getUsername() );
        dao.deleteHome(mlsId);
    }


}
