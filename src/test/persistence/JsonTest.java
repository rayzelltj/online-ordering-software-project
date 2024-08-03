package persistence;

import model.Customer;
import model.Drink;
import model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JSON test class (modelled using JsonSerializationDemo)
public class JsonTest {
    protected void checkCustomer(String name, double totalSales, Customer customer) {
        assertEquals(name, customer.getName());
        assertEquals(totalSales, customer.getTotalSalesByCustomer(),0.001);
    }

    protected void checkOrder(double totalPrice, Order order) {
        assertEquals(totalPrice, order.getTotalPriceOfOrder(), 0.001);
    }

    protected void checkDrink(String name, double price, String sizeOption, String milkOption, String flavourOption,
                              String additionalRequests, Drink drink) {
        assertEquals(name, drink.getName());
        assertEquals(price, drink.getPrice(),0.001);
        assertEquals(sizeOption, drink.getSizeOption());
        assertEquals(milkOption, drink.getMilkOption());
        assertEquals(flavourOption, drink.getFlavourOption());
        assertEquals(additionalRequests, drink.getAdditionalRequests());
    }
}
