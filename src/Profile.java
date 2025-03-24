import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Profile {
    String name;
    String location;
    //variable declarations for all the variables used in the main App Screen
    JPanel mainPanel, profilePanel, gardenGrid, recPanel, plantOutputPanel, editProfilePanel, notificationsPanel, drawerPanel, waterTrackerPanel;
    JScrollPane scrollPane;
    GridBagConstraints constraints;
    JButton profile_btn, addRowbtn, addColbtn, getRecBtn, recPanelBackBtn, recPanelGoBtn, recPanelResetBtn, toggleDrawerButton;
    JLabel description, loclbl, gardenTitle, titlelbl, recPanelTitle, recPanelState, recPanelTypeLabel, recPanelOutputLabel;
    //String[] months = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] types = {"", "Vegetable", "Flower", "Fruit", "Herb", "Tree"};
    JComboBox<String> recPanelTypeDD = new JComboBox<>(types);
    ArrayList<String> plantRecOutput = new ArrayList<>();

    CardLayout profileCL;
    JPanel cp;

    private boolean isDrawerOpen = false;
    private final int drawerWidth = 200;
    private Timer slideTimer;
    private int drawerX = -drawerWidth; 

    JPopupMenu navPopUp;
    JMenuItem homeButton;
    JMenuItem profileButton;
    JMenuItem waterButton;
    JMenuItem logoutButton;

    String statesFile = "data\\States.csv";
    String plantFile = "data\\PlantData.csv";
    BufferedReader stateReader = null;
    BufferedReader plantReader = null;
    String stateLine = "";
    String plantLine = "";

    int stateMinTemp = 0;
    int stateMaxTemp = 0;
    int stateZoneMin = 0;
    int stateZoneMax = 0;
    int plantMin = 0;
    int plantMax = 0;
    int plantZoneMin = 0;
    int plantZoneMax = 0;
    String plantType;
    int index;

    String line = "";
    String [] states = new String[50];
    BufferedReader reader = null;

    //edit profile variables
    JLabel editProfileTitle, changeNameLabel, changeStateLabel;
    JTextField changeName;
    JComboBox<String> changeState = new JComboBox<>(createStatesDropDown());
    JButton saveBtn, editProfileBackBtn;

    // notification panel variables
    JLabel notPanelTitle;

    public List<String> finalA= new ArrayList<>(); 

    //gives the coordinate information of the basic garden grid the user will get
    private static int rowCount = 2;
    private static int columnCount = 2;
    private static int count = 0;

    CardLayout loginCardLayout;
    JPanel loginCardPanel;

    //holds all of the code for the profile, the garden, and other features we want to add
    public Profile(CardLayout c, JPanel cardp){
        mainPanel = new JPanel(null);
        //mainPanel.setBounds(0, 0, 800, 600);

        profilePanel = new JPanel(null);
       // profilePanel.setBounds(0, 0, 800, 600);
        //profilePanel.setBackground(new Color(221, 229, 182));
        editProfilePanel = new JPanel(null);
        waterTrackerPanel = new JPanel(null);
        notificationsPanel = new JPanel(null);
        scrollPane = new JScrollPane(notificationsPanel);
        
        gardenGrid = new JPanel(new GridBagLayout());
        //gardenGrid.setBounds(50, 225, 500, 300);
        constraints = new GridBagConstraints();

        profileCL = new CardLayout();
        cp = new JPanel(profileCL);

        loginCardLayout = c;
        loginCardPanel = cardp;
        //cp.setBounds(0, 0, 800, 600);
        //cp.setLayout(profileCL);


        //profilePanel.add(gardenGrid);
        //cp.add(profilePanel, "Profile Panel");
       // profileCL.show(cp, "Profile Panel");
        //mainPanel.add(cp);
        //f.add(mainPanel);

    }


    public void createGUI(JFrame f, String n, String l){
        mainPanel.setBounds(0, 0, 800, 600);
        
        cp.setBounds(0, 0, 800, 600);
        cp.setLayout(profileCL);

        name = n;
        location = l;
        createPanel();

        profileCL.show(cp, "Profile Panel");
        mainPanel.add(cp);
        f.add(mainPanel);
        
    }
    //createPanel  helps us add all of the infromation on one screen, without having multiple frames open. 
    //We used ChatGPT for help on figuring out what createPanel is, but we used documentation for the code.
    public void createPanel(){
        profilePanel.setBackground(new Color(221, 229, 182));
        //Creates the different fonts we use throughout the application
        Font t = new Font("Times Roman", Font.BOLD, 40);
        Font heading = new Font("Times Roman", Font.BOLD, 20);
        Font title = new Font("Times Roman", Font.BOLD, 30);
        Font text = new Font("Times Roman", Font.PLAIN , 15);

        // format notifications panel
        notificationsPanel.setBounds(360, 85, 400, 120);
        notificationsPanel.setBackground(new Color(175, 186, 119));
        notPanelTitle = new JLabel("Notifications");
        notPanelTitle.setFont(heading);
        notPanelTitle.setBounds(130,5,200,40);

        notificationsPanel.add(notPanelTitle);
       // profilePanel.add(scrollPane);
        profilePanel.add(notificationsPanel);

        initNavigationDrawer();

        // waterTrackerPanel code - SHREE

        waterTrackerPanel.setBackground(new Color(221, 229, 182));
        cp.add(waterTrackerPanel, "Water Tracker");





        // format edit profile panel
        editProfilePanel.setBackground(new Color(221, 229, 182));
        
        editProfileTitle = new JLabel("Edit Profile");
        editProfileTitle.setBounds(320, 45, 300, 50);
        editProfileTitle.setHorizontalTextPosition(JLabel.CENTER);
        editProfileTitle.setVerticalTextPosition(JLabel.CENTER);
        editProfileTitle.setFont(t);

        changeNameLabel = new JLabel("Change Name: ");
        changeNameLabel.setBounds(200,200,200,40);
        changeNameLabel.setFont(heading);

        changeStateLabel = new JLabel("Change State: ");
        changeStateLabel.setBounds(200, 250, 200, 40);
        changeStateLabel.setFont(heading);

        changeName = new JTextField(name);
        changeName.setBounds(350, 200, 300, 40);

        changeState.setBounds(350, 250, 300, 40);
        changeState.setSelectedItem(location);

        editProfileBackBtn = new JButton("Back");
        editProfileBackBtn.setBounds(230,330,200,40);

        editProfileBackBtn.addActionListener((ActionEvent e) -> {
            profileCL.show(cp, "Profile Panel");
        });

        saveBtn = new JButton("Save Changes");
        saveBtn.setBounds(440,330,200,40);

        saveBtn.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(editProfilePanel, "Proceed with changes?", "Confirm changes?", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                name = changeName.getText();
                location = (String) changeState.getSelectedItem();
                titlelbl.setText("Welcome " + name + "!");
                loclbl.setText("State: " + location);
                recPanelState.setText("State: " + location);
                readAndAddStateTemp(location);
                profileCL.show(cp, "Profile Panel");
                //JOptionPane.showMessageDialog(null, "Hello, " + name + "! You live in " + location + ".");
            }
        });

        editProfilePanel.add(editProfileTitle);
        editProfilePanel.add(changeNameLabel);
        editProfilePanel.add(changeStateLabel);
        editProfilePanel.add(changeName);
        editProfilePanel.add(changeState);
        editProfilePanel.add(editProfileBackBtn);
        editProfilePanel.add(saveBtn);

        cp.add(editProfilePanel, "Edit Profile");

        // reading and putting right values into the STATE min temp and max temp variables
        readAndAddStateTemp(location);
        // try {
        //     stateReader = new BufferedReader(new FileReader(statesFile));
        //     while((stateLine = stateReader.readLine()) != null){
                
        //         String [] stateRow = stateLine.split(",");
                
        //         // for(String index : stateRow){
        //         if (stateRow.length > 1) { // Ensure there are enough columns
        //             if(stateRow[0].equals(location)){
        //                 stateMinTemp = Integer.parseInt(stateRow[2]);
        //                 stateMaxTemp = Integer.parseInt(stateRow[3]);
        //                 System.out.println("State Min Temp: " + stateMinTemp);
        //                 System.out.println("State Max Temp: " + stateMaxTemp);
        //                 break;
        //             }
        //         }
        //         // }
                
        //     }
        //     stateReader.close();
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     System.out.println("Error reading State file: " + e.getMessage());
        // }
        // finally {
        //     try {
        //         stateReader.close();
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // }

        // creating recommnedations panel
        recPanel = new JPanel(null);
        recPanel.setBounds(555, 0, 300, 600);
        recPanel.setBackground(new Color(228,222,174));

        plantOutputPanel = new JPanel(null);
        plantOutputPanel.setBounds(10,250,200,150);
        plantOutputPanel.setBackground(new Color(221, 229, 182));

        recPanelTitle = new JLabel("Recommendations");
        recPanelTitle.setBounds(20, 20, 280, 30);
        recPanelTitle.setFont(heading);

        recPanelState = new JLabel("State: " + location);
        recPanelState.setBounds(10, 60, 280, 30);
        recPanelState.setFont(text);

        recPanelTypeLabel = new JLabel("Optional Type:");
        recPanelTypeLabel.setBounds(10, 90, 120, 40);
        recPanelTypeLabel.setFont(text);

        recPanelTypeDD.setBounds(120, 95, 100, 30);
        recPanelTypeDD.setFont(text);

        recPanelGoBtn = new JButton("Go");
        recPanelGoBtn.setBounds(75, 135, 100, 40);
        int check = 0;

        recPanelGoBtn.addActionListener((ActionEvent e) -> {
            int randInt;
            plantOutputPanel.removeAll();
            plantOutputPanel.revalidate();
            plantOutputPanel.repaint();
            check((String) recPanelTypeDD.getSelectedItem());
            if("".equals((String) recPanelTypeDD.getSelectedItem())){
                for(int i=0; i<5; i++){
                    JLabel plantLabel = new JLabel((i+1) + ". " + plantRecOutput.get(randomIndex(0,plantRecOutput.size()-1)));
                    plantLabel.setBounds(20, 10+(i*15), 150, 35);
                    plantOutputPanel.add(plantLabel);
                }
            }
            else{
                if(plantRecOutput.size()<5){
                    index = plantRecOutput.size();
                }
                else{
                    index = 5;
                }
                for(int i=0; i<index; i++){
                    randInt = randomIndex(0,plantRecOutput.size()-1);
                    JLabel plantLabel = new JLabel((i+1) + ". " + plantRecOutput.get(randInt));
                    plantLabel.setBounds(20, 10+(i*15), 150, 35);
                    plantOutputPanel.add(plantLabel);
                    plantRecOutput.remove(randInt);
                }
            }
            recPanelOutputLabel.setVisible(true);
            plantOutputPanel.setVisible(true);
        });

        recPanelOutputLabel = new JLabel("<html>Here are some plants that grow well in your area: </html>");
        recPanelOutputLabel.setBounds(10, 185, 200, 45);
        recPanelOutputLabel.setFont(text);
        recPanelOutputLabel.setVisible(false);

        recPanelBackBtn = new JButton("Go Back");
        recPanelBackBtn.setBounds(40, 500, 150, 40);

        recPanelResetBtn = new JButton("Reset");
        recPanelResetBtn.setBounds(50, 425, 130, 40);

        recPanelResetBtn.addActionListener((ActionEvent e) -> {
            if(!"".equals((String) recPanelTypeDD.getSelectedItem())){
                recPanelTypeDD.setSelectedItem("");
            }
            plantOutputPanel.removeAll();
            plantOutputPanel.revalidate();
            plantOutputPanel.repaint();
            plantRecOutput.clear();

            recPanelOutputLabel.setVisible(false);
            plantOutputPanel.setVisible(false);
        });

        recPanel.add(recPanelTitle);
        recPanel.add(recPanelState);
        recPanel.add(recPanelTypeLabel);
        recPanel.add(recPanelBackBtn);
        recPanel.add(recPanelTypeDD);
        recPanel.add(recPanelGoBtn);
        recPanel.add(recPanelResetBtn);
        recPanel.add(recPanelOutputLabel);
        recPanel.add(plantOutputPanel);
        profilePanel.add(recPanel);
        plantOutputPanel.setVisible(false);
        recPanel.setVisible(false);

        // end of rec panel creation

        //The next few sections are label and button declarations where we set the bounds and create them.
        titlelbl = new JLabel("Welcome " + name + "!");
        titlelbl.setBounds(270, 30, 500, 40);
        titlelbl.setFont(title);
        profilePanel.add(titlelbl);

        profile_btn = new JButton("Edit Profile");
        profile_btn.setBounds(100, 125, 200, 40);
        profile_btn.setFont(text);
        profilePanel.add(profile_btn);

        profile_btn.addActionListener((ActionEvent e) -> {
            profileCL.show(cp, "Edit Profile");
        });

        // namelbl = new JLabel("Name: " + name);
        // namelbl.setBounds(100, 125, 400, 40);
        // namelbl.setFont(text);
        // profilePanel.add(namelbl);

        loclbl = new JLabel("State: " + location);
        loclbl.setBounds(100, 75, 400, 40);
        loclbl.setFont(text);
        profilePanel.add(loclbl);

        gardenTitle = new JLabel("Garden");
        gardenTitle.setBounds(270, 175, 300, 40);
        gardenTitle.setFont(heading);
        profilePanel.add(gardenTitle);

        addRowbtn = new JButton("Add Row");
        addRowbtn.setBounds(575, 225, 150, 40);
        addRowbtn.setFont(text);
        profilePanel.add(addRowbtn);

        addColbtn = new JButton("Add Column");
        addColbtn.setBounds(575, 275, 150, 40);
        addColbtn.setFont(text);
        profilePanel.add(addColbtn);

        description = new JLabel("<html>Click on each box to customize it with plants! Don't know what plants to garden? Get recommendations!</html>");
        description.setBounds(575, 225, 175, 300);
        description.setFont(text);
        profilePanel.add(description);

        getRecBtn = new JButton("<html>Get Your Plant Recommendations</html>");
        getRecBtn.setBounds(575, 440, 160, 80);
        getRecBtn.setFont(text);
        profilePanel.add(getRecBtn);

        getRecBtn.addActionListener((ActionEvent e) -> {
            // createRecPanel();
            recPanel.setVisible(true);
            recPanelBackBtn.setVisible(true);

            addColbtn.setVisible(false);
            addRowbtn.setVisible(false);
            getRecBtn.setVisible(false);
            notificationsPanel.setVisible(false);
        });

        recPanelBackBtn.addActionListener((ActionEvent e) -> {
            plantRecOutput.clear();
            recPanel.setVisible(false);
            recPanelOutputLabel.setVisible(false);
            plantOutputPanel.setVisible(false);
            recPanelTypeDD.setSelectedItem("");
            plantOutputPanel.removeAll();
            plantOutputPanel.revalidate();
            plantOutputPanel.repaint();
            recPanelBackBtn.setVisible(false);

            addColbtn.setVisible(true);
            addRowbtn.setVisible(true);
            getRecBtn.setVisible(true);
            notificationsPanel.setVisible(true);
        });

        constraints.insets = new Insets(2, 2, 2, 2);
        gardenGrid.setBackground(new Color(108, 88, 76));

        //The next few blocks of code is the creation of the Garden Grid. We used the GridBagLayout
       
        //With the help of ChatGPT we were able to figure out how to format the grid and loop though it
        //The block of code below runs though the grid and creates a button for each grid box.
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                //JButton p = new JButton("Plant");
                Plant pl = new Plant(profilePanel, cp, gardenGrid, notificationsPanel, profileCL, count);
                pl.createPlantPanel(profilePanel);
                cp.add(pl.getPlantPanel(), "Plant " + Integer.toString(count));
                constraints.gridx = j;
                constraints.gridy = i;
                constraints.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
                constraints.ipadx = 20; // Padding x
                constraints.ipady = 40;
                gardenGrid.add(pl.getButton(), constraints);
                count++;
            }
        }
        profilePanel.add(gardenGrid);
        gardenGrid.setBounds(50, 225, 500, 300);
        profilePanel.revalidate();
        profilePanel.repaint();

        //This is the action listerner setup to create the Add Row Button
        //When the user clicks on this button they will add a row to their garden grid
        //ChatGPT was used to learn more about how action listeners work. 
        addRowbtn.addActionListener((ActionEvent e) -> {
            for (int i = 0; i < columnCount; i++){
                //JButton p = new JButton("Plant");
                Plant pl = new Plant(profilePanel, cp, gardenGrid, notificationsPanel, profileCL, count);
                pl.createPlantPanel(profilePanel);
                cp.add(pl.getPlantPanel(), "Plant " + Integer.toString(count));
                constraints.gridx = i;
                constraints.gridy = rowCount;
                gardenGrid.add(pl.getButton(), constraints);
                count++;
            }
            rowCount++;
            profilePanel.revalidate(); // Refresh layout
            profilePanel.repaint();

        });

        //This is the action listerner setup to create the Add Column Button
        //When the user clicks on this button they will add a column to their garden grid
        //ChatGPT was used to learn more about how action listeners work. 
        addColbtn.addActionListener((ActionEvent e) -> {
            for (int i = 0; i < rowCount; i++){
                //JButton p = new JButton("Plant");
                Plant pl = new Plant(profilePanel, cp, gardenGrid, notificationsPanel, profileCL, count);
                pl.createPlantPanel(profilePanel);
                cp.add(pl.getPlantPanel(), "Plant " + Integer.toString(count));
                constraints.gridx = columnCount;
                constraints.gridy = i;
                gardenGrid.add(pl.getButton(), constraints);
                count++;
            }
            columnCount++;
            profilePanel.revalidate();
            profilePanel.repaint();
        });
        
        cp.add(profilePanel, "Profile Panel");

        //sets the profilePanel visible so all of the buttons, labels, etc can be seen on the screen.
        //profilePanel.setVisible(true);


    }

    public void createRecPanel(){
    }

    public JPanel getMainProfilePanel(){
        return mainPanel;
    }

    public void readAndAddStateTemp(String loc){
        try {
            stateReader = new BufferedReader(new FileReader(statesFile));
            while((stateLine = stateReader.readLine()) != null){
                
                String [] stateRow = stateLine.split(",");
                
                // for(String index : stateRow){
                if (stateRow.length > 1) { // Ensure there are enough columns
                    if(stateRow[0].equals(loc)){
                        stateMinTemp = Integer.parseInt(stateRow[3]);
                        stateMaxTemp = Integer.parseInt(stateRow[4]);
                        stateZoneMin = Integer.parseInt(stateRow[1]);
                        stateZoneMax = Integer.parseInt(stateRow[2]);
                        System.out.println("State Min Temp: " + stateMinTemp);
                        System.out.println("State Max Temp: " + stateMaxTemp);
                        break;
                    }
                }
                // }
                
            }
            stateReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading State file: " + e.getMessage());
        }
    }

    public void check(String t){
        int check = 0;
        try {
            plantReader = new BufferedReader(new FileReader(plantFile));
            while((plantLine = plantReader.readLine()) != null){
                
                String [] plantRow = plantLine.split(",");
                if(plantRow.length > 1 && check > 1 && !"".equals(t)){ // if there is a type selected
                    plantMin = Integer.parseInt(plantRow[6]);
                    plantMax = Integer.parseInt(plantRow[7]);
                    plantZoneMin = Integer.parseInt(plantRow[15]);
                    plantZoneMax = Integer.parseInt(plantRow[16]);
                    plantType = plantRow[13];

                    if(plantMin <= stateMinTemp && plantMax >= stateMaxTemp && plantType.equals(t)){
                        if(plantZoneMin <= stateZoneMin && plantZoneMax >= stateZoneMax){
                            plantRecOutput.add(plantRow[0]);
                            System.out.println("Added plant: " + plantRow[0]);
                        }
                    }
                    else{
                        System.out.println("Plant does not meet conditions: " + plantRow[0]);
                    }
                }
                
                else if (plantRow.length > 1 && check > 1) {
                    plantMin = Integer.parseInt(plantRow[6]);
                    plantMax = Integer.parseInt(plantRow[7]);
                    plantZoneMin = Integer.parseInt(plantRow[15]);
                    plantZoneMax = Integer.parseInt(plantRow[16]);

                    if(plantMin <= stateMinTemp && plantMax >= stateMaxTemp){
                        if(plantZoneMin <= stateZoneMin && plantZoneMax >= stateZoneMax){
                            plantRecOutput.add(plantRow[0]);
                            System.out.println("Added plant: " + plantRow[0]);
                        }
                    }
                    else{
                        System.out.println("Plant does not meet conditions: " + plantRow[0]);
                    }
                }
                check++;
                
            }
            plantReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading plant file: " + e.getMessage());
        }
    }

    public String[] createStatesDropDown(){
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
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return states;
    }

    private void initNavigationDrawer() {
        //drawerPanel = new JPanel();
        // drawerPanel.setLayout(new BoxLayout(drawerPanel, BoxLayout.Y_AXIS));
        // drawerPanel.setBackground(new Color(189, 195, 155));
        // drawerPanel.setBounds(-drawerWidth, 0, drawerWidth, 700);  // Shifted down

        // JButton homeButton = new JButton("Home");
        // JButton profileButton = new JButton("Profile");
        // JButton settingsButton = new JButton("Settings");
        // JButton logoutButton = new JButton("Logout");

        toggleDrawerButton = new JButton("☰");
        toggleDrawerButton.setBounds(10, 10, 50, 30);
        toggleDrawerButton.addActionListener(e -> toggleDrawer());

        navPopUp = new JPopupMenu();

        homeButton = new JMenuItem("Home");
        profileButton = new JMenuItem("Profile");
        waterButton = new JMenuItem("Water Tracker");
        logoutButton = new JMenuItem("Logout");

        navPopUp.add(homeButton);
        navPopUp.add(profileButton);
        navPopUp.add(waterButton);
        navPopUp.add(logoutButton);

        //homeButton.addActionListener(e -> showContent("Home"));
        homeButton.addActionListener((ActionEvent e) -> {
            profileCL.show(cp, "Profile Panel");
            toggleDrawerButton.setVisible(true);
        });

        //profileButton.addActionListener(e -> showContent("Profile"));
        profileButton.addActionListener((ActionEvent e) -> {
            profileCL.show(cp, "Edit Profile");
            toggleDrawerButton.setVisible(true);
        });

        //settingsButton.addActionListener(e -> showContent("Settings"));
        waterButton.addActionListener((ActionEvent e) -> {
            profileCL.show(cp, "Water Tracker");
            toggleDrawerButton.setVisible(true);
        });

        //logoutButton.addActionListener(e -> showContent("Logout"));
        logoutButton.addActionListener((ActionEvent e) -> {
            loginCardLayout.show(loginCardPanel, "Login");
        });

        // drawerPanel.add(Box.createVerticalStrut(20)); // Add spacing at the top
        // drawerPanel.add(homeButton);
        // drawerPanel.add(profileButton);
        // drawerPanel.add(waterButton);
        // drawerPanel.add(logoutButton);

        // toggleDrawerButton = new JButton("☰");
        // toggleDrawerButton.setBounds(10, 10, 50, 30);
        // toggleDrawerButton.addActionListener(e -> toggleDrawer());

        mainPanel.add(toggleDrawerButton);
       // mainPanel.add(drawerPanel);
    }

    private void toggleDrawer() {
        // drawerX = isDrawerOpen ? -drawerWidth : 0;
        // drawerPanel.setBounds(drawerX, 50, drawerWidth, 600);  // Shifted down
        // isDrawerOpen = !isDrawerOpen;

        navPopUp.show(toggleDrawerButton, 0, 30);
    }

    private void showContent(String content) {
        JOptionPane.showMessageDialog(mainPanel, "You selected: " + content);
    }

     // Method to generate a random integer in the range [min, max]
    public int randomIndex(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }
}