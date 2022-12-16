package com.techelevator.auctions.controller;

import com.techelevator.auctions.dao.AuctionDao;
import com.techelevator.auctions.dao.MemoryAuctionDao;
import com.techelevator.auctions.model.Auction;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private AuctionDao auctionDao;

    public AuctionController() {
        this.auctionDao = new MemoryAuctionDao();
    }


    @RequestMapping (path="/{id}")
    public Auction doSomething(){

        return null;
    }


    //      /auctions?currentBid_lte=
    //     /auctions?title_like=
    @RequestMapping ( path = "", method = RequestMethod.GET ) //notice we ALREADY defined the main path on line 12...
    public List<Auction> list
    ( @RequestParam(required = false) String title_like, @RequestParam(required = false, defaultValue = "0")  double currentBid_lte ) {

        List<Auction> filteredAuctions;

        if (currentBid_lte > 0 && title_like != null) {
            return auctionDao.searchByTitleAndPrice(title_like, currentBid_lte);
        }

        else if(currentBid_lte > 0) { filteredAuctions = auctionDao.searchByPrice(currentBid_lte);
            return filteredAuctions; }

        else if ( title_like != null ) { filteredAuctions = auctionDao.searchByTitle(title_like);
            return filteredAuctions; }
//
//        else if ( title_like == null ) { filteredAuctions = auctionDao.list();
//            return filteredAuctions; }
//
//
//        else if (currentBid_lte == 0) { filteredAuctions = auctionDao.list();
//            return filteredAuctions;
//        }

        else { filteredAuctions = auctionDao.list();
            return filteredAuctions;}

    }


    @RequestMapping ( path = "/{id}", method = RequestMethod.GET )
    public Auction get(@PathVariable int id) {
        return auctionDao.get(id);
    }


    @RequestMapping ( path= "", method = RequestMethod.POST )
    public Auction create(@RequestBody Auction auction ) {
        return auctionDao.create(auction);
    }

}
