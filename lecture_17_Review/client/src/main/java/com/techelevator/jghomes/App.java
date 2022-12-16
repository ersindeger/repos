package com.techelevator.jghomes;

import com.techelevator.jghomes.exceptions.ServiceException;
import com.techelevator.jghomes.model.AuthenticatedUser;
import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.model.UserCredentials;
import com.techelevator.jghomes.services.AuthenticationService;
import com.techelevator.jghomes.services.ConsoleService;
import com.techelevator.jghomes.services.HomeClientService;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                consoleService.printStatusMessage("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        consoleService.printStatusMessage("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            consoleService.printStatusMessage("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        else {
            //TODO: we have a user... I bet we have some sort of JWT token thingy inside the user object that our
            // client side service might find helpful... ;-)   Hint. Hint...

        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                retrieveHomes();
            } else if (menuSelection == 2) {
                retrieveHomesByMLSId();
            } else if (menuSelection == 3) {
                addNewHome();
            } else if (menuSelection == 4) {
                deleteHome();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


	private void retrieveHomes() {
        //TODO - IMPLEMENT ME
    }
        Home[] homes = consoleService.getHomeInfo()
//    // List all hotels
//    Hotel[] hotels = hotelService.listHotels();
//        if (hotels != null) {
//        consoleService.printHotels(hotels);
//    } else {
//        consoleService.printErrorMessage();
//    }
//}

	private void retrieveHomesByMLSId() {
        //TODO - IMPLEMENT ME
    }

	private void addNewHome() {
        //TODO - IMPLEMENT ME

    }

	private void deleteHome() {
        //TODO - IMPLEMENT ME

    }


}
