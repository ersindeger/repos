package com.techelevator.jghomes.services;


import com.techelevator.jghomes.model.Address;
import com.techelevator.jghomes.model.Home;
import com.techelevator.jghomes.model.UserCredentials;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("********************************************");
        System.out.println("* Welcome to Shady Steve's Real Estate App! *");
        System.out.println("*********************************************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: Get list of homes");
        System.out.println("2: Get home by mlsid");
        System.out.println("3: Add a home");
        System.out.println("4: Delete a home");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

//    public BigDecimal promptForBigDecimal(String prompt) {
//        System.out.print(prompt);
//        while (true) {
//            try {
//                return new BigDecimal(scanner.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.println("Please enter a decimal number.");
//            }
//        }
//    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public String askUserForMLSID() {
        System.out.println("Please enter a valid MLS ID: ");
        return scanner.nextLine();
    }

    public void printHomes(List<Home> homes) {

        for (Home home : homes) {
            printHome(home);
        }

    }


    /**
     * This method prints out a single home.  We 'could' have put this up in the for each loop above, but breaking
     * it out on it's own keeps the code above cleaner AND we can also reuse when printing out the home from menu option 2
     *
     * @param home
     */
    public void printHome(Home home) {


        System.out.println("\nMLS Number: " + home.getMlsNumber());

        if (home.getAddress() != null) {
            System.out.println("Street Address: "
                    + home.getAddress().getAddressLine() + " "
                    + home.getAddress().getCity() + " "
                    + home.getAddress().getState() + " "
                    + home.getAddress().getZip() + "\n");
        }

        System.out.println(String.format("%-25s %s", "Bedrooms: ", home.getNumberOfBedrooms()));
        System.out.println(String.format("%-25s %s", "Bathrooms: ", home.getNumberOfBathrooms()));
        System.out.println(String.format("%-25s $%s", "Price: ", String.format("%.2f", home.getPrice())));
        System.out.println("\nDescription: " + home.getShortDescription());

    }

    public void printStatusMessage(String message) {
        System.out.println(message);
    }


    public Home getHomeInfo() {

        Home home = new Home();

        System.out.println("Enter the MLS Number this house is being listed under");
        String mls = scanner.nextLine();
        home.setMlsNumber(mls);

        // call private method below to get the address from user
        Address address = getAddressInfo();
        home.setAddress(address);

        System.out.println("Enter the number of Bedrooms: ");
        String numberOfBedrooms = scanner.nextLine();
        home.setNumberOfBedrooms(Double.parseDouble(numberOfBedrooms));

        System.out.println("Enter the number of Bathrooms: ");
        String numberOfBathrooms = scanner.nextLine();
        home.setNumberOfBathrooms(Double.parseDouble(numberOfBathrooms));

        System.out.println("Enter a short description of the property: ");
        home.setShortDescription(scanner.nextLine());

        System.out.println("What is the listing price of this property?");
        String priceAsString = scanner.nextLine();
        // home.setPrice(Double.parseDouble(priceAsString));
        home.setPrice(new BigDecimal(priceAsString));

        return home;

    }

    private Address getAddressInfo() {
        Address address = new Address();

        System.out.println("\nEnter address Line 1: ");
        address.setAddressLine(scanner.nextLine());

        System.out.println("\nEnter the city name: ");
        address.setCity(scanner.nextLine());

        System.out.println("\nEnter the state: ");
        address.setState(scanner.nextLine());

        System.out.println("\nEnter the zip code: ");
        String zip = scanner.nextLine();
        address.setZip(Integer.parseInt(zip));

        return address;
    }


}
