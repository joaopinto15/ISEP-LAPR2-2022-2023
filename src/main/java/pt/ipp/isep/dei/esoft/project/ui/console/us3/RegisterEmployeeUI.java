package pt.ipp.isep.dei.esoft.project.ui.console.us3;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.controller.RegisterEmployeeController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Message;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;

/**
 * The type Register employee ui.
 */
public class RegisterEmployeeUI implements Runnable {
    /**
     * Instantiates a new Register employee ui.
     */
    public RegisterEmployeeUI() {
    }

    /**
     * Run.
     */
    public void run() {
        try {

            //Sorry, I completely forgot that all our authentication systems require passwords with seven alphanumeric characters in length , including three capital letters and two digits. The password should be generated automatically. The password is sent to the employee by e-mail.

            RegisterEmployeeController controller = new RegisterEmployeeController();
            System.out.println("Register an Employee");
            System.out.println("Choose an agency to where the employee will work:");
            Agency agency = (Agency) Utils.showAndSelectOne(controller.getAgencies(), "Agencies:", true);
            if (agency == null) {
                System.out.println("No agency was chosen.");
                return;
            }
            String agencyId = agency.getId();
            String[] roles = chooseRole();
            //Person attributes

            String name;
            boolean isValidName;
            do {
                name = Utils.readLineFromConsole("### Insert your name ### (ex: Haaland)");
                isValidName = Utils.confirmNames(name);
                if(isValidName){
                    System.out.println("Name is Valid: "+name);
                }else{
                    System.out.println("Not a valid Name, Try Again");
                }
            }while(!isValidName);


            String emailAddress;
            boolean isValidEmail;
            do {
                emailAddress = Utils.readLineFromConsole("### Insert your Email Address ### (ex: example@gmail.com)");
                isValidEmail=Utils.confirmEmail(emailAddress);
                if(isValidEmail){
                    System.out.println("Email Address is Valid: "+ emailAddress);
                }else{
                    System.out.println("Not Valid Email Address, Try Again");
                }
            }while(!isValidEmail);

            int passportNumber;
            boolean isValidPassportNumber;
            do {
                passportNumber = Utils.readIntegerFromConsole("### Insert your Passport Card Number ###");
                isValidPassportNumber = Utils.confirmInt9Digits(passportNumber);
                if(isValidPassportNumber){
                    System.out.println("The Passport Number is Valid: "+ passportNumber);
                }else{
                    System.out.println("Not Valid Passport Number, Try Again");
                }

            }while(!isValidPassportNumber);

            String taxNumber;
            boolean isValidTaxNumber;
            do {
                taxNumber = Utils.readLineFromConsole("### Insert your Tax Number ### (ex: 123456789)");
                isValidTaxNumber = Utils.confirm9DigitString(taxNumber);
                if (isValidTaxNumber){
                    System.out.println("Tax Number is Valid: "+taxNumber);
                }else{
                    System.out.println("Not valid Tax Number, Try Again");
                }
            }while(!isValidTaxNumber);

            String phoneNumber;
            boolean isValidPhoneNumber;

            do {
                phoneNumber = Utils.readLineFromConsole("### Insert your Phone Number ###(ex: 919191919)");
                isValidPhoneNumber = Utils.confirm9DigitString(phoneNumber);
                if(isValidPhoneNumber){
                    System.out.println("Phone Number is Valid: "+phoneNumber);
                }else{
                    System.out.println("Not a valid Phone Number, Try Again");
                }
            }while(!isValidPhoneNumber);


            //Address attributes
            String street = Utils.readLineFromConsole("Insert the employee's street (ex:Rua dos Cravos)");


            String city;
            boolean isValidCity;
            do {
                city = Utils.readLineFromConsole("Insert the employee's city (ex: Póvoa de Varzim)");
                isValidCity=Utils.confirmNames(city);
                if(isValidCity){
                    System.out.println("City is Valid: "+city);
                }else{
                    System.out.println("Not a Valid City Name, Try Again");
                }
            }while(!isValidCity);

            String district;
            boolean isDistrictValid;
            do {
                district = Utils.readLineFromConsole("Insert the employee's district (ex: Barcelona)");
                isDistrictValid = Utils.confirmNames(district);
                if(isDistrictValid){
                    System.out.println("District is Valid: "+district);
                }else{
                    System.out.println("Not a Valid District, Try Again");
                }
            }while(!isDistrictValid);

            String state;
            boolean isStateValid;
            do {
                state = Utils.readLineFromConsole("Insert the employee's state (ex: California)");
                isStateValid = Utils.confirmNames(state);
                if(isStateValid){
                    System.out.println("State is Valid: "+state);
                }else{
                    System.out.println("Not a Valid State, Try Again");
                }
            }while(!isStateValid);

            String zipcode;
            boolean isValidZipCode;
            do {
                zipcode = Utils.readLineFromConsole("Insert the employee's zipcode (ex: 12345)");
                isValidZipCode = Utils.confirm5DigitString(zipcode);
                if(isValidZipCode){
                    System.out.println("Zip code Valid: "+zipcode);
                }else{
                    System.out.println("Not a Valid Zip Code, Try Again");
                }
            }while(!isValidZipCode);


            //Password
            String password;

                password = controller.generatePassword();
                Message message = new Message("A password has been Generated ",Utils.getEmployeeInSession(), new Email(emailAddress),"Hello "+name+" this is your password "+ password +", looking forward to see you working!");
                message.send();
                System.out.println("### A password has been generated for the employee and sent to the employee's email ###");


            //confirmation
            System.out.println("Confirm the following employee's information\nName: " + name +
                            "\nPassport Number" + passportNumber +
                            "\nTax Number: " + taxNumber + "\nEmail Address: " + emailAddress +
                            "\nPhone Number: " + phoneNumber +
                            "\nStreet: " + street +
                            "\nCity: " + city +
                            "\nDistrict: " + district +
                            "\nState: " + state +
                            "\nZipcode: " + zipcode +
                            "\nAgency: " + agencyId);

            boolean confirm = Utils.confirm("Are you sure you want to register this employee?(y/n)");

            if (confirm) {
                Employee employee = new Employee(name, passportNumber, taxNumber, phoneNumber, emailAddress, password,roles , new Address(street, city, district, state, zipcode), agencyId);

                if(controller.registerEmployee(employee,password)){
                    System.out.println("Employee was registered successfully");
                } else {
                    System.out.println("Employee registration has failed");
                }
            } else {
                System.out.println("Employee registration was not confirmed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Choose role string [ ].
     *
     * @return the string [ ]
     */
    private String[] chooseRole() {
        ArrayList<String> roles = new ArrayList<>();
        roles.add(AuthenticationController.ROLE_EMPLOYEE);
        roles.add(AuthenticationController.ROLE_MANAGER);
        roles.add(AuthenticationController.ROLE_NETWORK_MANAGER);
        roles.add(AuthenticationController.ROLE_ADMIN);
        int chosenRole = Utils.showAndSelectIndex(roles, "Choose the role of the employee", false);
        switch (chosenRole) {
            case 0:
                return new String[]{AuthenticationController.ROLE_EMPLOYEE};
            case 1:
                return new String[]{AuthenticationController.ROLE_MANAGER};
            case 2:
                return new String[]{AuthenticationController.ROLE_NETWORK_MANAGER};
            case 3:
                return new String[]{AuthenticationController.ROLE_ADMIN};
        }
        return new String[]{AuthenticationController.ROLE_EMPLOYEE};
    }


}
