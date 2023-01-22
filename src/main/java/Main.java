import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Aircraft> aircrafts = new ArrayList<>();
    public static List<State> states = new ArrayList<>();

    public static long dateTime;
    public static void main(String[] args) {

        boolean isProductiveModeFlag = false;
        Scanner in = new Scanner(System.in);
        String userInput = "";

        System.out.println("Hello! Welcome to the Air Traffic Manager! \n");

        System.out.println("Loading Static Aircraft states... \n");
        aircrafts = StaticAircraftData.loadStaticAircraftData();
        System.out.println("Static Aircraft states loaded. \n");

        System.out.println("Would you like to work in productive mode or test mode to update your states?");
        do {
            System.out.println("Please make a selection below by inputting the proper task number:\n" +
                    "1. Productive\n" +
                    "2. Test\n" +
                    "3. Exit");
            userInput = in.nextLine();
            if (userInput.equals("1")) {
                isProductiveModeFlag = true;
                states = DynamicAircraftData.loadDynamicAircraftData();
                executeStaticData();
                executeDynamicData();
                break;
            } else if (userInput.equals("2")) {
                isProductiveModeFlag = true;
                states = DynamicAircraftData.loadDummyAircraftData();
                executeDynamicData();
                executeStaticData();
                break;
            } else if (userInput.equals("3")) {
                System.exit(0);
                break;
            } else {
                System.out.println("Please enter a valid task number");
            }
        } while(!userInput.equals("1") || !userInput.equals("2") || !userInput.equals("3"));

        System.out.println("Would you like to update your aircraft states? Please enter the task description below.\n");
        do {
            System.out.println( " update: Update states \n" +
                                " t1: Task 1 veraltete States \n" +
                                " t2: Task 2 Kollisionsgefahr \n" +
                                " t3: Task 3 Geschwindigkeit & Richtungsänderung \n" +
                                " Private: Task 4 Anzahl Aircrafts des Owners Private \n" +
                                " exit: Exit" );

            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("update")) {
                if (isProductiveModeFlag) {
                    states = DynamicAircraftData.loadDynamicAircraftData();
                    executeDynamicData();
                } else if (userInput.equalsIgnoreCase("exit")){
                    System.exit(0);
                } else {
                    System.out.println("Please enter a valid task description from the ones below:");
                }
            } else if (userInput.equalsIgnoreCase("t1")){
                //DataCollection.calculateFlightPosition(dateTime);
            } else if (userInput.equalsIgnoreCase("t2")){
                //DataCollection.detectCollision(dateTime);
            } else if (userInput.equalsIgnoreCase("t3")){
                //DataCollection.detectSpeedChange(dateTime);
                //DataCollection.detectDirectionChange(dateTime);
            } else if (userInput.equalsIgnoreCase("Private")){
            //DataCollection.calculateAggregation(dateTime, "Private");
            }
        } while (!userInput.equalsIgnoreCase("exit"));
    }

    private static void executeStaticData(){
        RDFConverter.convertStaticData(aircrafts);
    }
    private static void executeDynamicData(){
        dateTime = RDFConverter.convertDynamicData(states);
    }
}
