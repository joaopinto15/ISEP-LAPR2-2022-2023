# US 012 - Import information from a legacy system

# 4. Tests 


# 5. Construction (Implementation)


## Class ImportBusinessController 

```java
public class ImportBusinessController {

    private AuthenticationRepository authenticationRepository;
    private PersonRepository personRepository;
    private AnnouncementRepository announcementRepository;
    private AgencyRepository agencyRepository;

   

    /**
     * Constructor for ImportBusinessController class.
     * Initializes repositories and mapper.
     */
    public ImportBusinessController() {

        authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        personRepository = Repositories.getInstance().getPersonRepository();
        announcementRepository = Repositories.getInstance().getAnnouncementRepository();
        agencyRepository = Repositories.getInstance().getAgencyRepository();

    }

    /**
     * Checks if the file path has a CSV file extension.
     *
     * @param filePath The path of the file to check.
     * @return true if the file has a CSV extension, false otherwise.
     */
    public  boolean checkFileFormat(String filePath) {
        return checkFilePathEmpty(filePath) && checkFilePathIsCsv(filePath) && checkFileExists(filePath) ;
    }
    private boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    private  boolean checkFilePathEmpty(String filePath) {
        return !filePath.trim().isEmpty();
    }
    private  boolean checkFilePathIsCsv(String filePath) {
        return filePath.endsWith(".csv");
    }
    public  boolean checkFileIsEmpty(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));     
        if (br.readLine() == null) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the file content matches the expected format.
     *
     * @param filePath The path of the file to check.
     * @return true if the file content matches the expected format, false
     *         otherwise.
     * @throws FileNotFoundException If the file is not found.
     */
    public  boolean checkFileContent(String filePath) {
        try (InputStream input = ImportBusinessController.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            String headerContent;
            
            if(file.exists() && file.isFile()){
                headerContent = sc.nextLine();
                
                if(headerContent.equals(prop.getProperty("Csv.Header")) && sc.nextLine().trim().length() > 0  ){
                    sc.close();
                    return true;
                }
            }
            
            sc.close();
            
            return false;
            
        } catch (IOException ex) {
        ex.printStackTrace();
        return false;}
    }
    
    /**
     * Checks the number of lines in the file.
     *
     * @param filePath The path of the file to check.
     * @return The number of non-empty lines in the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public  int checkNumberOfLines(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        int count = 0;
        String line;
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.trim().length() > 0) {
                count++;
            }
        }
        sc.close();
        return count;
    }
    
    /**
     * Reads the file and returns the data as a two-dimensional array.
     *
     * @param filePath     The path of the file to read.
     * @param numberOfrows The number of rows to read from the file.
     * @return The data read from the file as a two-dimensional array.
     * @throws FileNotFoundException If the file is not found.
     */
    public  String[][] readFile(String filePath, int numberOfrows) throws FileNotFoundException {

        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        int count = 0;
        numberOfrows -= 1;
        sc.nextLine();
        String[][] data = new String[numberOfrows][30];

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] value = line.split(";");
            int numberOfcolumns = value.length;
            int j;
            for (j = 0; j < numberOfcolumns; j++) {
                data[count][j] = value[j];
            }
            count++;
        }
        sc.close();
        return data;
    }

    public boolean importData(String data[][], int numberOFLines) throws FileNotFoundException, ParseException {
        TransformFileInAnnouncement announcementsList;
        announcementRepository.removeAllAnnouncements();

        announcementsList = TransformFileInAnnouncement.transforminObject(data,numberOFLines);
        agencyRepository.saveAgency(announcementsList.getAgencyList());
        return announcementRepository.saveAnnouncement(announcementsList.getAnnouncementsList());
    }
}
```


## Class TransformFileInAnnouncement

```java
public class TransformFileInAnnouncement {



    private List<Announcement> announcementsList;
    private List<Agency> agencyList;

    public TransformFileInAnnouncement(List<Announcement> announcementsList, List<Agency> agencyList) {
        this.announcementsList = announcementsList;
        this.agencyList = agencyList;
    }

    public List<Announcement> getAnnouncementsList() {
        return announcementsList;
    }

    public List<Agency> getAgencyList() {
        return agencyList;
    }

    /**
     * Transformin object list.
     *
     * @param file the file
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
                    if(agency.getId().equals(x.getId())){
                         add = false;
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


```

# 6. Integration and Demo 

* A new option on the Admin menu options was added.








