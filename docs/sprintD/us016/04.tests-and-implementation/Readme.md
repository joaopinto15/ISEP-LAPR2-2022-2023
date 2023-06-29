# US 016 - I want to respond to the user that scheduled the visit

# 5. Construction (Implementation)


## Class RespondVisitRequestController 

```java
    public class RespondVisitRequestController {

    
    public boolean notifyClient(Visit visit, String text, boolean isAccepted) {
        if(DomainCheck.checkDomain()){
            if(isAccepted) return sendAcceptanceEmail(visit, text);
            else  return sendRejectionEmail(visit, text);
        }
        return false;
    }
    public boolean sendAcceptanceEmail(Visit visit, String text) {
        String subject = "Dear " +
                visit.getChosenAnnouncement().getRequest().getUser().getName() + ",\n\n" +
                "Your visit has been successfully scheduled to the Property bellow:\n\n" +
                visit.getChosenAnnouncement().getRequest().getProperty();
        Message message = new Message(subject,visit.getChosenAnnouncement().getRequest().getEmployee(),new Email(visit.getEmail()), text);
        return message.send();
    }
    public boolean sendRejectionEmail(Visit visit, String text) {
        String subject = "Dear " +
                visit.getChosenAnnouncement().getRequest().getUser().getName() + ",\n\n" +
                "Your visit has been rejected to the Property bellow:\n\n" +
                visit.getChosenAnnouncement().getRequest().getProperty();
        Message message = new Message(subject,visit.getChosenAnnouncement().getRequest().getEmployee(),new Email(visit.getEmail()), text);
        return message.send();

    }
    public boolean deleteVisit(Visit visit){
        return Repositories.getInstance().getVisitRepository().deleteVisit(visit);
    }
    
}
```


## Class DomainCheck

```java
public class DomainCheck {


    public DomainCheck() {
    }

    public static boolean checkDomain() {
        try (InputStream input = DomainCheck.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

        String domain = prop.getProperty("Domain.Inuse") ;
            if (domain.equals("dei") || domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo")){
                return true;
            }else{
                throw new IllegalArgumentException("Unsupported email domain: " + domain);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        
    }
 }

```

# 6. Integration and Demo 

* (In the GUI) A new option on the Employee menu options was added.

* Some visits are bootstrapped while system starts.







