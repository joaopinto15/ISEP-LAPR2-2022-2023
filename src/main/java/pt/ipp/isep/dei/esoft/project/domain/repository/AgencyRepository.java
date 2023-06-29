package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
     * Save agency boolean.
     *
     * @param agencyList the agency list
     * @return the boolean
     */
    public boolean saveAgency(List<Agency> agencyList) {
        List<Agency> validAgencies = new ArrayList<>();
        for (Agency agency : agencyList) {
            if (validateAgency(agency)) {
                validAgencies.add(agency);
            }    
        }
        boolean add = addAgencies(validAgencies);
        System.out.println(validAgencies.size());
        if(add){
            serializeagencyList();
        }
        return add;
    }

    /**
     * Add agencies boolean.
     *
     * @param agencyList the agency list
     * @return the boolean
     */
    private boolean addAgencies(List<Agency> agencyList) {
        return this.agencyList.addAll(agencyList);
    }

    /**
     * Method to check if the agency already exists
     *
     * @param agency the agency
     * @return false if it doesn't exist, true if it does
     */
    public boolean validateAgency(Agency agency){
        return true;
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

    /**
     * Serializeagency list.
     */
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializeagency list.
     */
    public void deserializeagencyList(){
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            agencyList = (ArrayList<Agency>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

        } catch (FileNotFoundException e) {
            // Lidar com a exceção: exibir mensagem de erro ou realizar ação alternativa
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The constant fileName.
     */
    private static final String fileName="agencyList.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder="serialized.files";

    /**
     * Add agency without serializer boolean.
     *
     * @param agency the agency
     * @return the boolean
     */
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
