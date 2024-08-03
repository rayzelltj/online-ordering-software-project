package model;

import model.drinks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ManagerTest {
    private Manager manager;
    private Customer customerOne;
    private Customer customerTwo;
    private Customer customerThree;
    private Order orderOne;
    private Order orderTwo;
    private Order orderThree;
    private Order orderFour;

    @BeforeEach
    public void runBefore() {
        manager = new Manager();

        customerOne = new Customer("john mayer");
        customerTwo = new Customer("scott lang");
        customerThree = new Customer("amy zhou");

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

        orderFour = new Order();
        orderFour.addDrinkToOrder(matchaLatte);

        customerOne.addToOrderHistory(orderOne);
        customerTwo.addToOrderHistory(orderTwo);
        customerTwo.addToOrderHistory(orderThree);
        customerThree.addToOrderHistory(orderFour);
    }

    @Test
    public void managerTest() {
        assertEquals("rayzell", manager.getName());
        assertEquals("cpsc210project", manager.getPassword());
        assertTrue(manager.getListOfAllCustomers().isEmpty());
        assertEquals(0, manager.getTotalSalesRevenue());
    }

    @Test
    public void returningCustomerTest() {
        manager.addCustomerToListOfAllCustomers(customerOne);
        manager.addCustomerToListOfAllCustomers(customerTwo);
        manager.addCustomerToListOfAllCustomers(customerThree);

        assertEquals(customerOne, manager.returningCustomer("john mayer"));
        assertEquals(customerTwo, manager.returningCustomer("scott lang"));
        assertEquals(customerThree, manager.returningCustomer("amy zhou"));
        assertEquals(null, manager.returningCustomer("jonathan wall"));
    }

    @Test
    public void addCustomerListOfAllCustomersTest() {
        manager.addCustomerToListOfAllCustomers(customerOne);
        assertEquals(1, manager.getListOfAllCustomers().size());

        manager.addCustomerToListOfAllCustomers(customerTwo);
        assertEquals(2, manager.getListOfAllCustomers().size());

        manager.addCustomerToListOfAllCustomers(customerThree);
        assertEquals(3, manager.getListOfAllCustomers().size());
    }

    @Test
    public void addToTotalSalesRevenueTest() {
        manager.addToTotalSalesRevenue(orderOne);
        assertEquals(6.40, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderTwo);
        assertEquals(19.45, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderThree);
        assertEquals(31.60, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderFour);
        assertEquals(37.40, manager.getTotalSalesRevenue(), 0.001);
    }

    @Test
    public void getTotalSalesByCustomerTest() {
        manager.addCustomerToListOfAllCustomers(customerOne);
        manager.addCustomerToListOfAllCustomers(customerTwo);
        manager.addCustomerToListOfAllCustomers(customerThree);

        assertEquals(6.40, manager.getTotalSalesByCustomer("john mayer"), 0.001);
        assertEquals(25.20, manager.getTotalSalesByCustomer("scott lang"), 0.001);
        assertEquals(5.80, manager.getTotalSalesByCustomer("amy zhou"), 0.001);
        assertEquals(-1 , manager.getTotalSalesByCustomer("jonathan wilson"), 0.001);
    }

    @Test
    public void getTotalSalesRevenueTest() {
        manager.addToTotalSalesRevenue(orderOne);
        assertEquals(6.40, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderTwo);
        assertEquals(19.45, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderThree);
        assertEquals(31.60, manager.getTotalSalesRevenue(), 0.001);

        manager.addToTotalSalesRevenue(orderFour);
        assertEquals(37.40, manager.getTotalSalesRevenue(), 0.001);
    }
}
