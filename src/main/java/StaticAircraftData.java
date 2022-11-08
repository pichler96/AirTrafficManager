import com.opencsv.CSVReader;
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
    private static ArrayList<Aircraft> listOfOperatorAircrafts;

    static List<Aircraft> loadStaticAircraftData() {
        try {
            CSVReader csv = new CSVReader(new FileReader("aircraftDatabase-filtered.csv"));
            while (csv.readNext() != null) {
                Aircraft aircraft = new Aircraft("3c5eec");
                ArrayList<String> aircraftDetails = new ArrayList<>(Arrays.asList(csv.readNext()));
                csv.getRecordsRead();
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
                            aircraft.setModel(new AircraftModel(aircraftDetails.get(i)));
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
                            aircraft.setAircraftType(new IcaoAircraftType(aircraftDetails.get(i)));
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
                            listOfOperatorAircrafts.add(aircraft);
                            aircraft.setOperator(new Operator(operatorName, operatorCallSign, operatorIcao, operatorIata, listOfOperatorAircrafts));
                            break;
                        case 14:
                            aircraft.setOwner(new Owner(aircraftDetails.get(i)));
                            break;
                        case 15:
                            //TODO testreg?
                            break;
                        case 16:
                            aircraft.setRegistered(LocalDate.parse(aircraftDetails.get(i), formatter));
                            break;
                        case 17:
                            aircraft.setRegUntil(LocalDate.parse(aircraftDetails.get(i), formatter));
                            break;
                        case 18:
                            //TODO status?
                            break;
                        case 19:
                            aircraft.setBuilt(LocalDate.parse(aircraftDetails.get(i), formatter));
                            break;
                        case 20:
                            aircraft.setFirstFlightDate(LocalDate.parse(aircraftDetails.get(i), formatter));
                            break;
                        case 21:
                            //TODO seatconfiguration?
                            break;
                        case 22:
                            aircraft.setEngine(new Engine(aircraftDetails.get(i)));
                            break;
                        case 23:
                            aircraft.setModes(Boolean.parseBoolean(aircraftDetails.get(i)));
                            break;
                        case 24:
                            aircraft.setAdsb(Boolean.parseBoolean(aircraftDetails.get(i)));
                            break;
                        case 25:
                            aircraft.setAcars(Boolean.parseBoolean(aircraftDetails.get(i)));
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
