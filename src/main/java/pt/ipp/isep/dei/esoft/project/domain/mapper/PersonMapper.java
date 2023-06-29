package pt.ipp.isep.dei.esoft.project.domain.mapper;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.dto.PersonDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The PersonMapper class provides static methods for mapping between Person and
 * PersonDTO objects.
 */
public class PersonMapper implements Serializable {
    /**
     * Converts a list of Person objects to a list of PersonDTO objects.
     *
     * @param persons The list of Person objects to be converted.
     * @return The list of PersonDTO objects.
     */
    public static List<PersonDTO> toDto(List<Person> persons) {

        List<PersonDTO> personDTOs = new ArrayList<>();

        for(Person p: persons){
            PersonDTO personDto;

            personDto = toDto(p);
            personDTOs.add(personDto);
        }

        return personDTOs;
    }

    /**
     * Converts a list of PersonDTO objects to a list of Person objects.
     *
     * @param personDTOs The list of PersonDTO objects to be converted.
     * @return The list of Person objects.
     */
    public static List<Person> toModel(List<PersonDTO> personDTOs) {

        List<Person> persons = new ArrayList<>();

        for (PersonDTO pDto : personDTOs) {
            Person person;

            person = toModel(pDto);
            persons.add(person);
        }

        return persons;
    }

    /**
     * Converts an array of String data to a PersonDTO object.
     *
     * @param data The array of String data representing a PersonDTO.
     * @return The PersonDTO object.
     */
    public static PersonDTO toDto(String[] data) {
        PersonDTO personDto;
        ArrayList<UserRoleDTO> DefaultUserRoleDTO= new ArrayList<UserRoleDTO>();
        DefaultUserRoleDTO.add(new UserRoleDTO(AuthenticationController.ROLE_CLIENT,AuthenticationController.ROLE_CLIENT));
        personDto = new PersonDTO(data[1], Integer.parseInt(data[2]),
                data[3],  data[4], data[5],DefaultUserRoleDTO);

        return personDto;
    }

    /**
     * Converts a Person object to a PersonDTO object.
     *
     * @param person The Person object to be converted.
     * @return The PersonDTO object.
     */
    public static PersonDTO toDto(Person person) {

        PersonDTO personDto;

        personDto = new PersonDTO(person.getName(), person.getPassportNumber(), person.getTaxNumber(),
                person.getEmailAddress().toString(), person.getPhoneNumber(), Repositories.getInstance().getAuthenticationRepository().getUser(person.getEmailAddress()).get().getRoles());

        return personDto;
    }

    /**
     * Converts a PersonDTO object to a Person object.
     *
     * @param personDto The PersonDTO object to be converted.
     * @return The Person object.
     */
//Authlib contains a mapper that does not feature toModel, is having this a good idea?
    public static Person toModel(PersonDTO personDto) {

        Person person;

        //person = new Person(personDto.getName(), personDto.getPassportNumber(), personDto.getTaxNumber(),
        //        personDto.getEmailAddress(), personDto.getPhoneNumber(), personDto.getRoleId());
        person = Repositories.getInstance().getPersonRepository().getPersonById(new Email(personDto.getEmailAddress()));
        return person;
    }
}
