package ui;

import model.Customer;
import model.Drink;
import model.Manager;
import model.Order;
import model.drinks.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class OnlineOrderingApp {
    private static final String JSON_STORE = "./data/manager.json";
    private Manager manager;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public OnlineOrderingApp() throws FileNotFoundException  {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runOnlineOrderingApp();
    }

    //EFFECTS: runs the online ordering app
    public void runOnlineOrderingApp() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Have a good day!");
    }

    // EFFECTS: initializes coffee shop with manager
    private void init() {
        manager = new Manager();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    public void displayMainMenu() {
        System.out.println("\nWelcome to the UBC Coffee Shop.");
        System.out.println("\nPlease select from below:");
        System.out.println("\tc -> customer");
        System.out.println("\tm -> manager");
        System.out.println("\ts -> save coffee shop data to file");
        System.out.println("\tl -> load coffee shop data from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void processCommand(String command) {
        if (command.equals("c")) {
            doPlaceOrder();
        } else if (command.equals("m")) {
            doCoffeeShopSummary();
        } else if (command.equals("s")) {
            saveManager();
        } else if (command.equals("l")) {
            loadManager();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays drink menu
    public void displayDrinkMenu() {
        System.out.println("\nPlease select what you would like to order:");
        System.out.println("\tbm -> belgian mocha");
        System.out.println("\tc -> cappuccino");
        System.out.println("\tcm -> caramel macchiato");
        System.out.println("\tl -> latte");
        System.out.println("\tml -> matcha latte");
        System.out.println("\tf -> finish ordering");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink order command
    public Drink processDrinkOrderCommand(String command) {
        if (command.equals("bm")) {
            return new BelgianMocha();
        } else if (command.equals("c")) {
            return new Cappuccino();
        } else if (command.equals("cm")) {
            return new CaramelMacchiato();
        } else if (command.equals("l")) {
            return new Latte();
        } else if (command.equals("ml")) {
            return new MatchaLatte();
        } else {
            System.out.println("Selection not valid...");
        }
        return null;
    }

    // EFFECTS: displays size options
    public void displaySizeOptions() {
        System.out.println("\nPlease select size option:");
        System.out.println("\tm -> medium");
        System.out.println("\tl -> large");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink size option command
    public void processSizeOptionCommand(Drink drink, String command) {
        if (command.equals("m")) {
            drink.setSizeOption("m");
        } else if (command.equals("l")) {
            drink.setSizeOption("l");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays milk options
    public void displayMilkOptions() {
        System.out.println("\nPlease select milk option:");
        System.out.println("\ta -> almond");
        System.out.println("\to -> oat");
        System.out.println("\ts -> soy");
        System.out.println("\tn -> regular");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink milk option command
    public void processMilkOptionCommand(Drink drink, String command) {
        if (command.equals("a")) {
            drink.setMilkOption("a");
        } else if (command.equals("o")) {
            drink.setMilkOption("o");
        } else if (command.equals("s")) {
            drink.setMilkOption("s");
        } else if (command.equals("n")) {
            drink.setMilkOption("n");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays syrup flavour options
    public void displaySyrupFlavourOptions() {
        System.out.println("\nPlease select syrup flavour option:");
        System.out.println("\tv -> vanilla");
        System.out.println("\tm -> maple");
        System.out.println("\th -> hazelnut");
        System.out.println("\tn -> none");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink syrup flavour option command
    public void processSyrupFlavourOptionCommand(Drink drink, String command) {
        if (command.equals("v")) {
            drink.setFlavourOption("v");
        } else if (command.equals("m")) {
            drink.setFlavourOption("m");
        } else if (command.equals("h")) {
            drink.setFlavourOption("h");
        } else if (command.equals("n")) {
            drink.setFlavourOption("n");
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays syrup flavour options
    public void displayAdditionalRequestsOptions() {
        System.out.println("\nPlease enter additional requests (or 'n' if none):");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink syrup flavour option command
    public void processAdditionalRequestsOptionCommand(Drink drink, String command) {
        if (command.equals("n")) {
            drink.setAdditionalRequests("n");
        } else {
            drink.setAdditionalRequests(command);
        }
    }


    //EFFECTS: display current list of drinks ordered
    public void displayDrinksOrdered(Order order) {
        for (Drink drink : order.getDrinks()) {
            System.out.println(drink.getName());
            System.out.println("\t$" + String.format("%.2f", drink.getPrice()));
            System.out.println("\t" + drink.getSizeOption());
            if (drink.getMilkOption() != null) {
                System.out.println("\t" + drink.getMilkOption());
            }
            if (drink.getFlavourOption() != null) {
                System.out.println("\t" + drink.getFlavourOption());
            }
            if (drink.getAdditionalRequests() != null) {
                System.out.println("\t" + drink.getAdditionalRequests());
            }
        }
        System.out.println("Total: $" + String.format("%.2f", order.getTotalPriceOfOrder()));
    }

    // EFFECTS: allows user/customer to place an order
    @SuppressWarnings("methodlength")
    private void doPlaceOrder() {
        System.out.print("Hi! Please enter your name: ");
        String name = input.next();
        name = name.toLowerCase();


        Customer newCustomer;

        if (manager.returningCustomer(name) == null) {
            newCustomer = new Customer(name);
            manager.addCustomerToListOfAllCustomers(newCustomer);
        } else {
            newCustomer = manager.returningCustomer(name);
        }

        Order newOrder = new Order();

        boolean keepGoing = true;

        while (keepGoing) {
            Drink newDrink;
            String command = "";

            newDrink = selectDrink();
            selectSize(newDrink);
            selectMilk(newDrink);
            selectSyrupFlavour(newDrink);
            addAdditionalRequests(newDrink);

            newOrder.addDrinkToOrder(newDrink);
            System.out.println("Order list: ");
            displayDrinksOrdered(newOrder);

            while (!(command.equals("c") || command.equals("f"))) {
                System.out.println("c - continue order");
                System.out.println("m - modify order");
                System.out.println("f - finish order");
                command = input.next();
                command = command.toLowerCase();
                if (command.equals("f")) {
                    keepGoing = false;
                } else if (command.equals("m")) {
                    modifyOrder(newOrder);
                } else if (!command.equals("c")) {
                    System.out.println("Selection not valid...");
                }
            }
        }

        newCustomer.addToOrderHistory(newOrder);
        manager.addToTotalSalesRevenue(newOrder);

        System.out.print("Your total was: $" + String.format("%.2f",newOrder.getTotalPriceOfOrder()) + ". ");
        System.out.print("Thank you, " + newCustomer.getName() + ". You have successfully placed your order.");
    }

    //EFFECTS: allows user to select drink
    private Drink selectDrink() {
        String selection = "";
        String[] validDrinkOrderInputs = {"bm","c","cm","l","ml","f"};

        while (!(Arrays.asList(validDrinkOrderInputs).contains(selection))) {
            displayDrinkMenu();
            selection = input.next();
            selection = selection.toLowerCase();
            processDrinkOrderCommand(selection);
        }

        return processDrinkOrderCommand(selection);
    }

    //EFFECTS: allows user to select size of drink
    private void selectSize(Drink newDrink) {
        String selection = "";

        while (!(selection.equals("m") || selection.equals("l"))) {
            displaySizeOptions();
            selection = input.next();
            selection = selection.toLowerCase();
            processSizeOptionCommand(newDrink, selection);
        }
    }

    //EFFECTS: allows user to select milk for drink
    private void selectMilk(Drink newDrink) {
        String selection = "";

        while (!(selection.equals("a") || selection.equals("o") || selection.equals("s") || selection.equals("n"))) {
            displayMilkOptions();
            selection = input.next();
            selection = selection.toLowerCase();
            processMilkOptionCommand(newDrink, selection);
        }
    }

    //EFFECTS: allows user to select syrup flavour for drink
    private void selectSyrupFlavour(Drink newDrink) {
        String selection = "";

        while (!(selection.equals("v") || selection.equals("m") || selection.equals("h") || selection.equals("n"))) {
            displaySyrupFlavourOptions();
            selection = input.next();
            selection = selection.toLowerCase();
            processSyrupFlavourOptionCommand(newDrink, selection);
        }
    }

    //EFFECTS: allows user to add additional requests for drink
    private void addAdditionalRequests(Drink newDrink) {
        String selection;
        displayAdditionalRequestsOptions();
        selection = input.next();
        selection = selection.toLowerCase();
        processAdditionalRequestsOptionCommand(newDrink, selection);
    }

    //MODIFIES: order
    //EFFECTS: modifies/deletes drink in order
    @SuppressWarnings("methodlength")
    private void modifyOrder(Order order) {
        String userInput;
        int integer;
        int drinkIndex = -1;
        Drink drink;

        while (drinkIndex < 0 || drinkIndex >= order.getDrinks().size()) {
            System.out.println("\nEnter the drink number that you would like to modify/delete:");
            userInput = input.next();
            if (!userInput.matches("\\d+")) {
                System.out.println("\nPlease enter an integer...");
            } else {
                integer = Integer.parseInt(userInput);
                drinkIndex = integer - 1;
                if (drinkIndex < 0 || drinkIndex >= order.getDrinks().size()) {
                    System.out.println("Invalid drink number...");
                }
            }
        }

        drink = order.getDrinks().get(drinkIndex);

        String mainSelection;
        boolean keepGoing = true;

        while (keepGoing) {
            System.out.println("\nPlease select: ");
            System.out.println("\tm -> modify drink");
            System.out.println("\td -> delete drink");
            System.out.println("\tf -> finish modification on the drink");

            mainSelection = input.next();
            mainSelection = mainSelection.toLowerCase();

            if (mainSelection.equals("m")) {
                String modifySelection = "";
                String[] validModifyInputs = {"s","f","m","a"};

                while (!(Arrays.asList(validModifyInputs).contains(modifySelection))) {
                    displayModifyOrderOptions();
                    modifySelection = input.next();
                    modifySelection = modifySelection.toLowerCase();
                    processModifyOrderOptions(drink, modifySelection);
                }
            } else if (mainSelection.equals("d")) {
                order.removeDrinkFromOrder(drink);
                keepGoing = false;
            } else if (mainSelection.equals("f")) {
                keepGoing = false;
            } else {
                System.out.println("Selection not valid...");
            }
        }

        displayDrinksOrdered(order);
        System.out.println("Your order has been modified.");
    }

    // EFFECTS: display modify order options
    public void displayModifyOrderOptions() {
        System.out.println("\nPlease select the option you would like to modify:");
        System.out.println("\ts -> size");
        System.out.println("\tm -> milk");
        System.out.println("\tf -> syrup flavour");
        System.out.println("\ta -> additional requests");
    }

    // MODIFIES: this
    // EFFECTS: processes user modify order command
    public void processModifyOrderOptions(Drink drink, String selection) {
        if (selection.equals("s")) {
            selectSize(drink);
        } else if (selection.equals("m")) {
            selectMilk(drink);
        } else if (selection.equals("f")) {
            selectSyrupFlavour(drink);
        } else if (selection.equals("a")) {
            addAdditionalRequests(drink);
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //EFFECTS: allows manager to access customer and financial information
    //         of the coffee shop
    @SuppressWarnings("methodlength")
    public void doCoffeeShopSummary() {
        String name = "";

        while (!name.equals(manager.getName())) {
            System.out.print("Hi! Please enter your name: ");
            name = input.next();
            name = name.toLowerCase();
            if (!name.equals(manager.getName())) {
                System.out.println("Invalid manager name...");
            }
        }

        String password = "";

        while (!password.equals(manager.getPassword())) {
            System.out.print("Please enter your password: ");
            password = input.next();
            password = password.toLowerCase();
            if (!password.equals(manager.getPassword())) {
                System.out.println("Invalid password...");
            }
        }

        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayCoffeeShopSummaryOptions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCoffeeShopSummaryCommand(command);
            }
        }
    }

    private void displayCoffeeShopSummaryOptions() {
        System.out.println("\nPlease select summary options:");
        System.out.println("\tt -> total sales revenue");
        System.out.println("\tl -> list of customers");
        System.out.println("\to -> orders from a customer");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user drink size option command
    public void processCoffeeShopSummaryCommand(String command) {
        if (command.equals("t")) {
            doTotalSalesRevenue();
        } else if (command.equals("l")) {
            doListOfCustomers();
        } else if (command.equals("o")) {
            doOrdersByCustomer();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: prints the total sales revenue
    private void doTotalSalesRevenue() {
        System.out.println("$" + String.format("%.2f", manager.getTotalSalesRevenue()));
    }

    //EFFECTS: prints a list of all the past customers
    private void doListOfCustomers() {
        System.out.println("List of all customers: ");
        for (Customer customer : manager.getListOfAllCustomers()) {
            System.out.println("\t" + customer.getName());
        }
    }

    //EFFECTS: prints the t
    private void doOrdersByCustomer() {
        String name;
        System.out.println("Enter customer name: ");
        name = input.next();
        name = name.toLowerCase();

        if (manager.returningCustomer(name) != null) {
            int count = 1;
            for (Order order: manager.returningCustomer(name).getOrderHistory()) {
                System.out.println("Order " + count + ":");
                displayDrinksOrdered(order);
                count++;
            }
        } else {
            System.out.println("Customer not found.");
        }
    }

    // EFFECTS: saves the coffee shop to file
    private void saveManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Saved " + manager.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads coffee shop from file
    private void loadManager() {
        try {
            manager = jsonReader.read();
            System.out.println("Loaded " + manager.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}