package com.techelevator.reservations.controllers;

import com.techelevator.reservations.dao.HotelDao;
import com.techelevator.reservations.dao.MemoryHotelDao;
import com.techelevator.reservations.dao.MemoryReservationDao;
import com.techelevator.reservations.dao.ReservationDao;
import com.techelevator.reservations.model.Hotel;
import com.techelevator.reservations.model.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController //informs Spring Boot for the controller

public class HotelController {

    private HotelDao hotelDao;
    private ReservationDao reservationDao;

    public HotelController(){
        hotelDao = new MemoryHotelDao();
        reservationDao = new MemoryReservationDao(hotelDao); {
        }
    }

    /**
     * This method returns a list of all hotels
     * @return List of all Hotels
     **/

    @RequestMapping(path= "/hotels", method = RequestMethod.GET)
    public List<Hotel> list() {
        return hotelDao.list();
    }

    @RequestMapping(path= "/hotels/{id}", method = RequestMethod.GET)
    public Hotel getHotelsById ( @PathVariable int id ) {
        return hotelDao.get(id);
    }

    @RequestMapping(path = "/reservations", method = RequestMethod.GET )
    public List<Reservation> listReservations (){
        return reservationDao.findAll();
    }

    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.GET )
    public Reservation listReservationsById (@PathVariable int id){
        return reservationDao.get(id);
    }

    @RequestMapping ( path = "/hotels/{hotelid}/reservations", method = RequestMethod.GET )
    public List<Reservation> getReservationsByHotelId (@PathVariable("id") int hotelId) {
        return reservationDao.findByHotel(hotelId);
    }

    @RequestMapping ( path= "/reservations", method = RequestMethod.POST )
    public Reservation addReservation (@RequestBody Reservation reservation) {
        return reservationDao.create(reservation, reservation.getHotelID());
    }

    //  /hotels/filter?state={state}&city={city}
    @RequestMapping ( path = "/hotels/filter", method = RequestMethod.GET )
    public List<Hotel> getHotelsFilterByCityState(String state, @RequestParam(required = false) String city) {
                                                          // ensures CITY is OPTIONAL in the search!

        List<Hotel> filteredHotels = new ArrayList<>();
        List<Hotel> hotels = list();

        // return hotels that match state
        for (Hotel hotel : hotels) {

            // if city was passed we don't care about the state filter
            if (city != null) {
                if (hotel.getAddress().getCity().toLowerCase().equals(city.toLowerCase())) {
                    filteredHotels.add(hotel);
                }
            } else {
                if (hotel.getAddress().getState().toLowerCase().equals(state.toLowerCase())) {
                    filteredHotels.add(hotel);
                }

            }
        }

        return filteredHotels;
    }


}
