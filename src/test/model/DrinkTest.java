package model;

import model.drinks.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DrinkTest {
    private Drink latte;

    @BeforeEach
    public void runBefore() {
        latte = new Latte();
    }

    @Test
    public void drinkTest() {
        assertEquals("Latte", latte.getName());
        assertEquals(5.35, latte.getPrice());
        assertEquals("medium", latte.getSizeOption());
        assertEquals(null, latte.getMilkOption());
        assertEquals(null, latte.getFlavourOption());
        assertEquals(null, latte.getAdditionalRequests());
    }

    @Test
    public void setPriceTest() {
        latte.setPrice(0.00);
        assertEquals(0.00, latte.getPrice());
        latte.setPrice(5.00);
        assertEquals(5.00, latte.getPrice());
    }

    @Test
    public void setSizeOptionTest() {
        latte.setSizeOption("l");
        assertEquals("large", latte.getSizeOption());
        assertEquals(6.05, latte.getPrice(), 0.001);

        latte.setSizeOption("l");
        assertEquals("large", latte.getSizeOption());
        assertEquals(6.05, latte.getPrice(), 0.001);

        latte.setSizeOption("m");
        assertEquals("medium", latte.getSizeOption());
        assertEquals(5.35, latte.getPrice(), 0.001);

        latte.setSizeOption("m");
        assertEquals("medium", latte.getSizeOption());
        assertEquals(5.35, latte.getPrice(), 0.001);
    }

    @Test
    public void setMilkOptionTest() {
        latte.setMilkOption("n");
        assertEquals(null, latte.getMilkOption());
        assertEquals(5.35, latte.getPrice(), 0.001);

        latte.setMilkOption("a");
        assertEquals("almond", latte.getMilkOption());
        assertEquals(6.35, latte.getPrice(), 0.001);

        latte.setMilkOption("o");
        assertEquals("oat", latte.getMilkOption());
        assertEquals(6.35, latte.getPrice(), 0.001);

        latte.setMilkOption("s");
        assertEquals("soy", latte.getMilkOption());
        assertEquals(6.35, latte.getPrice(), 0.001);

        latte.setMilkOption("n");
        assertEquals(null, latte.getMilkOption());
        assertEquals(5.35, latte.getPrice(), 0.001);
    }

    @Test
    public void setFlavourOptionTest() {
        latte.setFlavourOption("n");
        assertEquals(null, latte.getFlavourOption());
        assertEquals(5.35, latte.getPrice());

        latte.setFlavourOption("v");
        assertEquals("vanilla", latte.getFlavourOption());
        assertEquals(6.35, latte.getPrice());

        latte.setFlavourOption("m");
        assertEquals("maple", latte.getFlavourOption());
        assertEquals(6.35, latte.getPrice());

        latte.setFlavourOption("h");
        assertEquals("hazelnut", latte.getFlavourOption());
        assertEquals(6.35, latte.getPrice());

        latte.setFlavourOption("n");
        assertEquals(null, latte.getFlavourOption());
        assertEquals(5.35, latte.getPrice());
    }

    @Test
    public void setAdditionalRequestsTest() {
        latte.setAdditionalRequests("n");
        assertEquals(null, latte.getAdditionalRequests());

        latte.setAdditionalRequests("extra hot");
        assertEquals("extra hot", latte.getAdditionalRequests());

        latte.setAdditionalRequests("add cinnamon powder");
        assertEquals("add cinnamon powder", latte.getAdditionalRequests());
    }

    @Test
    public void getNameTest() {
        assertEquals("Latte", latte.getName());
    }
    @Test
    public void getPriceTest() {
        assertEquals(5.35, latte.getPrice());
    }

    @Test
    public void getSizeOptionTest() {
        latte.setSizeOption("m");
        assertEquals("medium", latte.getSizeOption());

        latte.setSizeOption("l");
        assertEquals("large", latte.getSizeOption());
    }

    @Test
    public void getMilkOptionTest() {
        latte.setMilkOption("n");
        assertEquals(null, latte.getMilkOption());

        latte.setMilkOption("a");
        assertEquals("almond", latte.getMilkOption());

        latte.setMilkOption("o");
        assertEquals("oat", latte.getMilkOption());

        latte.setMilkOption("s");
        assertEquals("soy", latte.getMilkOption());

        latte.setMilkOption("n");
        assertEquals(null, latte.getMilkOption());
    }

    @Test
    public void getFlavourOptionTest() {
        latte.setFlavourOption("n");
        assertEquals(null, latte.getFlavourOption());

        latte.setFlavourOption("v");
        assertEquals("vanilla", latte.getFlavourOption());

        latte.setFlavourOption("m");
        assertEquals("maple", latte.getFlavourOption());

        latte.setFlavourOption("h");
        assertEquals("hazelnut", latte.getFlavourOption());

        latte.setFlavourOption("n");
        assertEquals(null, latte.getFlavourOption());
    }

    @Test
    public void getAdditionalRequestsTest() {
        latte.setAdditionalRequests("n");
        assertEquals(null, latte.getAdditionalRequests());

        latte.setAdditionalRequests("extra hot");
        assertEquals("extra hot", latte.getAdditionalRequests());
    }
}