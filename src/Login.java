import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Login {
    //testing
    //Creates all of the variables used in the login and sign-up sections of the app. 
    JFrame loginFrame;
    JPanel newOrNot, signup, login;

    JLabel title, label_password, label_username, title_login;
    JButton yes, no, btn_login;
    CardLayout cl;
    JPasswordField password_login;
    JTextField username_login;
    JPanel cardPanel;

    String statesFile = "data\\States.csv";
    BufferedReader reader = null;
    String line = "";
    String [] states = new String[50];

    // signUp Panel instance variables
    JPasswordField password;
    JTextField name, email, username;
    JLabel label_name, label_email, label_pass, label_usern, signUp_title, label_location;
    JButton btn, login_btn;
    String fusername, fpassword, profile_name, profile_location;

    // constructor that initializes the main frame, panels, and layout
    public Login(){
        loginFrame = new JFrame();
        newOrNot = new JPanel();
        signup = new JPanel();
        login = new JPanel();

        cl = new CardLayout();

        cardPanel = new JPanel(cl);
    }

    public String[] getStates(){
        int i=0;
        try {
            reader = new BufferedReader(new FileReader(statesFile));
            while((line = reader.readLine()) != null){

                String [] row = line.split(",");

                // for(String index : row){
                //     System.out.printf("%-10s", index);
                // }
                // System.out.println();

                if (0 < row.length) {
                    //System.out.println(row[0]);
                    states[i] = row[0];
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return states;
    }

    public void createGUI(){
        //Sets up the loginFrame (background color, frame size, etc)
        //we call this method on the login object we create in the main file
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(800,600);
        loginFrame.setTitle("Login");
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);       
        

        //loginFrame.setBackground(new Color(221, 229, 182));
        loginFrame.setBackground(Color.RED);
        
        //Sets up the card panel with the bounds and layout
        cardPanel.setBounds(0, 0, 800, 600);
        cardPanel.setLayout(cl);

        //Calls the different functions
        createNewOrNot();
        createSignupPanel();
        createLoginPanel();

        //shows the cardPanel
        cl.show(cardPanel, "New or Not");

        loginFrame.add(cardPanel);
        loginFrame.setVisible(true);
    }

    //Creates the frame/screen that asks the user if they have an account or not
    public void createNewOrNot(){
        //sets up newOrNot
        newOrNot.setLayout(null);
        newOrNot.setBackground(new Color(0, 25, 0));
        //newOrNot.setBackground(Color.BLUE);
        //The next few lines of code set up labels, text field, and buttons
        title = new JLabel("Welcome! \nAre you new to Plantify?");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setBounds(155, 90, 600, 40);
        title.setForeground(new Color(255, 255, 255));

        yes = new JButton("Yes, Sign up!");
        yes.setBounds(275, 360, 220, 50);
        yes.setFont(new Font("SansSerif", Font.BOLD, 20)); // Set font to SansSerif
        yes.setBackground(Color.WHITE); // Set background color to white
        yes.setForeground(Color.BLACK); // Set font color to black
        yes.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Set border color to black
        

        //checks to see if the user clicks the button to sign up, and takes them there if so
        yes.addActionListener((ActionEvent e) -> {
            cl.show(cardPanel, "Signup");
        });

        no = new JButton("No, Login");
        no.setBounds(275, 430, 220, 50);
        no.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        no.setBackground(Color.WHITE); // Set background color to white
        no.setForeground(Color.BLACK); // Set font color to black
        no.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Set border color to black

        //checks to see if the user clicks the button to login, and takes them there if so
        no.addActionListener((ActionEvent e) -> {
            //LoginFrame login = new LoginFrame();
            //login.setVisible(true);
            cl.show(cardPanel, "Login");
        });

            // Path to the image file (make sure the path is correct)
            String imagePath = "C:\\Users\\saray\\Downloads\\GardenScapes-Photoroom.png";            
            // Load the image
            ImageIcon imageIcon = new ImageIcon(imagePath);            
            // Create a JLabel with the image
            JLabel imageLabel = new JLabel(imageIcon); 
            imageLabel.setFont(new Font("Serif", Font.BOLD, 30));
            imageLabel.setBounds(240, 100, 300, 300);
            imageLabel.setForeground(new Color(255, 255, 255));
            
        //adds all the items to the frame so they can show up
        newOrNot.add(title);
        newOrNot.add(imageLabel);
        newOrNot.add(yes);
        newOrNot.add(no);

        cardPanel.add(newOrNot, "New or Not");
    }
    public void displayImageExample() {
        
    }
        

    //creates the login panel
    public void createLoginPanel(){

        //sets up the login panel
        login.setLayout(null);
        login.setBackground(new Color(0, 25, 0));
        //login.setBackground(Color.GREEN);
        //the next few lines of code create labels, text fields, and buttons for the login to work
        //we used a video to figure out how to create a password input (so the words don't show) and
        //setting up the entire username and password

        // we were able to implement these methods in the signup panel and newOrNot panel
        label_password = new JLabel("Password");
        label_password.setBounds(200, 300, 100, 40);
        password_login = new JPasswordField();
        password_login.setBounds(300, 300, 300, 40);
        label_password.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        label_password.setForeground(new Color(255, 255, 255));

        label_username = new JLabel("Username");
        label_username.setBounds(200, 250, 100, 40 );
        username_login = new JTextField();
        username_login.setBounds(300, 250, 300, 40);
        label_username.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        label_username.setForeground(new Color(255, 255, 255));

        title_login = new JLabel("Login To Your Account");
        title_login.setBounds(150, 150, 400, 40);
        title_login.setFont(new Font("SansSerif", Font.BOLD, 30));  // Set font to SansSerif
        title_login.setForeground(new Color(255, 255, 255));

        btn_login = new JButton("Login");
        btn_login.setBounds(300, 350, 100, 40);
        btn_login.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        btn_login.setBackground(Color.WHITE); // Set background color to white
        btn_login.setForeground(Color.BLACK); // Set font color to black
        btn_login.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Set border color to black


        //checks to see if the user clicks login, and will check if the username and password match
        //it will take them to their garden if everything looks right
        btn_login.addActionListener((ActionEvent e) -> {
            // this.setVisible(false);
            String pwdText_login = new String(password_login.getPassword());
            if(username_login.getText().equals(fusername) && pwdText_login.equals(fpassword)){
                cl.show(cardPanel, "Profile");
            }
            else{
                JOptionPane.showMessageDialog(btn_login, "Incorrect Username and Password. Try again.");
                username_login.setText("");
                password_login.setText("");
            }
        });

        //adds everything to the panel so it works
        login.add(password_login);
        login.add(label_password);
        login.add(label_username);
        login.add(username_login);
        login.add(title_login);
        login.add(btn_login);

        cardPanel.add(login, "Login");
    }

    //creates the Sign-Up panel
    public void createSignupPanel(){

        //sets up the sign-up panel
        signup.setLayout(null);
        signup.setBackground(new Color(0, 25, 0));
        //signup.setBackground(Color.RED);//ep
        //the next few lines of code focus on creating labels, text fields, and buttons used on this screen
        label_pass = new JLabel("Password");
        label_pass.setBounds(200, 300, 100, 40);
        password = new JPasswordField();
        password.setBounds(300, 300, 300, 40);
        label_pass.setFont(new Font("SansSerif", Font.BOLD, 20));
        label_pass.setForeground(Color.WHITE);

        label_usern = new JLabel("Username");
        label_usern.setBounds(200, 250, 100, 40 );
        username = new JTextField();
        username.setBounds(300, 250, 300, 40);
        label_usern.setFont(new Font("SansSerif", Font.BOLD, 20));
        label_usern.setForeground(Color.WHITE);

        label_email = new JLabel("Email");
        label_email.setBounds(200, 200, 100, 40);
        email = new JTextField();
        email.setBounds(300, 200, 300, 40);
        label_email.setFont(new Font("SansSerif", Font.BOLD, 25));
        label_email.setForeground(Color.WHITE);

        label_name = new JLabel("Name");
        label_name.setBounds(200, 150, 100, 40);
        name = new JTextField();
        name.setBounds(300, 150, 300, 40);
        label_name.setFont(new Font("SansSerif", Font.BOLD, 25));
        label_name.setForeground(Color.WHITE);

        label_location = new JLabel("Location");
        label_location.setBounds(200, 350, 100, 40);
        //location = new JTextField();
        //location.setBounds(300, 350, 300, 40);
        label_location.setFont(new Font("SansSerif", Font.BOLD, 20));
        label_location.setForeground(Color.WHITE);

        JComboBox<String> location = new JComboBox<>(getStates());
        location.setBounds(300, 350, 300, 40);

        signUp_title = new JLabel("Make Your Account");
        signUp_title.setBounds(200, 50, 400, 40);
        signUp_title.setForeground(Color.WHITE);
        signUp_title.setFont(new Font("SansSerif", Font.BOLD, 30));  // Set font to SansSerif

        btn = new JButton("Signup");
        btn.setBounds(300, 400, 100, 40);
        btn.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        btn.setBackground(Color.WHITE); // Set background color to white
        btn.setForeground(Color.BLACK); // Set font color to black
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Set border color to black


        login_btn = new JButton("Login");
        login_btn.setBounds(450, 400, 100, 40);
        login_btn.setFont(new Font("SansSerif", Font.BOLD, 20));  // Set font to SansSerif
        login_btn.setBackground(Color.WHITE); // Set background color to white
        login_btn.setForeground(Color.BLACK); // Set font color to black
        login_btn.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Set border color to black


        //checks to see if the the users fills all of the text fields on the screen before clicking sign up
        btn.addActionListener((ActionEvent e) -> {
            fpassword = new String(password.getPassword());
            fusername = username.getText();
            if(!"".equals(name.getText()) && !"".equals(email.getText()) && !"".equals(fusername) && !"".equals(fpassword)){
                profile_name = name.getText();
                //profile_location = (String) location.getSelectedItem();
                State.setStateName((String) location.getSelectedItem());
                Profile p = new Profile(cl, cardPanel);
                p.createGUI(loginFrame, profile_name);
                cardPanel.add(p.getMainProfilePanel(), "Profile");
                JOptionPane.showMessageDialog(btn, "You signed up successfully! Now you can login.");
            }
            else{
                JOptionPane.showMessageDialog(btn, "You must complete ALL the fields!");
            }
        });
        
        //if the user clicks login, then this button will take them to the login screen. 
        login_btn.addActionListener((ActionEvent e) -> {
            //this.setVisible(false);
            cl.show(cardPanel, "Login");
        });
        

        //adds all the text fields, buttons, labels to the panel so they can show up
        signup.add(password);
        signup.add(label_pass);
        signup.add(label_usern);
        signup.add(username);
        signup.add(label_name);
        signup.add(name);
        signup.add(label_email);
        signup.add(email);
        signup.add(signUp_title);
        signup.add(login_btn);
        signup.add(btn);
        signup.add(label_location);
        signup.add(location);

        cardPanel.add(signup, "Signup");
    }

}
