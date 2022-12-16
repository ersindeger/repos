package com.techelevator.jghomes;

import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.view.UserInterface;
import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.services.HomeClientService;
import org.springframework.web.bind.annotation.PathVariable;


public class Application {

    private UserInterface ui;

    private final String GET_LIST_OF_HOMES = "1";
    private final String ADD_HOME = "2";
    private final String DELETE_HOME = "3";
    private final String SEARCH_FOR_HOME_BY_MLS = "4";
    private final String EXIT_PROGRAM = "5";


    HomeClientService service = new HomeClientService();

    /**
     * The main entry point in the application
     */
    public static void main(String[] args) {


        Application app = new Application();
        app.run();

    }

    public Application() {
        ui = new UserInterface();

    }

    private void run() {

        while (true) {

            String choice = ui.printMenuOptions();

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

        try {
            ui.printHome(service.getHomeByMLSID(mlsId));
        } catch (ServiceException e) {
            ui.printStatusMessage(e.getMessage());
        }


    }

    private void retrieveHomes() {

        //go call the HomeClientService class and send results to UI
        try {
            ui.printHomes(service.retrieveAllHomes());
        } catch (ServiceException e) {
            ui.printStatusMessage(e.getMessage());
        }

    }

    private void addHome() {

        Home newHome = ui.getHomeInfo();

        //go call the HomeClientService class and take the Home object in the response and send to UI...
        try {
            newHome = service.addHome(newHome);
            ui.printStatusMessage("Home: " + newHome.getMlsNumber() + " was added!");
        } catch (ServiceException e) {
            ui.printStatusMessage(e.getMessage());
        }

    }

    private void deleteHome() {
        String mlsId = ui.askUserForMLSID();

        try {
            boolean deleted = service.deleteHome(mlsId);
            ui.printStatusMessage("Home: " + mlsId + " has been deleted");
        } catch (ServiceException e) {
            ui.printStatusMessage(e.getMessage());
        }


    }





}
