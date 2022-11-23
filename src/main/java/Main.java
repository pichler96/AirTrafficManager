import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Aircraft> aircrafts = new ArrayList<>();

    public static void main(String[] args) {

        boolean isProductiveModeFlag = false;
        Scanner in = new Scanner(System.in);
        String userInput = "";

        System.out.println("Hello! Welcome to the Air Traffic Manager! \n");

        System.out.println("Loading initial Aircraft states... \n");

        aircrafts = StaticAircraftData.loadStaticAircraftData();
        loadProductiveData(/*aircrafts*/);

        System.out.println("Initial Aircraft states loaded. \n");
        System.out.println("Would you like to work in productive mode or test mode to update your states?");

        do {
            System.out.println("Please make a selection below by inputting the proper task number:\n" +
                    "1. Productive\n" +
                    "2. Test\n" +
                    "3. Exit");
            userInput = in.nextLine();
            if (userInput.equals("1")) {
                isProductiveModeFlag = true;
                loadProductiveData(/*aircrafts*/);
                convertDataIntoGraph(/*aircrafts*/);
            } else if (userInput.equals("2")) {
                isProductiveModeFlag = true;
                DynamicAircraftData.loadDummyAircraftData();
                convertDataIntoGraph(/*aircrafts*/);
                //loadTestData(aircrafts);
            } else if (userInput.equals("3")) {
                System.exit(0);
            } else {
                System.out.println("Please enter a valid task number");
            }
        } while(!userInput.equals("1") || !userInput.equals("2") || !userInput.equals("3"));

        System.out.println("Would you like to update your aircraft states? Please enter the task description below.\n");
        do {
            System.out.println("   update: Update states \n" +
                               "   exit: Exit");
            userInput = in.nextLine();
            if (userInput.equalsIgnoreCase("update")) {
                if (isProductiveModeFlag) {
                    loadProductiveData();
                    convertDataIntoGraph();
                } else if (userInput.equalsIgnoreCase("exit")){
                    System.exit(0);
                } else {
                    System.out.println("Please enter a valid task description from the ones below:");
                }
            }
        } while (!userInput.equalsIgnoreCase("exit"));
    }

    private static void convertDataIntoGraph(/*List<Aircraft> aircrafts*/) {
        RDFConverter.convertStaticData(aircrafts);
        RDFConverter.convertDynamicData(aircrafts);
        //TODO load data into named graph and return?
    }

    //TODO do we need this?
    /*
        private static List<Aircraft> loadTestData() {
            //load dummy data into aircrafts
            DynamicAircraftData.loadDummyAircraftData();
            return aircrafts;
        }
    */
    private static List<Aircraft> loadProductiveData(/*List<Aircraft> aircrafts*/) {
        DynamicAircraftData.loadDynamicAircraftData(aircrafts);
        return aircrafts;
    }
}
