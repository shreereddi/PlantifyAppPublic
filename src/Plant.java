/* 

Formating tasks:
- Add images (should be easy)
- Add a title label for the description that says "Description"
- Change color and font of description
- Change color and font of panel recForPlant panel
- The recForPlant Panel has a lot of different labels added to it (change the formating for all these labels)
- Make the words and suff bigger and take up more space

*/


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Plant{
    JButton plantBtn, backBtn;
    JPanel plantPanel, notifications;

    static int count = 0;

    String spacingWarnText = "";
    String plantingWarnText = "";
    String plantingMessage = "";
    boolean isRightTemp = false;
    boolean isRightZone = false;

    JPanel card;
    CardLayout cardlay;

    JPopupMenu popup;
    JMenuItem editName;
    JMenuItem viewDetails;
    JMenuItem removePlant;

    String plantFile = "data\\PlantData.csv";
    BufferedReader pReader = null;
    String pLine = "";
    int check = 0;

    String plantStateLine = "";
    String plantStatesFile = "data\\States.csv";
    BufferedReader plantStateReader = null;
    int plantMin, plantMax, plantZoneMin, plantZoneMax;

    //Plant panel variables
    JLabel description, season, height, spacing, image, general;
    String descriptionText = "";
    String seasonText = "";
    String heightText = "";
    String spacingText = "";
    URL imagePathUrl;
    Image resizedImage;
    int labelWidth = 250; // Desired width
    int labelHeight = 150; // Desired height
    BufferedImage buffImg;
    ImageIcon imageIcon;
    JLabel imageLabel;
    JPanel recForPlant;

    List<Plant> profileSelectedPlants;

    // private String name, image_url, description;
    // private int growth_months;
    // private double min_temp, max_temp, spread, min_precip, max_precip, light;
    // private String[] blooming_months;

    double dailyWaterLiters = 0.0; 

    String name;
    final int id;

    // elements of profile panel
    JLabel title;
    // JLabel title, image, description, growthMonths, spread, watering, light, germination; 
    // String imageurl;

   // GridBagConstraints notPanelConst;
    JScrollPane notScrollPane;

    public Plant(JPanel pp, JPanel c, JPanel gard, JPanel notPanel, CardLayout cardl, int plantID, List<Plant> selectedPlants, GridBagConstraints npConst){
        id = plantID;
        name = "Plant " + Integer.toString(plantID);
        card = c;
        cardlay = cardl;
        plantBtn = new JButton("Plant " + plantID);
        backBtn = new JButton("Go Back");
        plantPanel = new JPanel(null);
        notifications = notPanel;

        // notScrollPane = new JScrollPane(notifications);
        // notScrollPane.setBounds(notifications.getX(), notifications.getY(), notifications.getWidth(), notifications.getHeight());
        // //notifications.setPreferredSize(new Dimension(400, 140));
        // // scrollPane.setBounds(50, 50, 300, 200);
        // notScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // pp.add(notScrollPane);

        popup = new JPopupMenu();
        editName = new JMenuItem("Edit Name");
        viewDetails = new JMenuItem("View Details");
        removePlant = new JMenuItem("Remove Plant");

        //notPanelConst = npConst;

        popup.add(editName);
        popup.add(viewDetails);
        popup.add(removePlant);
        this.profileSelectedPlants = selectedPlants;

        plantBtn.addActionListener((ActionEvent e) -> {
            // Show the popup menu at the button's location
            popup.show(plantBtn, plantBtn.getWidth() / 2, plantBtn.getHeight() / 2);
        });

        editName.addActionListener((ActionEvent e) -> {
            // Show an input dialog to get new text from the user
            name = JOptionPane.showInputDialog(pp, "Enter name of the plant:");
            // Update the button text if the input is not null or empty
            if (name != null && !name.trim().isEmpty()) {
                plantBtn.setText(name);
                title.setText(name);
                readAndCheckForPlant();
                if(!"".equals(spacingWarnText)){
                    createSpacingWarning(name, npConst);
                    notifications.revalidate();
                    notifications.repaint();
                }
                if(!"".equals(plantingWarnText)){
                    createPlantingWarning(name, npConst);
                    notifications.revalidate();
                    notifications.repaint();
                }
                if (!profileSelectedPlants.contains(this)) {
                    profileSelectedPlants.add(this);
                }
                

            }
        });

        viewDetails.addActionListener((ActionEvent e) -> {
            card.setBounds(0, 0, 800, 600);
            cardlay.show(card, "Plant " + Integer.toString(id));
            //readAndCheckForPlant();

            description.setText(descriptionText);
            season.setText(seasonText);
            height.setText(heightText);
            spacing.setText(spacingText);
            imageLabel.setIcon(imageIcon);
            // image = new JLabel(imagePath);

            // pp.revalidate();
            // pp.repaint();

        });

        removePlant.addActionListener((ActionEvent e) -> {
            gard.remove(plantBtn);
            gard.revalidate();
            gard.repaint();
            // waterTrackp.revalidate();
            // waterTrackp.repaint();

        });

        //plant panel init
        title = new JLabel(name);

    }

    public void createPlantPanel(JPanel propa){ // creates the planel with all the details of the plant
        plantPanel.setBackground(new Color(221, 229, 182));
        Font t = new Font("Times Roman", Font.BOLD, 40);
        Font h = new Font("Times Roman", Font.BOLD, 20);
        Font hh = new Font("Times Roman", Font.BOLD, 35);
        Font n = new Font("Times Roman", Font.BOLD, 15);
        title.setBounds(320, 45, 300, 50);
        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        title.setFont(t);

        recForPlant = new JPanel(null);
        recForPlant.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        //recForPlant.setLayout(new BoxLayout(recForPlant));
        recForPlant.setBackground(new Color(108, 88, 76));
        recForPlant.setBounds(50, 120, 690, 370);

        backBtn.setBounds(600,500,100,40);

        backBtn.addActionListener((ActionEvent e) -> {
            cardlay.show(card, "Profile Panel");

        });


        general = new JLabel("Plant Information");
        //general.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        general.setFont(hh);
        general.setForeground(Color.white);

        general.setBounds(250, 30, 400, 50);  
        //general.setHorizontalTextPosition(JLabel.CENTER);
       // general.setVerticalTextPosition(JLabel.CENTER); 
        //general.setAlignmentY(JLabel.CENTER);

        description = new JLabel("Centered Label");
        description.setFont(new Font("Serif", Font.BOLD, 20));
        description.setForeground(Color.white);
        description.setBounds(50,60, 350, 100);
        //description.setHorizontalAlignment(SwingConstants.CENTER);
        //description.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel = new JLabel("Image");
        imageLabel.setBounds(400, 110, labelWidth, labelHeight);

        season = new JLabel("Season");
        season.setFont(n);
        season.setForeground(Color.white);

        season.setBounds(50 , 130, 300, 100);

        height = new JLabel("Height");
        height.setFont(n);
        height.setForeground(Color.white);

        height.setBounds(50 , 170, 300, 100);

        spacing = new JLabel("Spacing");
        spacing.setFont(n);
        spacing.setForeground(Color.white);

        spacing.setBounds(50 , 210, 300, 100);

        recForPlant.add(general);
        recForPlant.add(description);
        recForPlant.add(season);
        recForPlant.add(height);
        recForPlant.add(spacing);
        recForPlant.add(imageLabel);

        plantPanel.add(title);
        plantPanel.add(backBtn);
        plantPanel.add(recForPlant);
        //plantPanel.add(description);
        // card.add(plantPanel, Integer.toString(id));
    }

    public void readAndCheckForPlant(){
        try {
            pReader = new BufferedReader(new FileReader(plantFile));
            while((pLine = pReader.readLine()) != null){
                
                String [] pRow = pLine.split(",");

                if(pRow.length > 1 && pRow[0].equals(name)){
                    descriptionText = "<html>" + pRow[3] + "</html>";

                    seasonText = "<html>- This plant grows well in " + pRow[5] + ".</html>";
                    heightText = "<html>- Its height ranges from " + pRow[9] + " inches.</html>";
                    spacingText = "<html>- It takes up about " + pRow[8] + " inches of space.</html>";
                    dailyWaterLiters = Double.parseDouble(pRow[11]);
                    System.out.println("Set water usage for " + name + ": " + dailyWaterLiters);

                    plantMin = Integer.parseInt(pRow[6]);
                    plantMax = Integer.parseInt(pRow[7]);
                    System.out.println(plantMin + " " + plantMax);
                    plantZoneMin = Integer.parseInt(pRow[15]);
                    plantZoneMax = Integer.parseInt(pRow[16]);
                    
                    //imagePath = pRow[1];
                    // System.out.println("url added: " + imagePath);

                    try {
                        imagePathUrl = new URL(pRow[1]);
                        buffImg = ImageIO.read(imagePathUrl);
                        resizedImage = buffImg.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(resizedImage);
                        System.out.println("url added: " + imagePathUrl);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("failed to load image.");
                    }

                    System.out.println("Plant found and added");

                    if(Integer.parseInt(pRow[18]) >= 36){

                        spacingWarnText = "<html> Warning! The " + pRow[0] + " plant spreads up to " + pRow[18] + " inches! </html>";
                        // spacingWarning.setBounds(10, 10, 300, 200);
                        // notPanel.add(spacingWarning);
                    }

                    if(plantMin >= State.getMinTemp() && plantMax <= State.getMaxTemp()){
                        if(plantZoneMin <= State.getMinZone() && plantZoneMax >= State.getMaxZone()){
                            JOptionPane.showMessageDialog(plantBtn, "Good choice!", "Message", JOptionPane.INFORMATION_MESSAGE);
                            isRightZone = true;
                        }
                        else{
                            plantingWarnText = "<html> Warning! Are you sure you want to grow " + pRow[0] + " in " + State.getStateName() + "?";
                        }
                        isRightTemp = true;
                    }
                    else if (plantZoneMin <= State.getMinZone() && plantZoneMax >= State.getMaxZone()){
                        isRightZone = true;
                        plantingWarnText = "<html> Warning! Are you sure you want to grow " + pRow[0] + " in " + State.getStateName() + "?";

                    }
                    else{
                        plantingWarnText = "<html> Warning! Are you sure you want to grow " + pRow[0] + " in " + State.getStateName() + "?";
                    }

                    break;
                }
                else{
                    System.out.println("Not found " + pRow[0]);
                }
                
                check++;
                
            }
            pReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading plant file: " + e.getMessage());
        }
    }

    public void createSpacingWarning(String n, GridBagConstraints npConstraints){
        JButton spacingWarning = new JButton(spacingWarnText);
        spacingWarning.setBackground(Color.orange);

        JPopupMenu spacingWarnPopup = new JPopupMenu();
        JMenuItem details = new JMenuItem("View Details");
        JMenuItem delete = new JMenuItem("Dismiss");

        spacingWarnPopup.add(details);
        spacingWarnPopup.add(delete);

        details.addActionListener((ActionEvent event) -> {
            String spacingMessage = "<html>Plant your " + n + " plant a little apart from other plants, especially other spacious plants.</html>";
            JOptionPane.showMessageDialog(details, spacingMessage);
            
        });

        delete.addActionListener((ActionEvent event) -> {
            notifications.remove(spacingWarning);
            notifications.revalidate();
            notifications.repaint();
        });

        spacingWarning.addActionListener((ActionEvent e) -> {
            // Show the popup menu at the button's location
            spacingWarnPopup.show(spacingWarning, spacingWarning.getWidth()/2, spacingWarning.getHeight()/2);
        });

        npConstraints.insets = new Insets(3,8,3,8);
        npConstraints.weightx = 1;
        npConstraints.weighty = 0;
        npConstraints.gridx = 0;
        npConstraints.gridy = count;
        npConstraints.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        // npConstraints.ipadx = 20; // Padding x
        // npConstraints.ipady = 40;
        count++;

        spacingWarning.setBounds(10, 40, 380, 30);
        notifications.add(spacingWarning, npConstraints);
        spacingWarning.setVisible(true);
    }

    public void createPlantingWarning(String n, GridBagConstraints npConstraints){
        JButton plantingWarning = new JButton(plantingWarnText);
        plantingWarning.setBackground(Color.red);

        JPopupMenu plantingWarnPopup = new JPopupMenu();
        JMenuItem plantingDetails = new JMenuItem("View Details");
        JMenuItem plantingDelete = new JMenuItem("Dismiss");

        plantingWarnPopup.add(plantingDetails);
        plantingWarnPopup.add(plantingDelete);

        plantingDetails.addActionListener((ActionEvent event) -> {
            if(isRightTemp == true){
                plantingMessage = "<html>The " + n + " plant does not grow well in your temperature, so it might not be a good idea to plant it.</html>";
            }
            else if(isRightZone == true){
                plantingMessage = "<html>The " + n + " plant does not grow well in your zone, so it might not be a good idea to plant it.</html>";
            }
            else{
                plantingMessage = "<html>The " + n + " plant does not grow well in your temperature or zone, so it might not be a good idea to plant it.</html>";
            }

            // String plantingMessage = "<html>Plant your " + n + " plant a little apart from other plants, especially other spacious plants.</html>";
            JOptionPane.showMessageDialog(plantingDetails, plantingMessage);
            
        });

        plantingDelete.addActionListener((ActionEvent event) -> {
            notifications.remove(plantingWarning);
            notifications.revalidate();
            notifications.repaint();
        });

        plantingWarning.addActionListener((ActionEvent e) -> {
            // Show the popup menu at the button's location
            plantingWarnPopup.show(plantingWarning, plantingWarning.getWidth()/2, plantingWarning.getHeight()/2);
        });

        npConstraints.insets = new Insets(3,8,3,8);
        npConstraints.weightx = 1;
        npConstraints.weighty = 0;
        npConstraints.gridx = 0;
        npConstraints.gridy = count;
        npConstraints.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        // npConstraints.ipadx = 20; // Padding x
        // npConstraints.ipady = 40;
        count++;

        plantingWarning.setBounds(10, 40, 380, 30);
        notifications.add(plantingWarning, npConstraints);
        plantingWarning.setVisible(true);
    }

    // getters
    public JButton getButton(){ return plantBtn; }

    public JPanel getPlantPanel(){ return plantPanel; }

    public double getDailyWaterLiters() {
        return dailyWaterLiters;
    }
    

}