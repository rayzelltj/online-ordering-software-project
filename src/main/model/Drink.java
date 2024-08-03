package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a drink with a name, price, size, milk option,
// syrup flavour option, and additional requests
public abstract class Drink implements Writable {
    private final String name;
    private double price;
    private String sizeOption;
    private String milkOption;
    private String flavourOption;
    private String additionalRequests;

    // EFFECTS: constructs a drink with a chosen name and price;
    //          by default, the constructor sets:
    //          size as "Medium",
    //          milk as null (regular milk),
    //          flavour as null (no syrup),
    //          and additional requests as null (no additional requests)
    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
        this.sizeOption = "medium";
        this.milkOption = null;
        this.flavourOption = null;
        this.additionalRequests = null;
    }

    // REQUIRES: price is number with 2 decimal places > 0
    // MODIFIES: this
    // EFFECTS: sets the price of the drink
    public void setPrice(double price) {
        this.price = price;
    }

    // REQUIRES: size option must be one of:
    //           "m" - Medium
    //           "l" - Large
    // MODIFIES: this
    // EFFECTS: sets the size of the drink to medium or large;
    //          if size is large, charge an extra $0.70
    public void setSizeOption(String sizeOption) {
        if (this.sizeOption.equals("medium")) {
            if (sizeOption.equals("l")) {
                price += 0.70;
            }
        } else {
            if (sizeOption.equals("m")) {
                price -= 0.70;
            }
        }

        if (sizeOption.equals("m")) {
            this.sizeOption = "medium";
        } else {
            this.sizeOption = "large";
        }
    }

    // REQUIRES: milk option must be one of:
    //           "a" - almond
    //           "o" - oat
    //           "s" - soy
    //           "n" - null (regular)
    // MODIFIES: this
    // EFFECTS: sets the choice of milk, null for regular milk;
    //          if not using regular milk, charge an extra $1.00
    public void setMilkOption(String milkOption) {
        if (this.milkOption == null) {
            if (!milkOption.equals("n")) {
                this.price++;
            }
        } else {
            if (milkOption.equals("n")) {
                this.price--;
            }
        }

        if (milkOption.equals("a")) {
            this.milkOption = "almond";
        } else if (milkOption.equals("o")) {
            this.milkOption = "oat";
        } else if (milkOption.equals("s")) {
            this.milkOption = "soy";
        } else {
            this.milkOption = null;
        }
    }

    // REQUIRES: syrup flavour option must be one of:
    //           "v" - vanilla
    //           "m" - maple
    //           "h" - hazelnut
    //           "n" - null (no syrup)
    // MODIFIES: this
    // EFFECTS: sets the choice of syrup flavour, null for no syrup;
    //          if syrup is requested, charge extra $1.00
    public void setFlavourOption(String flavourOption) {
        if (this.flavourOption == null) {
            if (!flavourOption.equals("n")) {
                this.price++;
            }
        } else {
            if (flavourOption.equals("n")) {
                this.price--;
            }
        }

        if (flavourOption.equals("v")) {
            this.flavourOption = "vanilla";
        } else if (flavourOption.equals("m")) {
            this.flavourOption = "maple";
        } else if (flavourOption.equals("h")) {
            this.flavourOption = "hazelnut";
        } else {
            this.flavourOption = null;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets additional requests for the drink;
    //          enter "n" (null) for no additional requests
    public void setAdditionalRequests(String additionalRequests) {
        if (additionalRequests.equals("n")) {
            this.additionalRequests = null;
        } else {
            this.additionalRequests = additionalRequests;
        }
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSizeOption() {
        return sizeOption;
    }

    public String getMilkOption() {
        return milkOption;
    }

    public String getFlavourOption() {
        return flavourOption;
    }

    public String getAdditionalRequests() {
        return additionalRequests;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("size", sizeOption);
        json.put("milk", milkOption);
        json.put("flavour", flavourOption);
        json.put("additional requests", additionalRequests);
        return json;
    }
}
