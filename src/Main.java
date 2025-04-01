
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main{
    public static void main(String[] args) {
        //calls the login class

        // Login loginApp = new Login();
        // loginApp.createGUI();

        //THE BELOW CODE IS IF YOU WANT TO TEST THE PROFILE WIHTOUT BEING PROMTPED TO LOGIN AND EVERYTHING

        JFrame profileTest = new JFrame("Profile Test");
        profileTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileTest.setSize(800,600);

        CardLayout clay = new CardLayout();
        JPanel cardpan = new JPanel();

        Profile p = new Profile(clay, cardpan);
        State.setStateName("North Carolina");
        p.createGUI(profileTest, "Naga");
        profileTest.show(true);
        
    }
}
