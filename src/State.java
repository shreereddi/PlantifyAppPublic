import java.io.BufferedReader;
import java.io.FileReader;

public class State {
    // Static variable
    public static String stateName;
    public static int stateMinTemp, stateMaxTemp, stateZoneMin, stateZoneMax;

    public static BufferedReader stateReader = null;
    public static String statesFile = "data\\States.csv";
    public static String stateLine = "";

    // Static method
    public static int getMinTemp() {
        return stateMinTemp;
    }
    public static int getMaxTemp() {
        return stateMaxTemp;
    }
    public static int getMinZone() {
        return stateZoneMin;
    }
    public static int getMaxZone() {
        return stateZoneMax;
    }
    public static String getStateName(){
        return stateName;
    }

    public static void setStateName(String name){
        stateName = name;
        readAndAddStateTemp(stateName);
    }

    public static void readAndAddStateTemp(String loc){
        try {
            stateReader = new BufferedReader(new FileReader(statesFile));
            while((stateLine = stateReader.readLine()) != null){
                
                String [] stateRow = stateLine.split(",");
                
                // for(String index : stateRow){
                if (stateRow.length > 1) { // Ensure there are enough columns
                    if(stateRow[0].equals(loc)){
                        stateMinTemp = Integer.parseInt(stateRow[4]);
                        stateMaxTemp = Integer.parseInt(stateRow[3]);
                        stateZoneMin = Integer.parseInt(stateRow[1]);
                        stateZoneMax = Integer.parseInt(stateRow[2]);
                        System.out.println("State Min Temp: " + stateMinTemp);
                        System.out.println("State Max Temp: " + stateMaxTemp);
                        System.out.println("State Min Zone: " + stateZoneMin);
                        System.out.println("State Max Zone: " + stateZoneMax);
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
}

