package persistence;

import model.Customer;
import model.Drink;
import model.Manager;
import model.Order;
import model.drinks.BelgianMocha;
import model.drinks.Cappuccino;
import model.drinks.Latte;
import model.drinks.MatchaLatte;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// JSON writer test class (modelled using JsonSerializationDemo)
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Manager manager = new Manager();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Manager manager = new Manager();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCoffeeShop.json");
            writer.open();
            writer.write(manager);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCoffeeShop.json");
            manager = reader.read();
            assertEquals("rayzell", manager.getName());
            assertEquals(0, manager.getTotalSalesRevenue());
            assertEquals(0, manager.getListOfAllCustomers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Manager manager = new Manager();
            Customer john = new Customer("john");
            Customer bob = new Customer("bob");

            Order order1 = new Order();
            Order order2 = new Order();
            Order order3 = new Order();

            Latte latte = new Latte();
            Cappuccino cappuccino = new Cappuccino();
            BelgianMocha belgianMocha = new BelgianMocha();
            MatchaLatte matchaLatte = new MatchaLatte();

            cappuccino.setSizeOption("l");
            belgianMocha.setMilkOption("o");
            belgianMocha.setFlavourOption("v");
            matchaLatte.setMilkOption("s");
            matchaLatte.setAdditionalRequests("extra hot");

            order1.addDrinkToOrder(latte);
            order2.addDrinkToOrder(cappuccino);
            order3.addDrinkToOrder(belgianMocha);
            order3.addDrinkToOrder(matchaLatte);

            john.addToOrderHistory(order1);
            john.addToOrderHistory(order2);
            bob.addToOrderHistory(order3);

            manager.addCustomerToListOfAllCustomers(john);
            manager.addCustomerToListOfAllCustomers(bob);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCoffeeShop.json");
            writer.open();
            writer.write(manager);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCoffeeShop.json");
            manager = reader.read();
            assertEquals("rayzell", manager.getName());
            ArrayList<Customer> customers = manager.getListOfAllCustomers();
            assertEquals(2, customers.size());
            checkCustomer("john", 11.4, customers.get(0));
            checkCustomer("bob", 15.2, customers.get(1));

            Order readOrder1 = customers.get(0).getOrderHistory().get(0);
            Order readOrder2 = customers.get(0).getOrderHistory().get(1);
            Order readOrder3 = customers.get(1).getOrderHistory().get(0);

            checkOrder(5.35, readOrder1);
            checkOrder(6.05, readOrder2);
            checkOrder(15.2, readOrder3);

            Drink drink1 = readOrder1.getDrinks().get(0);
            Drink drink2 = readOrder2.getDrinks().get(0);
            Drink drink3 = readOrder3.getDrinks().get(0);
            Drink drink4 = readOrder3.getDrinks().get(1);

            checkDrink("Latte", 5.35, "medium", null, null, null, drink1);
            checkDrink("Cappuccino", 6.05, "large", null, null, null, drink2);
            checkDrink("Belgian Mocha", 8.40, "medium", "oat", "vanilla", null, drink3);
            checkDrink("Matcha Latte", 6.8, "medium", "soy", null, "extra hot", drink4);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
