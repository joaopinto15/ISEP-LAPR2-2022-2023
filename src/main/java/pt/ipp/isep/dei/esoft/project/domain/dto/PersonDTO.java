package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.Serializable;
import java.util.List;

/**
 * The PersonDTO class represents a Data Transfer Object (DTO) for a person's
 * information.
 */
public class PersonDTO implements Serializable {
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Passport number.
     */
    private final int passportNumber;
    /**
     * The Tax number.
     */
    private final String taxNumber;
    /**
     * The Email address.
     */
    private final String emailAddress;
    /**
     * The Phone number.
     */
    private final String phoneNumber;
    /**
     * The Role id.
     */
    private final List<UserRoleDTO> roleId;


    /**
     * Constructs a PersonDTO object with the specified information.
     *
     * @param name           The name of the person.
     * @param passportNumber The passport or citizen card number of the person.
     * @param taxNumber      The tax number of the person.
     * @param emailAddress   The email address of the person.
     * @param phoneNumber    The phone number of the person.
     * @param roleId         The role ID of the person.
     */
    public PersonDTO(String name, int passportNumber, String taxNumber, String emailAddress, String phoneNumber, List<UserRoleDTO> roleId) {

        this.name = name;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
    }

    /**
     * Gets the name of the person.
     *
     * @return The name of the person.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Gets the passport or citizen card number of the person.
     *
     * @return The passport or citizen card number of the person.
     */
    public int getPassportNumber(){
        return this.passportNumber;
    }

    /**
     * Gets the tax number of the person.
     *
     * @return The tax number of the person.
     */
    public String getTaxNumber(){
        return this.taxNumber;
    }

    /**
     * Gets the email address of the person.
     *
     * @return The email address of the person.
     */
    public String getEmailAddress(){
        return this.emailAddress;
    }

    /**
     * Gets the phone number of the person.
     *
     * @return The phone number of the person.
     */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    /**
     * Gets the role ID of the person.
     *
     * @return The role ID of the person.
     */
    public List<UserRoleDTO> getRoleId(){
        return this.roleId;
    }

}