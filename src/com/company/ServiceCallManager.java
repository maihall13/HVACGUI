package com.company;

import java.util.*;

/**
 * Created by clara on 2/3/17.
 * User interface for managing service calls.
 *
 */

public class ServiceCallManager {

    private LinkedList<ServiceCall> todayServiceCalls;
    private LinkedList<ServiceCall> resolvedServiceCalls;
    // Menu options, as an array
    String[] mainMenuOptions = {
            "1. Add service call to queue",
            "2. Resolve current call",
            "3. Print current call",
            "4. Print all outstanding calls",
            "5. Print all resolved calls ",
            "6. Quit" };

    String[] addCallOptions = {
            "1. Add service call for furnace",
            "2. Add service call for AC unit",
            "3. Add service call for water heater",
            "4. Return to main menu" };


    /* Constructor sets up the two lists, the UserInput object, and starts the main menu */
    public ServiceCallManager() {

        todayServiceCalls = new LinkedList<ServiceCall>();

        // This will be used to store a list of resolved service calls.
        resolvedServiceCalls = new LinkedList<ServiceCall>();

        manageCalls();
    }


    /* The main menu */
    public void manageCalls() {

        while (true) {

            displayMenu(mainMenuOptions);

            int choice = Input.getPositiveIntInput();

            if (choice == 1) {
                addServiceCall();
            }
            else if (choice == 2) {
                resolveServiceCall();
            }
            else if (choice == 3) {
                showNextCall();
            }
            else if (choice == 4){
                showAllOpenCalls();
            }
            else if (choice == 5) {
                showAllResolvedCalls();
            }
            else if (choice == 6) {
                break;
            }
            else {
                System.out.println("Enter a number from the menu choices");
            }
        }
        System.out.println("Thanks, bye!");
    }


    /* Displays the contents of an array; the array should hold each menu option. */
    protected void displayMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
    }


    /* Display sub-menu to add a new service call. Ask user what type of item needs servicing, and
    call appropriate method to create a service call for that thing. */
    private void addServiceCall() {

        while (true) {
            displayMenu(addCallOptions);

            int choice = Input.getPositiveIntInput();

            if (choice == 1) {
                addFurnaceServiceCall();
            }
            else if (choice == 2) {
                addACServiceCall();
            }
            else if (choice == 3) {
                addWaterHeaterCall();
            }
            else if(choice == 4){
                return;
            }
            else {
                System.out.println("Please enter a number from the menu choices");
            }
        }
    }


    /* Get data about furnace, create Furnace object, add to end of queue of ServiceCalls */
    private void addFurnaceServiceCall() {

        String address = Input.getStringInput("Enter address of furnace");
        String problem = Input.getStringInput("Enter description of problem");
        Furnace.FurnaceType type = Input.getFurnaceType();
        Furnace f = new Furnace(address, problem, new Date(), type);
        todayServiceCalls.add(f);

        System.out.println("Added the following furnace to list of calls:\n" + f);
    }


    /* Get data about AC unit, create CentralAC object, add to end of queue of ServiceCalls */
    private void addACServiceCall() {

        String address = Input.getStringInput("Enter address of AC Unit");
        String problem = Input.getStringInput("Enter description of problem");
        String model = Input.getStringInput("Enter model of AC unit");

        CentralAC ac = new CentralAC(address, problem, new Date(), model);
        todayServiceCalls.add(ac);
        System.out.println("Added the following AC unit to list of calls:\n" + ac);
    }

    //Add water heater to service call manager
    private void addWaterHeaterCall() {

        String address = Input.getStringInput("Enter address of water heater");
        String problem = Input.getStringInput("Enter description of problem");
        Double age = Input.getPositiveDoubleInput("Enter age of water heater");

        WaterHeater wh = new WaterHeater(address, problem, new Date(), age);
        todayServiceCalls.add(wh);
        System.out.println("Added the following AC unit to list of calls:\n" + wh);
    }


    /* Resolve the call at the top of the queue
     Call is resolved by removing it from the queue, asking user
     for resolution and fee, and adding the call to the resolved calls queue
     TODO - future version could allow user to resolve any call, not just the one at the top of the queue */
    private void resolveServiceCall() {

        if (todayServiceCalls.isEmpty()) {
            System.out.println("No service calls today");
            return;
        }

        ServiceCall resolvedCall = todayServiceCalls.remove();    //Remove call from head of queue

        String resolution = Input.getStringInput("Enter resolution for " + resolvedCall);
        double fee = Input.getPositiveDoubleInput("Enter fee charged to customer");

        resolvedCall.setResolution(resolution);
        resolvedCall.setFee(fee);
        resolvedCall.setResolvedDate(new Date());  //default resolved date is now

        resolvedServiceCalls.add(resolvedCall);  //Add this call to the list of resolved calls

    }


    /* Print details of the next call, the one at the head of the queue */
    private void showNextCall() {
        if (todayServiceCalls.isEmpty()) {
            System.out.println("No service calls today");
        } else {
            System.out.println(todayServiceCalls.peek());
        }
    }


    /* Display all open calls */
    private void showAllOpenCalls() {
        System.out.println("Today's service calls are: ");

        if (todayServiceCalls.isEmpty()) {
            System.out.println("No service calls today");
        }

        // Display a numbered list of all the serviceCalls
        int callCount = 1;
        for (ServiceCall call : todayServiceCalls) {
            System.out.println("Service Call " + callCount++ + ", " + call +  "\n");
        }
    }


    /* Display all resolved calls. Perhaps this method could be combined with the one above?  */
    private void showAllResolvedCalls() {
        System.out.println("List of resolved calls: ");

        if (resolvedServiceCalls.isEmpty()) {
            System.out.println("No resolved calls");
        }

        for (ServiceCall call : resolvedServiceCalls) {
            System.out.println(call + "\n");
        }
    }
}