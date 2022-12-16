package com.techelevator.jghomes;

import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.services.HomeClientService;
import com.techelevator.jghomes.view.UserInterface;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;


public class Application {

    private UserInterface ui;
    private HomeClientService homeClientService;

    private final String GET_LIST_OF_HOMES = "1";
    private final String ADD_HOME = "2";
    private final String DELETE_HOME = "3";
    private final String SEARCH_FOR_HOME_BY_MLS = "4";
    private final String EXIT_PROGRAM = "5";

    /**
     * The main entry point in the application
     */
    public static void main(String[] args) {


        Application app = new Application();
        app.run();

    }

    public Application() {
        ui = new UserInterface();
        homeClientService = new HomeClientService();
    }

    private void run() {

        while (true) {

            String choice = ui.printMenuOptions();

            //TODO: Remove next line. Here to test looping only
            System.out.println("You picked " + choice);

            if (choice.equals("1")) {    // retrieve all homes
                retrieveHomes();
            }
            else if (choice.equals("2")) {    //add a home
                addHome();
            }
            else if (choice.equals("3")) {   //delete a home
                deleteHome();
            }
            else if (choice.equals("4")) {    //search for a home
                searchForHomeByMLSId();
            }
            else if (choice.equals("5")) {    // exit program
                break;
            }
            else {   // not a valid choice. let user know
                ui.printStatusMessage("Not a valid choice");
            }

        }

    }

    private void searchForHomeByMLSId() {
        String mlsId = ui.askUserForMLSID();

        Home home = homeClientService.getHomeByMLSID(mlsId);
        //go call the HomeClientService class
        if (home != null) {
            ui.printHome(home);
        }
        else {
            ui.printStatusMessage("the home does not exist");
        }
    }

    private void retrieveHomes() {

        //go call the HomeClientService class and send results to UI
        List<Home> homes = homeClientService.retrieveAllHomes();

        try{
        if (homes != null) {
            ui.printHomes(homes);
        } else {
            ui.printStatusMessage("No homes to display");
        }
        }
        catch ( NullPointerException e ){
            e.printStackTrace();
        }
    }

    private void addHome() {

        Home newHome = ui.getHomeInfo();

        //go call the HomeClientService class and take the Home object in the response and send to UI...
        Home returnedHome = homeClientService.addHome(newHome);

        if (returnedHome != null) {
            ui.printHome(returnedHome);
        } else {
            ui.printStatusMessage("No new home to display");
        }

    }

    private void deleteHome() {
        String mlsId = ui.askUserForMLSID();

        //go call the HomeClientService class and if successful, let the ui know that home with 'mlsid' selected was deleted



        try {
            if (homeClientService.deleteHome(mlsId)) {
                ui.printStatusMessage("The home with the given MLS number is deleted!");
            } else {
                ui.printStatusMessage("The given MLS id does NOT exist. Deletion is unsuccessful!");
            }
        }
         catch (ServiceException e) {
            e.printStackTrace();
        }

    }


//    private void deleteHome() {
//        String mlsId = ui.askUserForMLSID();
//
//        try {
//            boolean deleted = service.deleteHome(mlsId);
//            ui.printStatusMessage("Home: " + mlsId + " has been deleted");
//        } catch (ServiceException e) {
//            ui.printStatusMessage(e.getMessage());
//        }
//
//
//    }
//

//    private void handleDeleteReservation() {
//        Reservation[] reservations = hotelService.listReservations();
//        if (reservations != null) {
//            consoleService.printReservationMenu(reservations);
//            int reservationId =  consoleService.promptForMenuSelection("Please select a reservation to delete: ");
//            if (reservationId > 0) {
//                if (!hotelService.deleteReservation(reservationId)) {
//                    consoleService.printErrorMessage();
//                }
//            }
//        } else {
//            consoleService.printErrorMessage();
//        }
//    }




}
