package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.drinks.*;
import org.json.*;

// Represents a reader that reads manager data, including list of customers and their
// respective orders, from JSON data stored in file (modelled using JsonSerializationDemo)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads manager from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Manager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded coffee shop data."));
        return parseManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses manager from JSON object and returns it
    private Manager parseManager(JSONObject jsonObject) {
        Manager manager = new Manager();
        addCustomers(manager, jsonObject);
        return manager;
    }

    // MODIFIES: manager
    // EFFECTS: parses list of customers from JSON object and adds them to manager
    private void addCustomers(Manager manager, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customers");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomer(manager, nextCustomer);
        }
    }

    // MODIFIES: manager
    // EFFECTS: parses customer from JSON object and adds it to manager
    private void addCustomer(Manager manager, JSONObject jsonObject) {
        String name = jsonObject.getString("customer name");
        Customer customer = new Customer(name);
        addOrders(manager, customer, jsonObject);
        manager.addCustomerToListOfAllCustomers(customer);
    }

    // MODIFIES: customer
    // EFFECTS: parses list of orders from JSON object and adds them to customer
    private void addOrders(Manager manager, Customer customer, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("order history");
        for (Object json : jsonArray) {
            JSONObject nextOrder = (JSONObject) json;
            addOrder(manager, customer, nextOrder);
        }
    }

    // MODIFIES: customer
    // EFFECTS: parses orders from JSON object and adds them to customer
    private void addOrder(Manager manager, Customer customer, JSONObject jsonObject) {
        Order order = new Order();
        addDrinks(order, jsonObject);
        customer.addToOrderHistory(order);
        manager.addToTotalSalesRevenue(order);
    }

    // MODIFIES: order
    // EFFECTS: parses list of drinks from JSON object and adds them to order
    private void addDrinks(Order order, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("drinks order");
        for (Object json : jsonArray) {
            JSONObject nextDrink = (JSONObject) json;
            addDrink(order, nextDrink);
        }
    }

    // MODIFIES: order
    // EFFECTS: parses drink from JSON object and adds them to order
    @SuppressWarnings("methodlength")
    private void addDrink(Order order, JSONObject jsonObject) {
        Drink drink;

        String name = jsonObject.getString("name");
        String size = jsonObject.getString("size");
        String milk = jsonObject.optString("milk");
        String flavour = jsonObject.optString("flavour");
        String additionalRequests = jsonObject.optString("additional requests");

        if (name.equals("Belgian Mocha")) {
            drink = new BelgianMocha();
        } else if (name.equals("Cappuccino")) {
            drink = new Cappuccino();
        } else if (name.equals("Caramel Macchiato")) {
            drink = new CaramelMacchiato();
        } else if (name.equals("Latte")) {
            drink = new Latte();
        } else {
            drink = new MatchaLatte();
        }

        if (size.equals("medium")) {
            size = "m";
        } else {
            size = "l";
        }

        if (milk.equals("almond")) {
            milk = "a";
        } else if (milk.equals("oat")) {
            milk = "o";
        } else if (milk.equals("soy")) {
            milk = "s";
        } else {
            milk = "n";
        }

        if (flavour.equals("vanilla")) {
            flavour = "v";
        } else if (flavour.equals("maple")) {
            flavour = "m";
        } else if (flavour.equals("hazelnut")) {
            flavour = "h";
        } else {
            flavour = "n";
        }

        if (additionalRequests.equals("")) {
            additionalRequests = "n";
        } else {
            additionalRequests = additionalRequests;
        }

        drink.setSizeOption(size);
        drink.setMilkOption(milk);
        drink.setFlavourOption(flavour);
        drink.setAdditionalRequests(additionalRequests);

        order.addDrinkToOrder(drink);
    }
}
