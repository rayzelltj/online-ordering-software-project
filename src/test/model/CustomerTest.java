package model;

import model.drinks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private Customer customer;
    private Order orderOne;
    private Order orderTwo;
    private Order orderThree;


    @BeforeEach
    public void runBefore() {
        customer = new Customer("john mayer");

        BelgianMocha belgianMocha = new BelgianMocha();

        Cappuccino cappuccino = new Cappuccino();
        cappuccino.setSizeOption("l");

        CaramelMacchiato caramelMacchiato = new CaramelMacchiato();
        caramelMacchiato.setMilkOption("o");

        Latte latte = new Latte();
        latte.setFlavourOption("v");

        MatchaLatte matchaLatte = new MatchaLatte();

        orderOne = new Order();
        orderOne.addDrinkToOrder(belgianMocha);

        orderTwo = new Order();
        orderTwo.addDrinkToOrder(cappuccino);
        orderTwo.addDrinkToOrder(caramelMacchiato);

        orderThree = new Order();
        orderThree.addDrinkToOrder(latte);
        orderThree.addDrinkToOrder(matchaLatte);
    }

    @Test
    public void customerTest() {
        assertEquals("john mayer", customer.getName());
        assertTrue(customer.getOrderHistory().isEmpty());
        assertEquals(0.00, customer.getTotalSalesByCustomer());
    }

    @Test
    public void addToOrderHistoryTest() {
        customer.addToOrderHistory(orderOne);
        assertEquals(1, customer.getOrderHistory().size());
        assertEquals(orderOne, customer.getOrderHistory().get(0));
        assertEquals(6.40, customer.getTotalSalesByCustomer(), 0.001);

        customer.addToOrderHistory(orderTwo);
        assertEquals(2, customer.getOrderHistory().size());
        assertEquals(orderTwo, customer.getOrderHistory().get(1));
        assertEquals(19.45, customer.getTotalSalesByCustomer(), 0.001);

        customer.addToOrderHistory(orderThree);
        assertEquals(3, customer.getOrderHistory().size());
        assertEquals(orderThree, customer.getOrderHistory().get(2));
        assertEquals(31.6, customer.getTotalSalesByCustomer(), 0.001);

    }

    @Test
    public void getOrderHistoryTest() {
        customer.addToOrderHistory(orderOne);
        assertEquals(orderOne, customer.getOrderHistory().get(0));

        customer.addToOrderHistory(orderTwo);
        assertEquals(orderTwo, customer.getOrderHistory().get(1));

        customer.addToOrderHistory(orderThree);
        assertEquals(orderThree, customer.getOrderHistory().get(2));

        assertEquals(3, customer.getOrderHistory().size());
    }

    @Test
    public void getNameTest() {
        assertEquals("john mayer", customer.getName());
    }

    @Test
    public void getTotalSalesByCustomerTest() {
        customer.addToOrderHistory(orderOne);
        assertEquals(6.40, customer.getTotalSalesByCustomer(), 0.001);

        customer.addToOrderHistory(orderTwo);
        assertEquals(19.45, customer.getTotalSalesByCustomer(), 0.001);

        customer.addToOrderHistory(orderThree);
        assertEquals(31.6, customer.getTotalSalesByCustomer(), 0.001);
    }
}
