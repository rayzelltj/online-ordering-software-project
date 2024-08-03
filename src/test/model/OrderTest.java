package model;

import model.drinks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private Order order;
    private BelgianMocha belgianMocha;
    private Cappuccino cappuccino;
    private CaramelMacchiato caramelMacchiato;
    private Latte latte;
    private MatchaLatte matchaLatte;

    @BeforeEach
    public void runBefore() {
        order = new Order();
        belgianMocha = new BelgianMocha();
        cappuccino = new Cappuccino();
        caramelMacchiato = new CaramelMacchiato();
        latte = new Latte();
        matchaLatte = new MatchaLatte();
    }

    @Test
    public void orderTest() {
        assertTrue(order.getDrinks().isEmpty());
        assertEquals(0.00, order.getTotalPriceOfOrder());
    }

    @Test
    public void addDrinkToOrderTest() {
        order.addDrinkToOrder(belgianMocha);
        assertEquals(belgianMocha, order.getDrinks().get(0));
        assertEquals(6.40, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(cappuccino);
        assertEquals(cappuccino, order.getDrinks().get(1));
        assertEquals(11.75, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(caramelMacchiato);
        assertEquals(caramelMacchiato, order.getDrinks().get(2));
        assertEquals(17.75, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(latte);
        assertEquals(latte, order.getDrinks().get(3));
        assertEquals(23.10, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(matchaLatte);
        assertEquals(matchaLatte, order.getDrinks().get(4));
        assertEquals(28.90, order.getTotalPriceOfOrder(), 0.001);

        assertEquals(5, order.getDrinks().size());
    }

    @Test
    public void removeDrinkFromOrder() {
        order.addDrinkToOrder(latte);
        order.addDrinkToOrder(belgianMocha);
        order.addDrinkToOrder(matchaLatte);
        order.addDrinkToOrder(caramelMacchiato);
        order.addDrinkToOrder(cappuccino);

        assertEquals(28.90, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(5, order.getDrinks().size());

        order.removeDrinkFromOrder(matchaLatte);
        assertEquals(23.10, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(4, order.getDrinks().size());

        order.removeDrinkFromOrder(latte);
        assertEquals(17.75, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(3, order.getDrinks().size());

        order.removeDrinkFromOrder(caramelMacchiato);
        assertEquals(11.75, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(2, order.getDrinks().size());

        order.removeDrinkFromOrder(cappuccino);
        assertEquals(6.40, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(1, order.getDrinks().size());

        order.removeDrinkFromOrder(belgianMocha);
        assertEquals(0, order.getTotalPriceOfOrder(), 0.001);
        assertEquals(0, order.getDrinks().size());
    }

    @Test
    public void getOrderTest() {
        order.addDrinkToOrder(belgianMocha);
        assertEquals(belgianMocha, order.getDrinks().get(0));

        order.addDrinkToOrder(cappuccino);
        assertEquals(cappuccino, order.getDrinks().get(1));

        order.addDrinkToOrder(caramelMacchiato);
        assertEquals(caramelMacchiato, order.getDrinks().get(2));

        order.addDrinkToOrder(latte);
        assertEquals(latte, order.getDrinks().get(3));

        order.addDrinkToOrder(matchaLatte);
        assertEquals(matchaLatte, order.getDrinks().get(4));
    }

    @Test
    public void getTotalPriceOfOrderTest() {
        assertEquals(0, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(belgianMocha);
        assertEquals(6.40, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(cappuccino);
        assertEquals(11.75, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(caramelMacchiato);
        assertEquals(17.75, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(latte);
        assertEquals(23.10, order.getTotalPriceOfOrder(), 0.001);

        order.addDrinkToOrder(matchaLatte);
        assertEquals(28.90, order.getTotalPriceOfOrder(), 0.001);
    }
}
