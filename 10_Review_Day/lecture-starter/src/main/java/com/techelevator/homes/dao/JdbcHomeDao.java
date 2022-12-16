package com.techelevator.homes.dao;

import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Address;
import com.techelevator.homes.model.Home;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcHomeDao implements HomeDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcHomeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public Home searchHomeByMlsId(String mlsNumber) {
        Home home = new Home();

        String sql = "SELECT * from home " +
                "JOIN address ON home.addressId = address.addressId " +
                "where mlsNumber = ? ;" ;
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, mlsNumber);

        if(result.next()){
            home = mapRowToHome(result);
        }

        return home;
    }


    @Override
    public List<Home> retrieveHomes() {
        List<Home> homes = new ArrayList<>();
        String sql = "SELECT * FROM home " +
                "JOIN address ON home.addressId = address.addressId;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while(results.next()){
            homes.add(mapRowToHome(results));
        }
        return homes;
    }

    @Override
    public void addHome(Home newHome) {
        String sqlAddress = "INSERT INTO address (addressLine, city, state, zip) " +
                " VALUES (?, ?, ?, ?) RETURNING addressId";
        Integer addressId = jdbcTemplate.queryForObject(sqlAddress, Integer.class, newHome.getAddress().getAddressLine(),
                newHome.getAddress().getCity(), newHome.getAddress().getState(), newHome.getAddress().getZip());

        String sql = "INSERT INTO home (mlsNumber, addressId, numberOfBathrooms, numberOfBedrooms, price, shortDescription) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, newHome.getMlsNumber(), addressId, newHome.getNumberOfBathrooms(),
                newHome.getNumberOfBedrooms(), newHome.getPrice(), newHome.getShortDescription());
    }

    @Override
    public void deleteHome(String mlsId) throws HomeNotFoundException {
        String sql = "SELECT addressId FROM home WHERE mlsNumber = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, mlsId);

//        String sqlCheck = "select count(*) from home where mlsnumber = ?;";
//        SqlRowSet countRow = jdbcTemplate.queryForRowSet(sqlCheck, mlsId);
//        int Z = countRow.getInt("addressId");

        int addressId = 0;
        if(results.next()) {
            addressId =results.getInt("addressId");
        }

        String sqlDeleteHome = "DELETE FROM home WHERE mlsNumber = ?;";
        jdbcTemplate.update(sqlDeleteHome, mlsId);

        String sqlDeleteAddress = "DELETE FROM address WHERE addressId = ?;";
        jdbcTemplate.update(sqlDeleteAddress, addressId);

    }


    private Home mapRowToHome(SqlRowSet rowSet){
        Home home = new Home();
        home.setHomeId(rowSet.getInt("homeId"));
        home.setMlsNumber(rowSet.getString("mlsNumber"));

        Address address = new Address();
        address.setAddressLine(rowSet.getString("addressLine"));
        address.setCity(rowSet.getString("city"));
        address.setState(rowSet.getString("state"));
        address.setZip(rowSet.getInt("zip"));

        home.setAddress(address);
        home.setNumberOfBathrooms(rowSet.getInt("numberOfBathrooms"));
        home.setNumberOfBedrooms(rowSet.getInt("numberOfBedrooms"));
        home.setPrice(rowSet.getBigDecimal("price"));
        home.setShortDescription(rowSet.getString("shortDescription"));

        return home;
    }

}
