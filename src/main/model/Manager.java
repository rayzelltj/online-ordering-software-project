package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a manager that has access to the list of all customers
// and the total sales revenue

public class Manager implements Writable {
    private final String name;
    private final String password;
    private ArrayList<Customer> listOfAllCustomers;
    private double totalSalesRevenue;

    // EFFECTS: constructs a manager account with a name
    //          and password; sets the list of customers to empty
    //          and the total sales revenue to $0.00
    public Manager() {
        this.name = "rayzell";
        this.password = "cpsc210project";
        listOfAllCustomers = new ArrayList<>();
        this.totalSalesRevenue = 0;
    }

    // EFFECTS: returns the customer if the customer is a returning customer,
    //          else returns null
    public Customer returningCustomer(String name) {
        for (Customer customer : listOfAllCustomers) {
            if (customer.getName().equals(name)) {
                return customer;
            }
        }
        return null;
    }


    // REQUIRES: customer name does not exist in the list of customers
    // MODIFIES: this
    // EFFECTS: adds customer to the list of all customers
    public void addCustomerToListOfAllCustomers(Customer customer) {
        listOfAllCustomers.add(customer);
        EventLog.getInstance().logEvent(new Event("Customer ordering: " + customer.getName()));
    }

    // MODIFIES: this
    // EFFECTS: add the order's total price to the total sales revenue
    public void addToTotalSalesRevenue(Order order) {
        totalSalesRevenue += order.getTotalPriceOfOrder();
    }

    // MODIFIES: this
    // EFFECTS: return total sales from a customer given the name;
    //          return -1 if customer is not found
    public double getTotalSalesByCustomer(String name) {
        for (Customer customer : listOfAllCustomers) {
            if (customer.getName().equals(name)) {
                return customer.getTotalSalesByCustomer();
            }
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<Customer> getListOfAllCustomers() {
        return listOfAllCustomers;
    }

    public double getTotalSalesRevenue() {
        return totalSalesRevenue;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("manager name", name);
        json.put("customers", customersToJson());
        json.put("total sales revenue", totalSalesRevenue);
        return json;
    }

    // EFFECTS: returns customers handled by this manager as a JSON array (cite)
    private JSONArray customersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer customer : listOfAllCustomers) {
            jsonArray.put(customer.toJson());
        }

        return jsonArray;
    }
}
