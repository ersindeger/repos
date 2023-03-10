package com.techelevator.homes.dao;

import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Address;
import com.techelevator.homes.model.Home;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component  //forget this <=== and the HomeDAO won't be injected into the Controller!!
public class JDBCHomeDAO implements HomeDAO{

    private JdbcTemplate jdbcTemplate;

    public JDBCHomeDAO(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Home retrieveHomeByMLSId(String mlsId) throws HomeNotFoundException {
        Home home = null;

        String sql = "SELECT home.*, address.* " +
                "FROM home " +
                "JOIN address ON home.addressid = address.addressid " +
                "WHERE mlsnumber = ?";

        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, mlsId);

        if (result.next()) {
            home = mapRowToHome(result);
        }

        if(home == null) {
            throw new HomeNotFoundException();
        }


        return home;
    }


    public List<Home> retrieveHomesForSale() {
        List<Home> homes = new ArrayList<>();

        String sql = "SELECT home.*, address.* " +
                "FROM home " +
                "JOIN address ON home.addressid = address.addressid";


        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()) {

            Home home = mapRowToHome(results);
            homes.add(home);
        }

        return homes;
    }

    @Override
    public Home addHome(Home newHome) {

        String addressSQL = "INSERT INTO address(addressLine, city, state, zip) " +
                "VALUES( ?, ?, ?, ?) RETURNING addressid";


        Integer addressId = jdbcTemplate.queryForObject(addressSQL,
                Integer.class,
                newHome.getAddress().getAddressLine(),
                newHome.getAddress().getCity(),
                newHome.getAddress().getState(),
                newHome.getAddress().getZip()
        );

        String homeSQL = "INSERT INTO home (mlsnumber, addressid, numberofbathrooms, numberofbedrooms, price, shortdescription) " +
                "VALUES(?,?,?,?,?,?) RETURNING HOMEID";

        Integer id = jdbcTemplate.queryForObject(homeSQL, Integer.class,
                newHome.getMlsNumber(),
                addressId,
                newHome.getNumberOfBathrooms(),
                newHome.getNumberOfBedrooms(),
                newHome.getPrice(),
                newHome.getShortDescription()
        );

        newHome.setHomeId(id);
        return newHome;
    }

    public void deleteHome(String mlsId) throws HomeNotFoundException {


        String addressIdSql = "SELECT addressid from home WHERE mlsnumber = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(addressIdSql, mlsId);

        int addressId;

        if (results.next()) {
            addressId = results.getInt("addressId");
        }
        else {
            throw new HomeNotFoundException();
        }

        //first delete the home..
        String sql = "DELETE FROM home WHERE mlsNumber = ?";
        jdbcTemplate.update(sql, mlsId);

        //now delete the address
        String addressSQL = "DELETE FROM address where addressId = ?";
        jdbcTemplate.update(addressSQL, addressId);


    }


    private Home mapRowToHome(SqlRowSet results) {
        Home home = new Home();

        home.setNumberOfBathrooms(results.getDouble("numberOfBathrooms"));
        home.setMlsNumber(results.getString("mlsNumber"));
        home.setPrice(results.getBigDecimal("price"));
        home.setNumberOfBedrooms(results.getDouble("numberOfBedrooms"));
        home.setShortDescription(results.getString("shortDescription"));

        Address address = new Address();

        address.setAddressLine(results.getString("addressLine"));
        address.setCity(results.getString("city"));
        address.setState(results.getString("state"));
        address.setZip(results.getInt("zip"));

        home.setAddress(address);

        return home;
    }

}
