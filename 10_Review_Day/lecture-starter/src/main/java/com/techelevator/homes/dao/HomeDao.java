package com.techelevator.homes.dao;

import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Home;

import java.util.List;

public interface HomeDao {


    public Home searchHomeByMlsId(String mlsNumber);

    public List<Home> retrieveHomes();

    public void addHome(Home newHome);

    public void deleteHome(String mlsId) throws HomeNotFoundException;

}
