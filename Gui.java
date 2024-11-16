import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * This is the login Gui for the user
 * to enter there username and password
 */
public class Gui {
    /**
     * JFrame for the login Gui
     */
    private JFrame frame;
    /**
     * Username text field
     */
    private JTextField userNameText;
    /**
     * Username text field
     */
    private JPasswordField passwordText;
    /**
     * Used to return to the main menu
     */
    private MainMenuGui mainMenu;

    /**
     * This is the constructor for the GUI
     */
    public Gui() {
         mainMenu = new MainMenuGui();
    }

    /**
     * This is the Gui of the add games class
     *  I am setting up the basics of the gui
     * such as the size, exiting the gui when i click the X button
     */
        public void startGui() {
            JFrame.setDefaultLookAndFeelDecorated(true);
            frame = new JFrame("DBMS");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //frame.setTitle("DBMS");
            frame.setLayout(new GridLayout(3, 2));
            frame.setSize(500, 500);

            //Creating a username & password
            userNameText = new JTextField();
            passwordText = new JPasswordField();

            //adding username
            frame.add(new JLabel("Username: "));
            //frame.add(new JTextField());
            frame.add(userNameText);

            //adding password
            frame.add(new JLabel("Password: "));
            //frame.add(new JTextField());
            frame.add(passwordText);


            // Creating Login button
            JButton loginButton = new JButton("Login");
            frame.add(loginButton);
            //frame.add(new JButton("Login"));
            int frameWidth = 200;
            int frameHeight = 200;

            // THis allows the user to login once button is clicked
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //String username = userNameText.getText();
                    //String password = new String(passwordText.getPassword());
                    loginPass();
                }
            });
            // Get the screen's dimensions (width and height)
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setBounds((int) screenSize.getWidth() - frame.getWidth(), 0, frameWidth, frameHeight);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

    /**
     * This is the method that checks the username and password
     * If either one is wrong then it doesn't go through if it does
     * then the user will have access to the main menu
     */
    private void loginPass() {
            String username = userNameText.getText();
            String password = new String(passwordText.getPassword());

            if(mainMenu.loginCheck(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose();
                mainMenu.mainMenuGUI();
            }else{
                JOptionPane.showMessageDialog(frame, "Login Failed");
            }

            /*
            //Check if the password and username is correct to proceed
            if (username.equals(mainMenu.userName) && password.equals(mainMenu.password)) {
                //System.out.println("Login Successful");
                JOptionPane.showMessageDialog(frame, "Login Successful");
                frame.dispose();
                mainMenu.mainMenuGUI();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Username or Password");
            }

             */
        }


}

