import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static List<Aircraft> aircrafts = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Hello! Welcome to the Air Traffic Manager! \n");

        while(true){
        System.out.println("Select the task you  would like to do \n"+
                "   1.Conversion of the static Data \n"
                +"   2.Conversion of the dynamic Data \n");

        Scanner userinput= new Scanner(System.in);
        int nmb= userinput.nextInt();
        if(nmb==1){
            System.out.println("Conversion of the static Data selected \n"+
                    "Would you like to transform the data into a RDF Model? (Y/N)");
            char letter= userinput.next().charAt(0);
            if(letter=='Y'){
                System.out.println("generate RDF Model...");
                break;
            }
            else continue;
        }
        }

       /* aircrafts = StaticAircraftData.loadStaticAircraftData();
        System.out.println(aircrafts.toString());
        DynamicAircraftData.loadDynamicAircraftData(aircrafts);
        RDFConverter.convertToRDF(aircrafts);
    */
    }
}
