# My Personal Project

## Phase 0


- **What will the application do?**

This application will be dedicated to taking online orders from customers, creating a list with specific requests which will be sent to the coffee shop and displayed on a tablet. The application will have features that allow users to add, delete and modify order requests, and view individual and total prices. When it comes to ordering drinks, orders can get really complex as each customer has unique preferences. For example, a simple latte drink can be made complex when a customer wants a breve latte made with oat milk, sugar-free vanilla syrup, some cinnamon powder on top, and some additional requests that may seem a little peculiar. The barista working at the coffee shop must know exactly what to make and the ingredients required to ensure 100% customer satisfaction. Therefore, a clear display of a list of an online order is important for the reputation of the coffee shop. This application will store all the online orders made, and have the order history of each customer. A manager would be able to access the daily, weekly, monthly, and annual sales.

- **Who will use it?**

Coffee shops who take online orders, like uber takeouts, will be able to use this application. With some variations, that application can be modified to take any type of order, and therefore can be used by other restaurants as well. Managers will handle the backend of the application and will be able to view specific customer and sales information.

- **Why is this project of interest to you?**

This project idea stemmed from my recent experience in working as a barista in a coffee shop. As a person who only orders a regular cappuccino, I’ve come to realise how complex drink orders can get with different customers, and how important it is to get every detail right to satisfy the customers.

## *User Stories*
- As a customer, I want to be able to order a drink with specific requests (size, milk type, syrup flavour, and additional requests).
- As a customer, I want to be able to delete or modify a drink from the list of orders.
- As a customer, I want to be able to view a list of my current orders.
- As a customer, I want to be able to view the price of each drink and the total price at the bottom of the list of drink orders.
- As a manager, I want to be able to view a list of all the customers that placed orders.
- As a manager, I want to be able to view a list of the sales of each customer and the total sales revenue.
- As a manager, I want to be able to save a record of all customers and their respective orders (if I so choose).
- As a manager, I want to be able to load all the past customers and orders data from a file (if I so choose) and resume exactly where the system was left off when it was quit.

## Instructions for Grader
- You can generate the first required action, selecting size of drink, related to the user story "adding multiple Drinks to an Order" by clicking the desired drink in the menu then clicking the "Medium" or "Large" button to select the size.
- You can generate the second required action, deleting drink from order, related to the user story "adding multiple Drinks to an Order" by selecting the drink on the table display of the order and then press the "Delete" button.
- You can locate my visual component by pressing the "Customer" button in the home panel, enter your name, then click "Proceed".
- You can save the state of my application by pressing the "Save Data" button in the home panel.
- You can reload the state of my application by pressing the "Load Data" button in the home panel.

## Phase 4: Task 2
Sample events: 
- Start ordering application. 
- Wed Apr 03 22:15:26 PDT 2024 - Loaded coffee shop data. 
- Wed Apr 03 22:15:31 PDT 2024 - Customer ordering: maggie 
- Wed Apr 03 22:15:34 PDT 2024 - Drink added to order: Matcha Latte 
- Wed Apr 03 22:15:36 PDT 2024 - Drink added to order: Cappuccino 
- Wed Apr 03 22:15:39 PDT 2024 - Drink removed from order: Cappuccino 
- Wed Apr 03 22:15:41 PDT 2024 - Drink added to order: Caramel Macchiato 
- Wed Apr 03 22:15:51 PDT 2024 - Saved coffee shop data. 
- Quit online ordering application.

## Phase 4: Task 3
The first refactoring I would like to implement is abstracting duplicated code into methods, specifically in the OnlineOrderingAppGUI class. There are duplicate codes observed, particularly within the initMenuPanel() and initDrinkOptionsPanel() methods. These blocks, responsible for setting up panel characteristics such as colour, bounds, and size, could be extracted into a standardized method named initPanel(). Additionally, the code responsible for scaling the icons of drink ordering buttons in the initMenuPanel() method, repeated five times, can also be refactored into a method named scaleImageIcon(), ensuring consistent image scaling across the application. The reason behind this refactoring is to reduce code duplication, enhance readability and improve coupling. By abstracting common functionality into methods, such as initializing panels and scaling images, the codebase becomes more concise and easier to comprehend. Standardizing panel initialization and image scaling eliminates redundancy and also ensures consistency, reducing the likelihood of errors and simplifying future modifications. Through these refactorings, the OnlineOrderingAppGUI class becomes more modular, which allows code reuse and facilitates future enhancements.

The second refactoring I would like to make is implementing the Singleton pattern for the manager class to ensure that only one instance of the manager exists throughout the application. The reason behind this is to mitigate potential issues related to multiple manager instances being created, guaranteeing consistent access to manager functionalities and past coffee shop data. This also more accurately reflects the managerial system of the coffee shop, where only one manager is responsible for one coffee shop. So when a manager is replaced, there will still only be one manager.