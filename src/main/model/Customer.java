package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a customer with a name, order history, and
// total sales from all purchases made

public class Customer implements Writable {
    private final String name;
    private ArrayList<Order> orderHistory;
    private double totalSalesByCustomer;

    // EFFECTS: constructs a customer with given name, an empty
    //          order history and a total sales of $0.00
    public Customer(String name) {
        this.name = name;
        this.orderHistory = new ArrayList<>();
        this.totalSalesByCustomer = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds an order made to the order history;
    //          update total sales by customer
    public void addToOrderHistory(Order order) {
        this.orderHistory.add(order);
        this.totalSalesByCustomer += order.getTotalPriceOfOrder();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public double getTotalSalesByCustomer() {
        return totalSalesByCustomer;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("customer name", name);
        json.put("order history", orderHistoryToJson());
        json.put("total sales by customer", totalSalesByCustomer);
        return json;
    }

    // EFFECTS: returns list of orders made by this manager as a JSON array (cite)
    private JSONArray orderHistoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Order order : orderHistory) {
            jsonArray.put(order.toJson());
        }

        return jsonArray;
    }
}
