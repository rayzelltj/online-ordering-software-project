package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents an order made by a customer comprised of
// a list of drinks and the total price

public class Order implements Writable {
    private ArrayList<Drink> drinks;
    private double totalPriceOfOrder;

    // EFFECTS: constructs an order with an empty list of drinks
    //          and total price of $0.00
    public Order() {
        this.drinks = new ArrayList<>();
        this.totalPriceOfOrder = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds drink to order & adds the price of drink
    //          to total price of order
    public void addDrinkToOrder(Drink drink) {
        this.drinks.add(drink);
        this.totalPriceOfOrder += drink.getPrice();
        EventLog.getInstance().logEvent(new Event("Drink added to order: " + drink.getName()));
    }

    // REQUIRES: drink exists in the order
    // MODIFIES: this
    // EFFECTS: removes the drink from the order & deducts the price
    //          of the drink from the total price of the order
    public void removeDrinkFromOrder(Drink drink) {
        this.drinks.remove(drink);
        this.totalPriceOfOrder -= drink.getPrice();
        EventLog.getInstance().logEvent(new Event("Drink removed from order: " + drink.getName()));
    }

    public ArrayList<Drink> getDrinks() {
        return drinks;
    }

    public double getTotalPriceOfOrder() {
        totalPriceOfOrder = 0;
        for (Drink drink : drinks) {
            totalPriceOfOrder += drink.getPrice();
        }
        return totalPriceOfOrder;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("drinks order", drinksToJson());
        json.put("total price of order", totalPriceOfOrder);
        return json;
    }

    // EFFECTS: returns list of drinks in an order as a JSON array (cite)
    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink drink : drinks) {
            jsonArray.put(drink.toJson());
        }

        return jsonArray;
    }

}
