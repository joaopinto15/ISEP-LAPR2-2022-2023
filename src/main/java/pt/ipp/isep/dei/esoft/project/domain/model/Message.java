package pt.ipp.isep.dei.esoft.project.domain.model;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import pt.ipp.isep.dei.esoft.project.application.session.ApplicationSession;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.util.Properties;
import java.util.UUID;

/**
 * The Message class represents a message entity with various attributes such as subject, sender, recipient, date, time,
 * and body.
 */
public class Message implements Serializable {

    /**
     * The Type
     */
    private final boolean isEmail;

    /**
     * The Subject.
     */
    private String subject = null;

    /**
     * The Sender.
     */
    private final Employee from;

    /**
     * The Receiver.
     */
    private final String to;

    /**
     * The Message.
     */
    private final String messageBody;

    /**
     * The Id.
     */
    private UUID id;

    /**
     * Constructs an email message.
     *
     * @param subject     the subject of the email
     * @param from        the sender of the email
     * @param to          the recipient of the email
     * @param messageBody the body of the email message
     */
    public Message(String subject, Employee from, Email to, String messageBody) {
        this.isEmail = true;
        this.subject = subject;
        this.from = from;
        this.to = to.toString();
        this.messageBody = messageBody;
        this.id = UUID.randomUUID();
    }


    /**
     * Constructs a sms message.
     *
     * @param from        the sender of the direct message
     * @param to          the recipient of the direct message
     * @param messageBody the body of the direct message
     */
    public Message(Employee from, String to, String messageBody) {
        this.isEmail = false;
        this.subject = null;
        this.from = from;
        this.to = to;
        this.messageBody = messageBody;
    }

    /**
     * Retrieves the type of the message.
     *
     * @return The boolean object representing the type.
     */
    public boolean isEmail() {
        return isEmail;
    }

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public UUID getUuid() {
        return id;
    }

    /**
     * Retrieves the subject of the message.
     *
     * @return The String object representing the subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Retrieves the sender of the message.
     *
     * @return The Employee object representing the sender.
     */
    public Employee getFrom() {
        return from;
    }

    /**
     * Retrieves the recipient of the message.
     *
     * @return The Person object representing the recipient.
     */
    public String getTo() {
        return to;
    }

    /**
     * Sends the message, either as an email or a direct message, and saves it to the appropriate storage.
     * If the message is an email, it will be saved as a text file with the email content.
     * If the message is a direct message, it will be saved as a text file with the message content.
     * Uses the configuration properties defined in "config.properties" for the Twilio API and storage folders.
     *
     * @return the boolean
     */
    public boolean send() {
        try (InputStream input = Message.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            if (isEmail) {
                return save(null);
            } else {
                final String ACCOUNT_SID = prop.getProperty("Twilio.SID");
                final String AUTH_TOKEN = prop.getProperty("Twilio.Token");

                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                String toPhoneNumberTest = "+351968586745";
                String fromPhoneNumber = "+13612649165"; // Twilio number

                String message = "To: " + to + "\n\n" + messageBody + "\nThis is an automated message from the system. Please do not reply to this sms.";

                try {
                    com.twilio.rest.api.v2010.account.Message messageTwilio = com.twilio.rest.api.v2010.account.Message.creator(
                                    new PhoneNumber(toPhoneNumberTest),
                                    new PhoneNumber(fromPhoneNumber),
                                    message)
                            .create();
                } catch (Exception e) {
                    System.out.println("An error occurred while sending an Message: " + e.getMessage());
                } finally {
                    save(message);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
        
    }

    /**
     * Saves the message, either as an email or a direct message, to the appropriate storage.
     * If the message is an email, it will be saved as a text file with the email content.
     * If the message is a direct message, it will be saved as a text file with the message content.
     * Uses the configuration properties defined in "config.properties" for the storage folders.
     *
     * @param message the content of the message to be saved
     * @return the boolean
     */
    public boolean save(String message) {
        Properties prop = ApplicationSession.getInstance().getProperties();

        if (isEmail) {
            File folder = new File(prop.getProperty("Email.StorageFolder"));
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("an error occurred when creating the folder");
                    return false;
                }
            }
            String filePath = folder + File.separator + to +"#"+ id + ".txt";
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write("#-#-#-#-#-#-#\n" + "From: " + from.getName() + " at " + prop.getProperty("Company.Designation") + " <" + prop.getProperty("Email.CompanyEmailAddress") + "> Phone number:"+ from.getPhoneNumber() + "\nTo: " + to + "\n" + "Subject: " + subject + "Date: " + Date.currentDate() + "\nTime: " + Time.currentTime() + "\n\nAgent Message:\n\n" + messageBody + "\nThis is an automated message from the system. Please do not reply to this email.\n#-#-#-#-#-#-#\n");
                return true;
            } catch (IOException e) {
                System.out.println("An error occurred while saving an email:");
                e.printStackTrace();
                return false;
            }
        } else {
            File folder = new File(prop.getProperty("Sms.StorageFolder"));
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("an error occurred when creating the folder");
                    return false;
                }
            }
            String filePath = folder + File.separator + to + ".txt";
            try (FileWriter writer = new FileWriter(filePath, true)) {
                writer.write(message);
                return true;
            } catch (IOException e) {
                System.out.println("An error occurred while saving an sms:");
                e.printStackTrace();
                return false;
            }
        }
    }
}
