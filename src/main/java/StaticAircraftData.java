import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StaticAircraftData {

    private static final ArrayList<Aircraft> listOfAircrafts = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static String manufacturerName;

    private static String operatorName;
    private static String operatorCallSign;
    private static String operatorIcao;


    static List<Aircraft> loadStaticAircraftData() {
        try {
            //Test case with 10 data sets
            CSVReader reader = new CSVReaderBuilder(new FileReader("aircraftDatabase-filtered-new.csv")).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();


            reader.skip(1);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Aircraft aircraft = new Aircraft();
                ArrayList<String> aircraftDetails = new ArrayList<>(Arrays.asList(nextLine));
                for (int i = 0; i < aircraftDetails.size(); i++) {
                    switch (i+1) {
                        case 1:
                            aircraft.setIcao(aircraftDetails.get(i));
                            break;
                        case 2:
                            aircraft.setRegistration(aircraftDetails.get(i));
                            break;
                        case 3:
                            manufacturerName = aircraftDetails.get(i);
                            break;
                        case 4:
                            String manufacturerIcao = aircraftDetails.get(i);
                            aircraft.setManufacturer(new Manufacturer(manufacturerIcao, manufacturerName));
                            break;
                        case 5:
                            aircraft.setModel(aircraftDetails.get(i));
                            break;
                        case 6:
                            aircraft.setTypeCode(aircraftDetails.get(i));
                            break;
                        case 7:
                            aircraft.setSerialNumber(aircraftDetails.get(i));
                            break;
                        case 8:
                            //TODO linenumber?
                            break;
                        case 9:
                            aircraft.setAircraftType(aircraftDetails.get(i));
                            break;
                        case 10:
                            operatorName = aircraftDetails.get(i);
                            break;
                        case 11:
                            operatorCallSign = aircraftDetails.get(i);
                            break;
                        case 12:
                            operatorIcao = aircraftDetails.get(i);
                            break;
                        case 13:
                            String operatorIata = aircraftDetails.get(i);
                            //TODO
                            //listOfOperatorAircrafts.add(aircraft);
                            aircraft.setOperator(new Operator(operatorName, operatorCallSign, operatorIcao, operatorIata));
                            break;
                        case 14:
                            aircraft.setOwner(aircraftDetails.get(i));
                            break;
                        case 15:
                            //TODO testreg?
                            break;
                        case 16:
                            if (aircraftDetails.get(i).equals("") || aircraftDetails.get(i) == null) {
                                aircraft.setRegistered(null);
                            } else {
                                aircraft.setRegistered(aircraftDetails.get(i));
                            }
                            break;
                        case 17:
                            if (aircraftDetails.get(i).equals("") || aircraftDetails.get(i) == null) {
                                aircraft.setRegUntil(null);
                            } else {
                                aircraft.setRegUntil(aircraftDetails.get(i));
                            }
                            break;
                        case 18:
                            //TODO status?
                            break;
                        case 19:
                            if (aircraftDetails.get(i).equals("") || aircraftDetails.get(i) == null) {
                                aircraft.setBuilt(null);
                            } else {
                                aircraft.setBuilt(aircraftDetails.get(i));
                            }
                            break;
                        case 20:
                            if (aircraftDetails.get(i).equals("") || aircraftDetails.get(i) == null) {
                                aircraft.setFirstFlightDate(null);
                            } else {
                                aircraft.setFirstFlightDate(aircraftDetails.get(i));
                            }
                            break;
                        case 21:
                            //TODO seatconfiguration?
                            break;
                        case 22:
                            aircraft.setEngine(aircraftDetails.get(i));
                            break;
                        case 23:
                            aircraft.setModes((Boolean.parseBoolean(aircraftDetails.get(i))));
                            break;
                        case 24:
                            aircraft.setAdsb((Boolean.parseBoolean(aircraftDetails.get(i))));
                            break;
                        case 25:
                            aircraft.setAcars((Boolean.parseBoolean(aircraftDetails.get(i))));
                            break;
                        case 26:
                            aircraft.setNotes(aircraftDetails.get(i));
                            break;
                        case 27:
                            aircraft.setCategoryDescription(aircraftDetails.get(i));
                            listOfAircrafts.add(aircraft);
                            break;
                        default:
                            throw new CsvValidationException();
                    }
                }
            }
        } catch (CsvValidationException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {}
        return listOfAircrafts;
    }
}
