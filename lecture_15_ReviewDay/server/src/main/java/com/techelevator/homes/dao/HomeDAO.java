package com.techelevator.homes.dao;

import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Home;

import java.util.List;

public interface HomeDAO {

    Home addHome(Home newHome);
    void deleteHome(String mlsId) throws HomeNotFoundException;
    List<Home> retrieveHomesForSale();
    Home retrieveHomeByMLSId(String mlsId) throws HomeNotFoundException;
}
