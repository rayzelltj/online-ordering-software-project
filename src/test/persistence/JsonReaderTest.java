package persistence;

import model.Customer;
import model.Drink;
import model.Manager;
import model.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// JSON reader test class (modelled using JsonSerializationDemo)
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Manager manager = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoCustomers() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCoffeeShop.json");
        try {
            Manager manager = reader.read();
            assertEquals("rayzell", manager.getName());
            assertEquals(0, manager.getTotalSalesRevenue());
            assertEquals(0, manager.getListOfAllCustomers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralManager() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCoffeeShop.json");
        try {
            Manager manager = reader.read();
            assertEquals("rayzell", manager.getName());
            assertEquals(36.00, manager.getTotalSalesRevenue());
            ArrayList<Customer> customers = manager.getListOfAllCustomers();
            assertEquals(2, customers.size());

            Customer customer1 = customers.get(0);
            Customer customer2 = customers.get(1);
            checkCustomer("john", 11.40, customer1);
            checkCustomer("bob", 24.60, customer2);

            ArrayList<Order> orderHistory1 = customer1.getOrderHistory();
            ArrayList<Order> orderHistory2 = customer2.getOrderHistory();

            Order order1 = orderHistory1.get(0);
            Order order2 = orderHistory1.get(1);
            Order order3 = orderHistory2.get(0);
            checkOrder(5.35, order1);
            checkOrder(6.05, order2);
            checkOrder(24.60, order3);

            Drink drink1 = order1.getDrinks().get(0);
            Drink drink2 = order2.getDrinks().get(0);
            Drink drink3 = order3.getDrinks().get(0);
            Drink drink4 = order3.getDrinks().get(1);
            Drink drink5 = order3.getDrinks().get(2);

            checkDrink("Latte", 5.35, "medium", null, null, null, drink1);
            checkDrink("Cappuccino", 6.05, "large", null, null, null, drink2);
            checkDrink("Matcha Latte", 8.50, "large", "almond", "hazelnut", "extra hot", drink3);
            checkDrink("Belgian Mocha", 9.10, "large", "oat", "vanilla", null, drink4);
            checkDrink("Caramel Macchiato", 7.00, "medium", null, "maple", null, drink5);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
