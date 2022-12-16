package com.techelevator;

import com.techelevator.homes.dao.HomeDao;
import com.techelevator.homes.dao.JdbcHomeDao;
import com.techelevator.homes.exception.HomeNotFoundException;
import com.techelevator.homes.model.Home;
import com.techelevator.homes.view.UserInterface;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.util.List;

public class Application {

    private UserInterface ui;

    private final String GET_LIST_OF_HOMES = "1";
    private final String ADD_HOME = "2";
    private final String DELETE_HOME = "3";
    private final String SEARCH_FOR_HOME_BY_MLS = "4";
    private final String EXIT_PROGRAM = "5";

    private HomeDao homeDao;


    /**
     * The main entry point in the application
     */
    public static void main(String[] args) {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/jghomes");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        Application app = new Application(dataSource);
        app.run();

    }

    public Application(DataSource dataSource) {
        ui = new UserInterface();

        //define and instantiate DAOs here ... don't forget to send them the datasource...
        homeDao = new JdbcHomeDao(dataSource);

    }

    private void run() {

        while (true) {

            String choice = ui.printMenuOptions();

            //TODO: Remove this line. Here to test looping only
            System.out.println("You picked " + choice);

            if (choice.equals(GET_LIST_OF_HOMES)) {    // retrieve all homes
                retrieveHomes();
            }
            else if (choice.equals(ADD_HOME)) {    //add a home
                addHome();
            }
            else if (choice.equals(DELETE_HOME)) {   //delete a home
                deleteHome();
            }
            else if (choice.equals(SEARCH_FOR_HOME_BY_MLS)) {    //search for a home
                searchForHomeByMLSId();
            }
            else if (choice.equals(EXIT_PROGRAM)) {    // exit program
                break;
            }
            else {   // not a valid choice. let user know
                ui.printStatusMessage("Not a valid choice");
            }

        }

    }

    private void searchForHomeByMLSId() {

        //call user interface to get MLS id
        String mlsId = ui.askUserForMLSID();

        //pass mlsId to dao
        Home home = homeDao.searchHomeByMlsId(mlsId);

        //send Home back to UI
        ui.printHome(home);

    }

    private void retrieveHomes() {

        //call DAO to retrieve a list of homes
       List<Home> homes = homeDao.retrieveHomes();

        // send retrieved List to the UI to print
        ui.printHomes(homes);

    }

    private void addHome() {

        //call user interface to get new Home object
        Home home = ui.getHomeInfo();
        //pass home to dao
        homeDao.addHome(home);

    }

    private void deleteHome() {

        //call user interface to get MLS id
         String mlsId =  ui.askUserForMLSID();
        //pass mlsId to dao for deletion

        try {
            homeDao.deleteHome(mlsId);
        } catch (HomeNotFoundException e) {
            e.printStackTrace();
            ui.printStatusMessage("this home NOT found!!!!");
        }

            ui.printStatusMessage("The house with the MLS number " + mlsId + " has been deleted.." );
    }


}
