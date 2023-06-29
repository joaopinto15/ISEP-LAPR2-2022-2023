package pt.ipp.isep.dei.esoft.project.domain.service;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The type Transform file in announcement.
 */
public class TransformFileInAnnouncement {


    /**
     * The Announcements list.
     */
    private final List<Announcement> announcementsList;
    /**
     * The Agency list.
     */
    private final List<Agency> agencyList;

    /**
     * Instantiates a new Transform file in announcement.
     *
     * @param announcementsList the announcements list
     * @param agencyList        the agency list
     */
    public TransformFileInAnnouncement(List<Announcement> announcementsList, List<Agency> agencyList) {
        this.announcementsList = announcementsList;
        this.agencyList = agencyList;
    }

    /**
     * Gets announcements list.
     *
     * @return the announcements list
     */
    public List<Announcement> getAnnouncementsList() {
        return announcementsList;
    }

    /**
     * Gets agency list.
     *
     * @return the agency list
     */
    public List<Agency> getAgencyList() {
        return agencyList;
    }

    /**
     * Transformin object list.
     *
     * @param data the data
     * @param j    the j
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public static TransformFileInAnnouncement  transforminObject(String[][]data,int j) throws FileNotFoundException, ParseException {

        List<Announcement> announcementsList = new ArrayList<>();
        List<Agency> agencyList = new ArrayList<>();

        j -= 1;
        Agency agency = null;

        for (int i = 0; i < j; i++) {

            //owner
            String name = data[i][1];
            int passportNumber = Integer.parseInt(data[i][2]);
            String taxNumber = readPhone_or_taxNumber(data[i][3]);
            String emailAddress = data[i][4];
            String phoneNumber = readPhone_or_taxNumber(data[i][5]);
            Person person = new Person(name, passportNumber, taxNumber, emailAddress, phoneNumber);

            //Agency
            String store_ID = data[i][25];
            String storeName = data[i][26];
            Address adressStore = createAddressObject(data[i][27]);
            String phoneStore = readPhone_or_taxNumber(data[i][28]);
            String emailStore = data[i][29];

            agency = new Agency(store_ID, adressStore, storeName, phoneStore, emailStore);
            Employee employee = new Employee(AuthenticationController.ROLE_EMPLOYEE, adressStore, store_ID);


            //property
            String propertyType = data[i][6];
            double property_area = Double.parseDouble(data[i][7]);

            String adress = data[i][8];

            Address location = createAddressObject(adress);


            double property_requested_sale_rent_price = Double.parseDouble(data[i][18]);


            //order date
            pt.ipp.isep.dei.esoft.project.domain.model.Date property_dateOfSale = parseDate(data[i][23]);

            String dealType = data[i][24];
            Deal deal = null;
            Sale sale;
            Rent rent;
            if (dealType.equalsIgnoreCase("SALE")) {
                sale = new Sale(property_requested_sale_rent_price);
                deal = sale;
            } else if (dealType.equalsIgnoreCase("RENT")) {
                rent = new Rent(property_requested_sale_rent_price, Integer.parseInt(data[i][21]));
                deal = rent;
            }


            double distanceFromCityCentre = Double.parseDouble(data[i][9]);
            int numberOfBedrooms = Integer.parseInt(data[i][10]);
            int numberOfBathrooms = Integer.parseInt(data[i][11]);
            int numberOfParkingSpaces = Integer.parseInt(data[i][12]);


            String property_centralHeating = data[i][13];//13 e 14
            String property_airconditioned = data[i][14];
            ArrayList<String> availableEquipment = new ArrayList<>();

            ArrayList<String> photoURL = new ArrayList<>();

            if (property_centralHeating.equalsIgnoreCase("Y")) {
                availableEquipment.add("centralHeating");
            }
            if (property_airconditioned.equalsIgnoreCase("Y")) {
                availableEquipment.add("airconditioned");
            }

            boolean inhabitableLoft = checkLoft_basement(data[i][15]);
            boolean basement = checkLoft_basement(data[i][16]);

            String sunExposure = setSunExposure(data[i][17]);


            Property property = null;
            switch(propertyType){
                case "land":
                    property = new Land(property_area, distanceFromCityCentre, photoURL, location, deal);
                    break;
                case "apartment":
                    property = new Apartment(property_area, distanceFromCityCentre, photoURL, location, deal, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
                    break;
                case "house":
                    property = new House(property_area, distanceFromCityCentre, photoURL, location, deal, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement, inhabitableLoft, sunExposure);
                    break;
            }


            double property_sale_rent_price = Double.parseDouble(data[i][19]);
            //comission
            float value = Float.parseFloat(data[i][20]);
            Commission commission = new Commission(value, true);


            //date Request
            pt.ipp.isep.dei.esoft.project.domain.model.Date requestDate = parseDate(data[i][22]);


            Order order = new Order(new Person(AuthenticationController.ROLE_CLIENT), property_sale_rent_price, property_dateOfSale, true);
            List<Order> orders = new ArrayList<>();
            orders.add(order);


            Request request = new Request(employee, person, property, false, requestDate);
            Announcement announcement = new Announcement(request, commission, requestDate, true, orders);
            announcementsList.add(announcement);
            
            if (!agencyList.isEmpty()) {
                boolean add = true;
                for (Agency x : agencyList) {
                    if (agency.getId().equals(x.getId())) {
                        add = false;
                        break;
                    }
                }
                if(add){
                    agencyList.add(agency);
                }
            }else{
                agencyList.add(agency);
            }
 
            
        }
        return new TransformFileInAnnouncement(announcementsList, agencyList);
    }


    /**
     * Create address object address.
     *
     * @param addressString the address string
     * @return the address
     */
    private static Address createAddressObject(String addressString) {
        String[] addressParts = addressString.split(", ");
        int range = addressParts.length;

        String street = addressParts[0];
        String city = addressParts[1];
        String district = addressParts[2];
        String state = addressParts[3];
        String zipcode = addressParts[range - 1];

        return new Address(street, city, district, state, zipcode);
    }

    /**
     * Parse date pt . ipp . isep . dei . esoft . project . domain . model . date.
     *
     * @param dateString the date string
     * @return the pt . ipp . isep . dei . esoft . project . domain . model . date
     * @throws ParseException the parse exception
     */
    private static pt.ipp.isep.dei.esoft.project.domain.model.Date parseDate(String dateString) throws ParseException {
        DateFormat dateFormat;
        if (dateString.contains("-")) {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        } else if (dateString.contains("/")) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        } else {
            throw new IllegalArgumentException("Formato de data inválido: " + dateString);
        }

        Date date = dateFormat.parse(dateString);

        // Extrair o dia, mês e ano da data convertida
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Os meses em Calendar começam em 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Criar e retornar uma nova instância da classe Date
        return new pt.ipp.isep.dei.esoft.project.domain.model.Date(year, month, day);
    }


    /**
     * Check loft basement boolean.
     *
     * @param answer the answer
     * @return the boolean
     */
//home
    private static boolean checkLoft_basement(String answer) {
        boolean inhabitableLoft;
        answer = answer.toUpperCase();

        if (answer.equals("Y")) {
            inhabitableLoft = true;
        } else if (answer.equals("N")) {
            inhabitableLoft = false;
        } else {
            // Opção inválida, defina como valor padrão (false) ou trate o erro conforme necessário.
            inhabitableLoft = false;
        }
        return inhabitableLoft;
    }

    /**
     * Sets sun exposure.
     *
     * @param direction the direction
     * @return the sun exposure
     */
    private static String setSunExposure(String direction) {
        String sunExposure;
        direction = direction.toUpperCase();

        switch (direction) {
            case "N":
                sunExposure = "North";
                break;
            case "S":
                sunExposure = "South";
                break;
            case "E":
                sunExposure = "East";
                break;
            case "W":
                sunExposure = "West";
                break;
            default:
                sunExposure = "Unknown";
                break;
        }
        return sunExposure;
    }

    /**
     * Read phone or tax number string.
     *
     * @param phoneNumber the phone number
     * @return the string
     */
//phone
    private static String readPhone_or_taxNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
        return phoneNumber;
    }

}

