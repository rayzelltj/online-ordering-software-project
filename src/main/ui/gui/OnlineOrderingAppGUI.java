package ui.gui;


import model.*;
import model.Event;
import model.drinks.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OnlineOrderingAppGUI extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/manager.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Manager manager;
    private Customer currentCustomer;
    private Order currentOrder;
    private Drink currentDrink;
    private final JFrame mainFrame;
    private final JPanel mainPanel;
    private final JPanel homePanel;
    private final JPanel customerPanel;
    private final JPanel orderPanel;
    private final JPanel menuPanel;
    private final JPanel drinkOptionsPanel;
    private final JPanel managerPanel;
    private JButton customerButton;
    private JButton managerButton;
    private JButton saveButton;
    private JButton loadButton;
    private final CardLayout mainCardLayout;
    private final CardLayout orderCardLayout;
    private DefaultTableModel tableModel;
    private JTextField nameField;

    public static void main(String[] args) {
        new OnlineOrderingAppGUI();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> printEventsOnExit()));
    }

    // EFFECTS: prints events log on exit
    public static void printEventsOnExit() {
        System.out.println("Start ordering application.");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.getDate() + " - " + event.getDescription());
        }
        System.out.println("Quit online ordering application.");
        EventLog.getInstance().clear();
    }

    // EFFECTS: initializes coffee shop online ordering app
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private OnlineOrderingAppGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        mainFrame = new JFrame("Online Ordering App");
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainCardLayout = new CardLayout();
        mainPanel.setLayout(mainCardLayout);

        // Set up home panel
        homePanel = new JPanel();
        homePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initHomePanel();

        // Set up customer panel
        customerPanel = new JPanel();
        customerPanel.setLayout(null);
        initCustomerPanel();

        orderPanel = new JPanel();
        orderPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        orderCardLayout = new CardLayout();
        orderPanel.setLayout(orderCardLayout);

        // Set up drink menu panel
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        initMenuPanel();

        // Set Up drink options panel
        drinkOptionsPanel = new JPanel();
        drinkOptionsPanel.setLayout(null);
        initDrinkOptionsPanel();

        // Set up manager panel
        managerPanel = new JPanel();
        managerPanel.setLayout(null);

        mainPanel.add(homePanel, "Home");
        mainPanel.add(customerPanel, "Customer");
        mainPanel.add(orderPanel, "Ordering");
        mainPanel.add(managerPanel, "Manager");

        orderPanel.add(menuPanel, "Menu");
        orderPanel.add(drinkOptionsPanel, "Drink Options");

        manager = new Manager();

        mainFrame.add(mainPanel);
        mainCardLayout.show(mainPanel, "Home");
        orderCardLayout.show(orderPanel, "Menu");
        mainFrame.setVisible(true);
    }

    // MODIFIES: homePanel
    // EFFECTS: initializes home panel
    private void initHomePanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        customerButton = new JButton("Customer");
        customerButton.setPreferredSize(new Dimension(200, 100));
        customerButton.addActionListener(this);

        //managerButton = new JButton("Manager");
        //managerButton.setPreferredSize(new Dimension(200, 100));
        //managerButton.addActionListener(this);

        saveButton = new JButton("Save Data");
        saveButton.setPreferredSize(new Dimension(200, 100));
        saveButton.addActionListener(this);

        loadButton = new JButton("Load Data");
        loadButton.setPreferredSize(new Dimension(200, 100));
        loadButton.addActionListener(this);

        buttonPanel.add(customerButton);
        //buttonPanel.add(managerButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        homePanel.add(buttonPanel);
    }

    //EFFECTS: actionListener for main panel buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == customerButton) {
            mainCardLayout.show(mainPanel, "Customer");
        } else if (e.getSource() == managerButton) {
            mainCardLayout.show(mainPanel, "Manager");
        } else if (e.getSource() == saveButton) {
            saveManager();
        } else if (e.getSource() == loadButton) {
            loadManager();
        }
    }

    // MODIFIES: customerPanel
    // EFFECTS: initialize customer panel
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void initCustomerPanel() {
        JPanel newCustomerPanel = new JPanel();
        newCustomerPanel.setBounds(0, 0, WIDTH, HEIGHT);
        JLabel nameLabel = new JLabel("Your Name:");
        nameField = new JTextField(20);
        JButton proceedButton = new JButton("Proceed");

        newCustomerPanel.add(nameLabel);
        newCustomerPanel.add(nameField);
        newCustomerPanel.add(proceedButton);

        customerPanel.add(newCustomerPanel);

        proceedButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) { // Check if the name is not empty
                Customer newCustomer;
                Order newOrder = new Order();
                if (manager.returningCustomer(name) == null) {
                    newCustomer = new Customer(name);
                    manager.addCustomerToListOfAllCustomers(newCustomer);
                } else {
                    newCustomer = manager.returningCustomer(name);
                }
                currentCustomer = newCustomer;
                currentOrder = newOrder;
                mainCardLayout.show(mainPanel, "Ordering");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a name.");
            }
        });
    }

    // MODIFIES: drinkMenu
    // EFFECTS: initializes ordering panel with drink menu
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void initMenuPanel() {
        JPanel menuDisplayPanel = new JPanel();
        menuDisplayPanel.setBounds(0, 0, WIDTH / 2, HEIGHT);
        menuDisplayPanel.setLayout(new GridLayout(3,2));
        menuDisplayPanel.setBackground(Color.blue);

        ImageIcon belgianMochaIcon = iconScalar("src/main/images/belgianmocha.png");
        JButton belgianMochaButton = drinkButtonMaker("Belgian Mocha", belgianMochaIcon);

        ImageIcon cappuccinoIcon = iconScalar("src/main/images/cappuccino.png");
        JButton cappuccinoButton = drinkButtonMaker("Cappuccino", cappuccinoIcon);

        ImageIcon caramelMacchiatoIcon = iconScalar("src/main/images/caramelmacchiato.png");
        JButton caramelMacchiatoButton = drinkButtonMaker("Caramel Macchiato", caramelMacchiatoIcon);

        ImageIcon latteIcon = iconScalar("src/main/images/latte.png");
        JButton latteButton = drinkButtonMaker("Latte", latteIcon);

        ImageIcon matchaLatteIcon = iconScalar("src/main/images/matchalatte.png");
        JButton matchaLatteButton = drinkButtonMaker("Matcha Latte", matchaLatteIcon);

        menuDisplayPanel.add(belgianMochaButton);
        menuDisplayPanel.add(cappuccinoButton);
        menuDisplayPanel.add(caramelMacchiatoButton);
        menuDisplayPanel.add(latteButton);
        menuDisplayPanel.add(matchaLatteButton);

        this.menuPanel.add(menuDisplayPanel);

        JPanel orderDisplayPanel = new JPanel();
        orderDisplayPanel.setBounds(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        // Create the table model
        tableModel = new DefaultTableModel(0,0);
        final String[] header;
        header = new String[] {"Item", "Size", "Price"};
        tableModel.setColumnIdentifiers(header);
        tableModel.addRow(header);

        // Create the table
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        this.menuPanel.add(scrollPane, BorderLayout.CENTER);
        orderDisplayPanel.add(table);

        JButton deleteDrink = new JButton("Delete Drink");
        deleteDrink.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Ensure a row is selected
                // Get the name of the drink to be removed
                String drinkName = (String) tableModel.getValueAt(selectedRow, 0);

                // Iterate through the drinks in the current order
                for (Drink drink : currentOrder.getDrinks()) {
                    if (drink.getName().equals(drinkName)) {
                        // Remove the selected drink from the order
                        currentOrder.removeDrinkFromOrder(drink);
                        break; // Exit loop once the drink is found and removed
                    }
                }

                // Remove the selected row from the table
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a drink to delete.");
            }
        });

        orderDisplayPanel.add(deleteDrink);

        JButton finishOrder = new JButton("Finish Order");
        finishOrder.addActionListener(e -> {
            currentCustomer.addToOrderHistory(currentOrder);
            manager.addToTotalSalesRevenue(currentOrder);
            int rowCount = tableModel.getRowCount();
            for (int i = rowCount - 1; i >= 1; i--) {
                tableModel.removeRow(i);
            }
            nameField.setText("");
            mainCardLayout.show(mainPanel, "Home");
        });

        orderDisplayPanel.add(finishOrder);
        this.menuPanel.add(orderDisplayPanel);
    }

    // EFFECTS: scales given image based on image path and returns image icon
    public ImageIcon iconScalar(String imagePath) {
        ImageIcon latteImage = new ImageIcon(imagePath);
        Image img = latteImage.getImage();
        int width = 150;
        int height = 90;
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    // EFFECTS: constructs drink ordering buttons
    public JButton drinkButtonMaker(String drinkName, ImageIcon drinkIcon) {
        JButton drinkButton = new JButton(drinkName, drinkIcon);
        drinkButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        drinkButton.setHorizontalTextPosition(SwingConstants.CENTER);
        drinkButton.addActionListener(new AddDrinkAction(drinkName));
        return drinkButton;
    }

    // EFFECTS: action listener form drink buttons
    private class AddDrinkAction extends AbstractAction {
        String drinkName;

        AddDrinkAction(String drinkName) {
            super(drinkName);
            this.drinkName = drinkName;
        }

        //EFFECTS: action listener for drink buttons
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (this.drinkName.equals("Belgian Mocha")) {
                BelgianMocha belgianMocha = new BelgianMocha();
                orderCardLayout.show(orderPanel, "Drink Options");
                addDrink(belgianMocha);
            } else if (this.drinkName.equals("Cappuccino")) {
                Cappuccino cappuccino = new Cappuccino();
                orderCardLayout.show(orderPanel, "Drink Options");
                addDrink(cappuccino);
            } else if (this.drinkName.equals("Caramel Macchiato")) {
                CaramelMacchiato caramelMacchiato = new CaramelMacchiato();
                orderCardLayout.show(orderPanel, "Drink Options");
                addDrink(caramelMacchiato);
            } else if (this.drinkName.equals("Latte")) {
                Latte latte = new Latte();
                orderCardLayout.show(orderPanel, "Drink Options");
                addDrink(latte);
            } else if (this.drinkName.equals("Matcha Latte")) {
                MatchaLatte matchaLatte = new MatchaLatte();
                orderCardLayout.show(orderPanel, "Drink Options");
                addDrink(matchaLatte);
            }
        }
    }

    // MODIFIES: optionsPanel
    // EFFECTS: initializes options panel, allows user to select drink size
    private void initDrinkOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBounds(0, 0, WIDTH / 2, HEIGHT);
        optionsPanel.setLayout(new GridLayout(3,2));
        optionsPanel.setBackground(Color.blue);

        optionsPanel.add(new JButton(new AddDrinkOptionAction("Medium")));
        optionsPanel.add(new JButton(new AddDrinkOptionAction("Large")));

        drinkOptionsPanel.add(optionsPanel);
    }

    // EFFECTS: action listener form drink buttons
    private class AddDrinkOptionAction extends AbstractAction {
        String drinkSize;

        AddDrinkOptionAction(String drinkSize) {
            super(drinkSize);
            this.drinkSize = drinkSize;
        }

        // MODIFIES: currentDrink, tableModel
        // EFFECTS: sets the size of the current drink and adds drink to table display
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (this.drinkSize.equals("Medium")) {
                currentDrink.setSizeOption("m");
            } else if (this.drinkSize.equals("Large")) {
                currentDrink.setSizeOption("l");
            }
            orderCardLayout.show(orderPanel, "Menu");
            tableModel.addRow(new Object[]{
                    currentDrink.getName(),
                    currentDrink.getSizeOption(),
                    currentDrink.getPrice()
            });
        }
    }

    // MODIFIES: currentOrder, currentDrink
    // EFFECTS: adds drink to current order and sets current drink as drink
    private void addDrink(Drink drink) {
        currentOrder.addDrinkToOrder(drink);
        currentDrink = drink;
    }

    // EFFECTS: saves the coffee shop to file
    private void saveManager() {
        try {
            jsonWriter.open();
            jsonWriter.write(manager);
            jsonWriter.close();
            System.out.println("Saved " + manager.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads coffee shop from file
    private void loadManager() {
        try {
            manager = jsonReader.read();
            System.out.println("Loaded " + manager.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}