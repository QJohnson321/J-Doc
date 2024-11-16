import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

//This Class is the main menu Gui. This class allows the
// user to decide what they would like to do.

/**
 * This Class is the main menu Gui. This class allows the
 * user to decide what they would like to do.
 */
public class MainMenuGui {

    /**
     * Allows me to scan user input
     */
    Scanner scanner = new Scanner(System.in);

    /**
     * Connects to database
     */
    private static final String DB_URL = "jdbc:sqlite:./SQL DataBase/mydbb.db";
    //private static final String DB_USER = "root";
    //private static final String DB_PASSWORD = "SmoothBeats@321";

    //String password = "1234";
    //String userName = "Login";

    /**
     * List of stored games
     */
    List<gameAttributes> gamesStored;

    /**
     * The main menu JFrame.
     */
    JFrame mainMenuFrame;

    /**
     * This is the construtor for the main menu
     */
    public MainMenuGui() {
        gameDatabase db = new gameDatabase();
        db.gamesStoredHere();
        this.gamesStored = db.getGameStored();
    }

    // I am building the game frame and setting the size, layout and to exit
    // the main menu when you click the X button

    /**
     * I am building the game frame and setting the size, layout and to exit
     * the main menu when you click the X button
     */
    public void mainMenuGUI() {
        mainMenuFrame = new JFrame();
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setTitle("Main Menu");
        mainMenuFrame.setSize(800, 600);
        mainMenuFrame.setLayout(new GridLayout(6,1));

        // I've created new buttons

        JButton addGameButton = new JButton("Add Game");
        JButton viewingButton = new JButton("View Games");
        JButton updateGameButton = new JButton("Update Game");
        JButton deleteGameButton = new JButton("Delete Game");
        JButton buyGameButton = new JButton("Buy Games");
        JButton exitButton = new JButton("Exit");


        // These are the actions that the button calls when
        // The user clicks on them
        addGameButton.addActionListener(e -> addGame());
        viewingButton.addActionListener(e -> viewGames());
        updateGameButton.addActionListener(e -> updateGame());
        deleteGameButton.addActionListener(e -> deleteGame());
        buyGameButton.addActionListener(e -> buyGame());
        exitButton.addActionListener(e -> System.exit(0));

        // I am adding the buttons to the gui
        mainMenuFrame.add(addGameButton);
        mainMenuFrame.add(viewingButton);
        mainMenuFrame.add(updateGameButton);
        mainMenuFrame.add(deleteGameButton);
        mainMenuFrame.add(buyGameButton);
        mainMenuFrame.add(exitButton);

        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setVisible(true);
    }

        // These are the choices that I give the user
        // each choice has their own class and the mainmenu frame
        // stays hidden when the user makes a selection

    /**
     * This allows the user to choose the add game method.
     * It is hiding a frame because when you open it up
     * there are two frames that appear
     */
    public void addGame() {
        //addGame addingTheGame = new addGame(this.gamesStored);
        //addingTheGame.start();
        //mainMenuFrame.dispose();
        mainMenuFrame.setVisible(false);
        addGameGui addGui = new addGameGui(gamesStored, this::mainMenuGUI);
        addGui.addGames();
    }
    /**
     * This allows the user to choose the view games method.
     * It is hiding a frame because when you open it up
     * there are two frames that appear
     */
    public void viewGames() {
        mainMenuFrame.setVisible(false);
        viewGamesGui viewingTheGame = new viewGamesGui(this.gamesStored, this::mainMenuGUI);
        viewingTheGame.buildViewGui();
    }
    /**
     * This allows the user to choose the update games method.
     * It is hiding a frame because when you open it up
     * there are two frames that appear
     */
    public void updateGame() {
        mainMenuFrame.setVisible(false);
        UpdateGamesGui UDG = new UpdateGamesGui(this.gamesStored, this::mainMenuGUI);
        UDG.buildUpdateGamesGui();
    }
    /**
     * This allows the user to choose the delete games method.
     * It is hiding a frame because when you open it up
     * there are two frames that appear
     */
    public void deleteGame() {
        mainMenuFrame.setVisible(false);
        deleteGameGui deleteGame = new deleteGameGui(this.gamesStored, this::mainMenuGUI);
        //deleteGame.buildDeleteGamesGui();
    }
    /**
     * This allows the user to choose the buy games method.
     * It is hiding a frame because when you open it up
     * there are two frames that appear
     */
    public void buyGame() {
        mainMenuFrame.setVisible(false);
        buyGamesGui buyGame = new buyGamesGui(this.gamesStored, this::mainMenuGUI);
        buyGame.buildBuyGamesGui();
    }

    /**
     *
     * @param mainMenuFrame this allows the user to exit the
     * program
     */
    public void shutDown(JFrame mainMenuFrame) {
        mainMenuFrame.dispose();
        System.out.println("Goodbye!");

    }

    /**
     *
     * @param userName This checks the username
     * @param password This checks the password
     * @return it will return if the user enters
     * the wrong username and password
     */
    public boolean loginCheck(String userName, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? ";

        try(Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement state =connection.prepareStatement(query)) {
            state.setString(1, userName);
            state.setString(2, password);

            try(ResultSet result = state.executeQuery()) {
                return result.next();
            }

        } catch (SQLException e) {
           e.printStackTrace();
           return false;
        }
    }

}