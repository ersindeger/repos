package com.techelevator.jghomes.dao;

import com.techelevator.jghomes.exception.HomeNotFoundException;
import com.techelevator.jghomes.model.Home;

import java.util.List;

public interface HomeDAO {

    Home addHome(Home newHome);
    void deleteHome(String mlsId) throws HomeNotFoundException;
    List<Home> retrieveHomesForSale();
    Home retrieveHomeByMLSId(String mlsId) throws HomeNotFoundException;
}
