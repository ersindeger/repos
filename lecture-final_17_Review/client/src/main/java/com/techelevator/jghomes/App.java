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
    private final HomeClientService homeClientService = new HomeClientService(API_BASE_URL);

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
            homeClientService.setAuthToken(currentUser.getToken());

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
        try {
            consoleService.printHomes(homeClientService.retrieveAllHomes());
        } catch (ServiceException e) {
            consoleService.printStatusMessage(e.getMessage());
        }

    }

	private void retrieveHomesByMLSId() {
		// TODO Auto-generated method stub
		String mlsid = consoleService.askUserForMLSID();
        try {
            consoleService.printHome(homeClientService.getHomeByMLSID(mlsid));
        } catch (ServiceException e) {
            consoleService.printStatusMessage(e.getMessage() + " Home " + mlsid + " was not found.");
        }
    }

	private void addNewHome() {
		// TODO Auto-generated method stub
        Home newHome = consoleService.getHomeInfo();
        try {
            newHome = homeClientService.addHome(newHome);
            if (newHome.getHomeId() > 0) {
                consoleService.printStatusMessage("Home MLS: " + newHome.getMlsNumber() + " was added with home id: " + newHome.getHomeId());
            }
        } catch (ServiceException e) {
            consoleService.printStatusMessage(e.getMessage() + " error adding home");
        }

    }

	private void deleteHome() {
		// TODO Auto-generated method stub
        String mlsId = consoleService.askUserForMLSID();
        try {
            if(homeClientService.deleteHome(mlsId)) {
                consoleService.printStatusMessage("Home with mlsid " + mlsId + " has been removed" );
            }
        } catch (ServiceException e) {
            consoleService.printStatusMessage(e.getMessage() + " error deleting home");
        }


    }


}
