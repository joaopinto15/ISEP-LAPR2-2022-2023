package pt.ipp.isep.dei.esoft.project.ui.console.us7;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.controller.RegisterUserController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;

/**
 * The type Register user ui.
 */
public class RegisterUserUI implements Runnable {
    /**
     * Instantiates a new Register user ui.
     */
    public RegisterUserUI() {
    }

    /**
     * Run.
     */
    public void run() {

        String[]roles = new String[1];
        roles[0]=AuthenticationController.ROLE_CLIENT;
        RegisterUserController controller = new RegisterUserController();
        System.out.println("Register User:");

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
            passportNumber = Utils.readIntegerFromConsole("### Insert your Passport Card Number ### (ex: 321321321");
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


        //Password
            String password;
            boolean isValidPassword = false;
    do {
        password = Utils.readLineFromConsole(
            "Insert your password.\n " +
                    "Must be seven alphanumeric characters in length \n" +
                    "Must have at least 2 digits \n" +
                    "Must have at least 3 Capital Letters ");
        isValidPassword = controller.confirmPassword(password);

        if (isValidPassword) {
        System.out.println("Password is Valid: " + password);
        } else {
        System.out.println("Not a Valid Password, Try Again: " + password);
        }
    }while(!isValidPassword);


        //Confirmation
        System.out.println("### Confirm the following information ###");
        System.out.println("Name: " + name);
        System.out.println("Citizen Card Number: " + passportNumber);
        System.out.println("Tax Number: " + taxNumber);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Password: " + password);

        boolean confirm = Utils.confirm("### Do you want to register? (y/n) ###");
        if (confirm) {
            if (controller.saveUser(new Person(name, passportNumber, taxNumber, emailAddress, phoneNumber, password, roles),password)) {
                System.out.println("### You have been successfully registered ###");
            } else {
                System.out.println("### Registration has failed! ###");
            }
        } else {
            System.out.println("### The Registration has been canceled! ###");
        }
    }




}
