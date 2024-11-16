import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This Gui allows the user to purchase video games
// the user searches for the game by title and
// selects the game once the game is selected
// then the user can check out the game

/**
 *  This Gui allows the user to purchase video games
 *  the user searches for the game by title and
 *  selects the game once the game is selected
 *  then the user can check out the game
 */
public class buyGamesGui {

    /**
     * This used for the Buy Games GUI.
     */
        private JFrame buyGamesFrame;

    /**
     * This Text field is where the user enters the title
     */
        private JTextField titleTxt;
    /**
     * This displays the items currently in the user's cart
     */
    private JTextArea resultArea, cartArea;
    /**
     * List for displaying the games available for purchase.
     */
        private JList<String> gamesList;
    /**


    /**
     * Model for the games list, used to dynamically update the list display.
     */
        private DefaultListModel<String> gamesListModel;

    /**
     * This is the list of games stored
     */
    private List<gameAttributes> gamesStored;

    /**
     * List of games matching the search query
     */
    List<gameAttributes> searchResults = new ArrayList<>();

    /**
     * List of items the user has added to their shopping cart
     */
        private List<cartSetup> userCart = new ArrayList<>();
    /**
     * The currently selected game from the games list
     */
        private gameAttributes selectedGame;

    /**
     * return to the main menu
     */
        private Runnable mainMenuRunnable;

    /**
     * This constructor is for the buyGamesGui
     *
     * @param gamesStored List of game attributes
     * @param mainMenuRunnable this is to return to the main menu
     */
    public buyGamesGui(List<gameAttributes> gamesStored, Runnable mainMenuRunnable) {
            this.gamesStored = gamesStored;
            this.mainMenuRunnable = mainMenuRunnable;
            //buildBuyGamesGui();
        }

    /**
     * This method is the GUI for the buy games class
     * I set the side of the frame
     * the layout and i had the games
     * attributes to the frame
     */
    public void buildBuyGamesGui() {
            buyGamesFrame = new JFrame();
            buyGamesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            buyGamesFrame.setSize(800, 600);
            buyGamesFrame.setLayout(new BorderLayout(10,10));

            JPanel buyGamesPanel = new JPanel();
            buyGamesPanel.setLayout(new GridLayout(6,2,10,10));

            buyGamesPanel.add(new JLabel("Title:"));
            titleTxt = new JTextField();
            buyGamesPanel.add(titleTxt);

            JButton searchGamesButton = new JButton("Search Game");
            searchGamesButton.addActionListener(e -> searchForGames());
            buyGamesPanel.add(searchGamesButton);

            JButton addGameButton = new JButton("Add Game");
            addGameButton.addActionListener(e -> addToCart());
            buyGamesPanel.add(addGameButton);

            JButton viewCartButton = new JButton("View Cart");
            viewCartButton.addActionListener(e -> viewCart());
            buyGamesPanel.add(viewCartButton);

            JButton checkoutButton = new JButton("Checkout Cart");
            checkoutButton.addActionListener(e -> checkoutFinal());
            buyGamesPanel.add(checkoutButton);

            JButton exitButton = new JButton("Exit");
            exitButton.addActionListener(e -> exitToMainMenu());
            buyGamesPanel.add(exitButton);

            gamesListModel = new DefaultListModel<>();
            gamesList = new JList<>(gamesListModel);
            gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane gamesListScroller = new JScrollPane(gamesList);

            resultArea = new JTextArea();
            resultArea.setEditable(false);
            resultArea.setLineWrap(true);
            resultArea.setWrapStyleWord(true);

            buyGamesFrame.add(buyGamesPanel, BorderLayout.NORTH);
            buyGamesFrame.add(gamesListScroller, BorderLayout.WEST);
            buyGamesFrame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
            buyGamesFrame.setLocationRelativeTo(null);
            buyGamesFrame.setVisible(true);

        }

    /**
     * This method searches for the games it
     * connects to the database and shows users the
     * games that are in the database
     */
    private void searchForGames() {
            resultArea.setText("");
            String dbUrl = "jdbc:sqlite:./SQL DataBase/mydbb.db";

            // Get and lowercase the search term from titleTxt
            String gamesToBuy = titleTxt.getText().toLowerCase();
            //List<gameAttributes> searchResults = new ArrayList<>();

            gamesListModel.clear();
            searchResults.clear();

            String searchQuery = "SELECT * FROM add_games WHERE LOWER(title) LIKE ?";
            try(Connection connection = DriverManager.getConnection(dbUrl);
                PreparedStatement state = connection.prepareStatement(searchQuery)){

                state.setString(1, "%" + gamesToBuy + "%");
                try(ResultSet resultSet = state.executeQuery()){

                    while (resultSet.next()) {
                        int gameId = resultSet.getInt("gameId");
                        String title = resultSet.getString("title");
                        double price = resultSet.getDouble("price");

                        gameAttributes game = new gameAttributes(gameId, title, price);
                        searchResults.add(game);
                        gamesListModel.addElement(title);

                    }
                }
                    if(searchResults.isEmpty()){
                        resultArea.setText("No games found");
                    }else{
                        resultArea.setText("Found " + searchResults.size() + " games");
                    }

            } catch (SQLException e) {
                e.printStackTrace();
                resultArea.setText("Error getting games " + e.getMessage());
            }
/*
            // Loop through all stored games to find titles
                for(gameAttributes myGame : gamesStored) {
                    if(myGame.getTitle().toLowerCase().contains(gamesToBuy)) {
                        searchResults.add(myGame);
                    }
                }

                gamesListModel.clear();// Clear previous search results

                if(searchResults.isEmpty()) {
                    resultArea.append("No games found");
                }else {
                    for(gameAttributes myGame : searchResults) {
                        // Add each found game title to gamesListModel
                        gamesListModel.addElement(myGame.getTitle());

                    }
                    resultArea.append("Found " + searchResults.size() + " games and add them to your cart");
                }

 */
            }

    /**
     * This method allows users to add there selected games in the cart
     * Users can select a game add it to cart and decide how many
     * copies does the user wants.
     */
    private void addToCart() {
                resultArea.setText("");
                int selectedIndex = gamesList.getSelectedIndex();  // Get the index of the selected game from the games list

                // Check if the selected index is valid
                if(selectedIndex >= 0 && selectedIndex < searchResults.size()) {
                    selectedGame = searchResults.get(selectedIndex); // Retrieve the selected game from gamesStored
                    String copiesStr = JOptionPane.showInputDialog("Enter copies"); // Asks user to enter quantity

                    try {
                        // Proceeds when the quantity is above 0
                        int copies = Integer.parseInt(copiesStr);
                        if (copies > 0) {
                            boolean cartReady = false;  // Track if game is already in the cart
                            for (cartSetup cartItem : userCart) {
                                if(cartItem.getTitle().equalsIgnoreCase(selectedGame.getTitle())){
                                    cartItem.setQuantity(cartItem.getQuantity() + copies);
                                    cartReady = true;
                                    break;
                                }
                            }
                            // If the game was not already in the cart, create a new cart item
                            if(!cartReady) {
                                cartSetup newItems = new cartSetup(selectedGame.getTitle(), selectedGame.getPrice());
                                newItems.setQuantity(copies);
                                userCart.add(newItems);
                        }
                            // Display message of the addition of games to cart
                            resultArea.setText("Added " + copies + " copies of " + selectedGame.getTitle() + " your cart");
                    }
                } catch (NumberFormatException e) {
                        //JOptionPane.showMessageDialog(null, "Please enter a valid number");
                        resultArea.append("Invalid input");
                    }
                } else {
                    resultArea.append("No games found");
                }
            }

    /**
     *This allows the user to view their cart of added games
     */
            private void viewCart() {
                resultArea.setText("");
                // Check if the cart is empty
                if(userCart.isEmpty()) {
                    resultArea.append("No Games In cart found");
                    return;
                }


                // Build a string with details of each item in the cart
                StringBuilder cartContents = new StringBuilder("Your Cart:\n");
                for (cartSetup item : userCart) {
                    cartContents.append(item.getTitle()).append(" - $").append(item.getPrice())
                            .append(" x ").append(item.getQuantity()).append("\n");
                }
                // Display cart contents in the result area
                resultArea.setText(cartContents.toString());
            }

    /**
     * This method allows the user to check out their games
     * there has to be games in the cart before checkout can begin
     * The system will connect to the database then display your cart with your cost
     * user decides if they want to purchase the games.
     *
     */
    private void checkoutFinal() {
        resultArea.setText("");
        if (userCart.isEmpty()) {
            resultArea.setText("No games in cart to checkout.");
            return;
        }

        double total = 0;
        StringBuilder checkoutDetails = new StringBuilder("Checkout:\n");
        String dbUrl = "jdbc:sqlite:./SQL DataBase/mydbb.db";

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(false); // Start a transaction

            for (cartSetup item : userCart) {
                double itemTotal = item.getPrice() * item.getQuantity();
                checkoutDetails.append(item.getTitle()).append(" - $").append(item.getPrice())
                        .append(" x ").append(item.getQuantity()).append(" = $").append(itemTotal).append("\n");
                total += itemTotal;

                // Deduct the quantity from the stock in `add_games`
                /*String updateStockQuery = "UPDATE add_games SET stock = stock - ? WHERE title = ?";
                try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockQuery)) {
                    updateStockStmt.setInt(1, item.getQuantity());
                    updateStockStmt.setString(2, item.getTitle());
                    updateStockStmt.executeUpdate();
                }

                 */

                // Insert transaction record
                String insertTransactionQuery = "INSERT INTO transactions (gameId, title, quantity, price, total) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertTransactionStmt = connection.prepareStatement(insertTransactionQuery)) {
                    insertTransactionStmt.setInt(1, selectedGame.getGameid());
                    insertTransactionStmt.setString(2, item.getTitle());
                    insertTransactionStmt.setInt(3, item.getQuantity());
                    insertTransactionStmt.setDouble(4, item.getPrice());
                    insertTransactionStmt.setDouble(5, itemTotal);
                    insertTransactionStmt.executeUpdate();
                }
            }

            connection.commit(); // Commit the transaction if all updates were successful
            checkoutDetails.append("Total: $").append(total);

            int answer = JOptionPane.showConfirmDialog(buyGamesFrame, checkoutDetails.toString() + "\nConfirm Purchase?");
            if (answer == JOptionPane.YES_OPTION) {
                resultArea.setText("Thank you for your purchase!");
                userCart.clear();
            } else {
                connection.rollback(); // Roll back transaction if user cancels
                resultArea.setText("Checkout canceled.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error processing checkout: " + e.getMessage());
        }
    }
    /**
     *This is to exit this class
     */
    // This is to exit this class
    private void exitToMainMenu() {
        buyGamesFrame.dispose();
        if (mainMenuRunnable != null) {
            mainMenuRunnable.run();
        }
    }
}




