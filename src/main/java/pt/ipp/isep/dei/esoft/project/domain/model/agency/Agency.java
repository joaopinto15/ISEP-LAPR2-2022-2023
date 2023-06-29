package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The type Agency.
 */
public class Agency implements Serializable {
    /**
     * this variable holds the id of the agency
     */
    private final String id;
    /**
     * this variable holds the address of the agency
     */
    private final Address address;
    /**
     * this variable holds the designation of the agency
     */
    private final String designation;
    /**
     * this variable holds the phone number of the agency
     */
    private final String phoneNumber;
    /**
     * this variable holds the email of the agency
     */
    private final String emailAddress;
    /**
     * this variable holds the manager of the agency
     */
    private Employee manager;

    /**
     * The Number of agencies.
     */
    private int numberOfAgencies = 0;

    /**
     * This function creates an instance receiving id, address, designation, phoneNumber and emailAddress as parameters
     *
     * @param id           the variable id holds the id of the agency
     * @param address      the variable address holds the address of the agency
     * @param designation  the variable designation holds the designation of the agency
     * @param phoneNumber  the variable phoneNumber holds the phone number of the agency
     * @param emailAddress the variable emailAddress holds the email address of the agency
     */
    public Agency(String id, Address address, String designation, String phoneNumber, String emailAddress) {

        this.id = id;
        this.address = new Address(address);
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * This function creates an instance of Agency with the same parameters as Agency
     *
     * @param otherAgency the agency with the parameters to copy
     */
    public Agency(Agency otherAgency) {
        id = otherAgency.id;
        address = otherAgency.address;
        designation = otherAgency.designation;
        phoneNumber = otherAgency.phoneNumber;
        emailAddress = otherAgency.emailAddress;
        numberOfAgencies++;
    }

    /**
     * Method to return the id of the agency
     *
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * method to return the address of the agency
     *
     * @return address address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * method to return the phone number of the agency
     *
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * method to return the email address of the agency
     *
     * @return email address
     */
    public Email getEmailAddress() {
        return new Email(emailAddress);
    }

    /**
     * method to return the manager of the agency
     *
     * @return manager manager
     */
    public Employee getManager() {
        return manager;
    }

    /**
     * method to return the designtation of the agency
     *
     * @return designation designation
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * checks if the Agency has a manager
     *
     * @return false if it doesn't, true if it does
     */
    public boolean hasManager() {
        return this.manager != null;
    }

    /**
     * This method sets an employee as a manager to the agency
     *
     * @param manager the manager
     */
    public void setManager(Employee manager) {

        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agency agency = (Agency) o;
        return numberOfAgencies == agency.numberOfAgencies && Objects.equals(id, agency.id) && Objects.equals(address, agency.address) && Objects.equals(designation, agency.designation) && Objects.equals(phoneNumber, agency.phoneNumber) && Objects.equals(emailAddress, agency.emailAddress) && Objects.equals(manager, agency.manager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, designation, phoneNumber, emailAddress, manager, numberOfAgencies);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {


        return "Agency:" + "\n" +
                "\n     -> Id: " + id +
                "\n     -> Id: " + address +
                "\n     -> Id: " + designation +
                "\n     -> Id: " + phoneNumber +
                "\n     -> Id: " + emailAddress;

    }


    /**
     * Gets total number of agency properties.
     *
     * @return the total number of agency properties
     */
    public int getTotalNumberOfAgencyProperties() {
       /** Map<Agency, Integer> agencyPropertyMap = new HashMap<>();
        List<Announcement> announcementList = Repositories.getInstance().getAnnouncementRepository().getAnnouncementList();
        for (Announcement announcement : announcementList) {
            String agencyId = announcement.getRequest().getEmployee().getAgencyId();
            Agency agency = Repositories.getInstance().getAgencyRepository().getAgency(agencyId);
            numberOfAgencies = numberOfAg
            }
        return agencyPropertyMap.get(this);
        */
      return numberOfAgencies;
    }

}

