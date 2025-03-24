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
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;

public class Plant{
    JButton plantBtn, backBtn;
    JPanel plantPanel, notifications;

    String spacingWarnText = "";

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

    //Plant panel variables
    JLabel description, season, height, spacing, image, general;
    String descriptionText = "";
    String seasonText = "";
    String heightText = "";
    String spacingText = "";
    ImageIcon imagePath;
    JPanel recForPlant;

    // private String name, image_url, description;
    // private int growth_months;
    // private double min_temp, max_temp, spread, min_precip, max_precip, light;
    // private String[] blooming_months;

    String name;
    final int id;

    // elements of profile panel
    JLabel title;
    // JLabel title, image, description, growthMonths, spread, watering, light, germination; 
    // String imageurl;

    public Plant(JPanel pp, JPanel c, JPanel gard, JPanel notPanel, CardLayout cardl, int plantID){
        id = plantID;
        name = "Plant " + Integer.toString(plantID);
        card = c;
        cardlay = cardl;
        plantBtn = new JButton("Plant " + plantID);
        backBtn = new JButton("Go Back");
        plantPanel = new JPanel(null);
        notifications = notPanel;

        popup = new JPopupMenu();
        editName = new JMenuItem("Edit Name");
        viewDetails = new JMenuItem("View Details");
        removePlant = new JMenuItem("Remove Plant");

        popup.add(editName);
        popup.add(viewDetails);
        popup.add(removePlant);

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
                    createSpacingWarning(name);
                    notifications.revalidate();
                    notifications.repaint();
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
            image = new JLabel(imagePath);
            image.setBounds(50, 200, 200, 150);
            plantPanel.add(image);

            // pp.revalidate();
            // pp.repaint();

        });

        removePlant.addActionListener((ActionEvent e) -> {
            gard.remove(plantBtn);
            gard.revalidate();
            gard.repaint();
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
        description.setBounds(170,60, 350, 100);
        //description.setHorizontalAlignment(SwingConstants.CENTER);
        //description.setVerticalAlignment(SwingConstants.CENTER);

        season = new JLabel("Season");
        season.setFont(n);
        season.setForeground(Color.white);

        season.setBounds(150 , 130, 500, 100);

        height = new JLabel("Height");
        height.setFont(n);
        height.setForeground(Color.white);

        height.setBounds(150 , 170, 500, 100);

        spacing = new JLabel("Spacing");
        spacing.setFont(n);
        spacing.setForeground(Color.white);

        spacing.setBounds(150 , 210, 500, 100);

        recForPlant.add(general);
        recForPlant.add(description);
        recForPlant.add(season);
        recForPlant.add(height);
        recForPlant.add(spacing);

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

                    seasonText = "- This plant grows well in " + pRow[5] + ".";
                    heightText = "- Its height ranges from " + pRow[9] + " inches.";
                    spacingText = "- It takes up about " + pRow[8] + " inches of space.";

                    imagePath = new ImageIcon(pRow[1]);

                    System.out.println("Plant found and added");

                    if(Integer.parseInt(pRow[18]) >= 36){

                        spacingWarnText = "<html> Warning! The " + pRow[0] + " plant spreads up to " + pRow[18] + " inches! </html>";
                        // spacingWarning.setBounds(10, 10, 300, 200);
                        // notPanel.add(spacingWarning);
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

    public void createSpacingWarning(String n){
        JButton spacingWarning = new JButton(spacingWarnText);
        spacingWarning.setBackground(Color.red);

        JPopupMenu spacingWarnPopup = new JPopupMenu();
        JMenuItem details = new JMenuItem("View Details");
        JMenuItem delete = new JMenuItem("Delete");

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

        spacingWarning.setBounds(10, 40, 380, 30);
        notifications.add(spacingWarning);
        spacingWarning.setVisible(true);
    }

    // getters
    public JButton getButton(){ return plantBtn; }

    public JPanel getPlantPanel(){ return plantPanel; }

}