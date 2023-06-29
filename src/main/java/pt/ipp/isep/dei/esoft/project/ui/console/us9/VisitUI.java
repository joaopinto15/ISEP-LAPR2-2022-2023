package pt.ipp.isep.dei.esoft.project.ui.console.us9;

import pt.ipp.isep.dei.esoft.project.domain.controller.CreateAnnouncementController;
import pt.ipp.isep.dei.esoft.project.domain.controller.VisitController;
import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Time;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.ui.console.ShowTextUI;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Schedule ui.
 */
public class VisitUI implements Runnable {


    /**
     * The Ctrl announcement.
     */
    private final CreateAnnouncementController ctrlAnnouncement = new CreateAnnouncementController();
    /**
     * The Ctrl visit.
     */
    private final VisitController ctrlVisit = new VisitController();
    /**
     * The Announcements.
     */
    final List<AnnouncementDTO> announcements = ctrlAnnouncement.getAnnouncementList();
    /**
     * The Current person.
     */
    private final Person currentPerson = ctrlVisit.getCurrentPerson();
    /**
     * The Name.
     */
    private final String name = currentPerson.getName();
    /**
     * The Phone number.
     */
    private final String phoneNumber = currentPerson.getPhoneNumber();


    /**
     * The Email.
     */
    private final String email = currentPerson.getEmailAddress().toString();
    /**
     * The Choosen anounce.
     */
    private Announcement chosenAnnouncement;

    /**
     * Run.
     */
    public void run() {
        boolean leave =chooseProperty();
        if(leave){
            return;
        }
        System.out.println("Enter the date of visit (YYYY-MM-DD)");
        Date date = chooseDate();

        System.out.println("\nEnter the hours of visit");
        System.out.print("\nStart visit");
        Time startTime = chooseTime();
        Time endTime;
        boolean checkTime;
        do {
            checkTime=true;
            System.out.print("End visit");
            endTime = chooseTime();
            if (startTime.getHours() >= endTime.getHours()) {

                if (startTime.getMinutes() >= endTime.getMinutes()){
                    checkTime=false;
                    System.out.println("The end of visit should be after the start of the visit");
                }
            }
        } while (!checkTime);

        Visit visit = new Visit(name, phoneNumber, date, startTime, endTime, email, chosenAnnouncement);
        //verify if is valid
        if (sendRequest(visit)) {
            System.out.println("Visit was agended for " + date.toString());
            System.out.println("Starts: " + startTime);
            System.out.println("Ends: " + endTime);
            //save on a file the visit

        }
    }

    /**
     * Choose property.
     *
     * @return the boolean
     */
    public boolean chooseProperty() {
        AnnouncementDTO option;
            option = (AnnouncementDTO) Utils.showAndSelectOne(announcements, "\n\nProperties", true);
            if (option == null) {
                return true;
            } else {
                chosenAnnouncement = AnnouncementMapper.toModel(option);
                return false;
            }
        

    }

    /**
     * Choose date date.
     *
     * @return the date
     */
    public Date chooseDate() {
        boolean check_date = false;
        int year = 0, month = 0, day = 0;

        do {
            String[] line = Utils.readNonNullLineFromConsole("Choose the date(YYYY-MM-DD)").split("-");

            if (line.length == 3) {
                try {
                    year = Integer.parseInt(line[0]);
                    month = Integer.parseInt(line[1]);
                    day = Integer.parseInt(line[2]);

                    check_date = Date.checkDate(year, month, day);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please enter the date in the format YYYY-MM-DD.");
                }
            } else {
                System.out.println("Invalid input format. Please enter the date in the format YYYY-MM-DD.");
            }
        } while (!check_date);

        return new Date(year, month, day);
    }

    /**
     * Choose time.
     *
     * @return the time
     */
    public Time chooseTime() {
        int hours = 0;
        int minutes = 0;
        boolean validTime = false;

        while (!validTime) {
            String line = Utils.readNonNullLineFromConsole("Enter the hour (A.M): XX-XX");
            String[] parts = line.split("-");

            if (parts.length == 2) {
                try {
                    hours = Integer.parseInt(parts[0]);
                    minutes = Integer.parseInt(parts[1]);

                    if (hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59) {
                        validTime = true;
                    } else {
                        System.out.println("Invalid time. Please enter hours between 0 and 23, and minutes between 0 and 59.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input format. Please enter the time in the format XX-XX.");
                }
            } else {
                System.out.println("Invalid input format. Please enter the time in the format XX-XX.");
            }
        }

        return new Time(hours, minutes);
    }

    /**
     * Send request boolean.
     *
     * @param visit the visit
     * @return the boolean
     */
    public boolean sendRequest(Visit visit) {
        if (ctrlVisit.saveVisit(visit)) {
            System.out.println("\nSuccess!!");
            return true;
        } else {
            System.out.println("\nInvalid!! it is not possible to schedule the visit, as you already have a visit at that timestamp.");
        }
        return false;
    }
}