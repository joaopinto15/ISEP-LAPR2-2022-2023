# US 019 - 

# 4. Tests 

**Test 1:** Check that it returns correctly an agency list from agency repository

	@Test
    public void testGetAgencyList() {
        AgencyRepository agencyRepository = new AgencyRepository();
        assertNotNull(agencyRepository.getAgencyList());
        assertTrue(agencyRepository.getAgencyList().isEmpty());
    }

# 5. Construction (Implementation)


## Class PropertySubsetService 

```java
public class PropertySubsetService {
    List<Agency> properties;

    public PropertySubsetService(List<Agency> properties) {
        this.properties = properties;
    }

    private ArrayList<Map.Entry<ArrayList<Agency>, ArrayList<Agency>>> getSubsets() {
        ArrayList<Map.Entry<ArrayList<Agency>, ArrayList<Agency>>> subsets = new ArrayList<>();
        int n = properties.size();

        for(int i = 1; i <= Math.pow(n,2)-1; i++) { // starting with 1 to prevent empty subset
            ArrayList<Agency>firstSubset = new ArrayList<>();
            ArrayList<Agency>secondSubset = new ArrayList<>();
            int k = i; // preserve i value
            for(int j= 0; j < n;j++) {
                int bit = k & 1; // get last bit
                if((bit & j)==1) {
                    firstSubset.add(properties.get(j));
                } else {
                    secondSubset.add(properties.get(j));
                }
                k = k >> 1; // shift to next bit
            }
            subsets.add(Map.entry(firstSubset, secondSubset));
        }
        return subsets;
    }

    public Map.Entry<ArrayList<Agency>, ArrayList<Agency>> getBestSubset() {
        int n = properties.size();

        ArrayList<Map.Entry<ArrayList<Agency>, ArrayList<Agency>>> subsets = getSubsets();
        int lowestDifference = Integer.MAX_VALUE;
        Map.Entry<ArrayList<Agency>, ArrayList<Agency>> bestSubset = null;

        for (Map.Entry<ArrayList<Agency>, ArrayList<Agency>> subset : subsets) {
            ArrayList<Agency> firstSub = subset.getKey();
            ArrayList<Agency> secondSub = subset.getValue();
            int difference = Math.abs(getSumOfProperties(firstSub) - getSumOfProperties(secondSub));

            if (difference < lowestDifference) {
                bestSubset = subset;
                lowestDifference = difference;
            }
        }
        return bestSubset;
    }

    private int getSumOfProperties(List<Agency>Agencys) {
        int sum = 0;
        for(Agency Agency : Agencys)
            sum += Agency.getTotalNumberOfAgency();

        return sum;
    }
}

```


## Class AgencyRepository

```java
/**
 * The type Agency repository.
 */
public class AgencyRepository implements Serializable {
    /**
     * the list containing the agencies registered in the system
     */
    private ArrayList<Agency> agencyList = new ArrayList<>();

    /**
     * Method to return the agency list
     *
     * @return the agency List
     */
    public ArrayList<Agency> getAgencyList() {
        return this.agencyList;
    }

    /**
     * This function adds an Agency
     *
     * @param agency the agency
     * @return false if it isn't added, true if it is
     */
    public boolean addAgency(Agency agency){
        if(agency == null){
            return false;
        }
        if(validateAgency(agency)){
            return false;
        }
        boolean add=this.agencyList.add(agency);
        serializeagencyList();
        return add;
    }

    /**
     * Gets agency.
     *
     * @param agencyId the agency id
     * @return the agency
     */
    public Agency getAgency(String agencyId) {
        for (Agency agency : this.agencyList) {
            if (agency.getId().equals(agencyId)) {
                return agency;
            }
        }
        return null;
    }

    /**
     * Method to check if the agency already exists
     *
     * @param agency the agency
     * @return false if it doesn't exist, true if it does
     */
    public boolean validateAgency(Agency agency){
        return this.agencyList.contains(agency);
    }

    /**
     * this function creates an instance of agency
     *
     * @param id           holds the id of the agency
     * @param address      holds the address of the agency
     * @param designation  holds the designation of the agency
     * @param phoneNumber  holds the phone number of the agency
     * @param emailAddress holds the email address of the agency
     * @return a new instance of agency
     */
    public Agency createAgency(String id, Address address, String designation, String phoneNumber, String emailAddress) {
        if (!validateAgency(new Agency(id, address, designation, phoneNumber, emailAddress))){
            return new Agency(id, address, designation, phoneNumber, emailAddress);
        }
        return null;
    }

    public void serializeagencyList(){
        try {
            File folder = new File(storageFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("an error occurred when creating the folder -> " + storageFolder + " <-");
                    return;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(storageFolder + File.separator + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(agencyList);
            objectOut.close();
            fileOut.close();
            System.out.println("A lista foi salva com sucesso.");//take out
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserializeagencyList(){
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            agencyList = (ArrayList<Agency>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String fileName="agencyList.byte";
    private static final String storageFolder="serialized.files";

    public boolean addAgencyWithoutSerializer(Agency agency) {
        if(agency == null){
            return false;
        }
        if(validateAgency(agency)){
            return false;
        }

        return this.agencyList.add(agency);

    }
}

```

# 6. Integration and Demo 

* Let's fetch the agencies from the repository instead of reading the csv file again

* Some demo purposes some agencies are bootstrapped while system starts.





