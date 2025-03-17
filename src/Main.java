import javax.swing.JFrame;

public class Main{
    public static void main(String[] args) {
        //calls the login class

        // Login loginApp = new Login();
        // loginApp.createGUI();

        //THE BELOW CODE IS IF YOU WANT TO TEST THE PROFILE WIHTOUT BEING PROMTPED TO LOGIN AND EVERYTHING

        JFrame profileTest = new JFrame("Profile Test");
        profileTest.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileTest.setSize(800,600);

        Profile p = new Profile();
        p.createGUI(profileTest, "Naga", "North Carolina");
        profileTest.show(true);
        
    }
}
