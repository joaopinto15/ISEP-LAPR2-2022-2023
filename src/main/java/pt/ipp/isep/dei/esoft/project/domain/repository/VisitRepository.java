package pt.ipp.isep.dei.esoft.project.domain.repository;


import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Time;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Visit repository.
 */
public class VisitRepository implements Serializable {
    /**
     * The constant fileName.
     */
    private static final String fileName = "FileVisit.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder = "serialized.files";
    /**
     * The Visit list.
     */
    private List<Visit> visitList = new ArrayList<>();


//    public VisitRepository() throws IOException, ClassNotFoundException{
//        ObjectInputStream objectInput=new ObjectInputStream(new FileInputStream("FileVisit"));
//        this.visitList=(List<Visit>) objectInput.readObject();
//        objectInput.close();
//    }

    /**
     * Save visit boolean.
     *
     * @param visit the visit
     * @return the boolean
     */
    public boolean saveVisit(Visit visit) {
        if (!validateVisit(visit) || !confirmVisit(visit)) {
            return false;
        } else {
            boolean add = addVisit(visit);
            serializeVisit();
            return add;
        }
    }

    /**
     * Validate visit .
     *
     * @param visit the visit
     * @return the boolean
     */
    public boolean validateVisit(Visit visit) {
        if (visit == null) {
            return false;
        } else {
            return !this.visitList.contains(visit);
        }
    }

    /**
     * Add visit boolean.
     *
     * @param visit the visit
     * @return the boolean
     */
    private boolean addVisit(Visit visit) {
        return this.visitList.add(visit);
    }

    /**
     * Gets visit list.
     *
     * @return the visit list
     */
    public List<Visit> getVisitList() {
        return visitList;
    }

    /**
     * Confirm visit boolean.
     *
     * @param visit the visit
     * @return the boolean
     */
    public boolean confirmVisit(Visit visit) {
        Address address = visit.getChosenAnnouncement().getRequest().getProperty().getAddress();//1
        Date date = visit.getDate();//2
        Time start = visit.getStartTime();
        Time end = visit.getEndTime();
        Email email = visit.getEmail();//1
        int startHour = visit.getStartTime().getHours();//3
        int endHour = visit.getEndTime().getHours();//3
        int startMinutes = visit.getStartTime().getMinutes();//4
        int endMinutes = visit.getEndTime().getMinutes();//4
        for (int i = 0; i < visitList.size(); i++) {
            if (visitList.get(i).getEmail().equals(email)) {
                if (address == visitList.get(i).getChosenAnnouncement().getRequest().getProperty().getAddress()) {
                    if (date.toString().equals(visitList.get(i).getDate().toString())) {
                        if (startHour <= visitList.get(i).getStartTime().getHours() && endHour >= visitList.get(i).getEndTime().getHours()) {
                            return false;
                        }
                        if (startHour >= visitList.get(i).getStartTime().getHours() && startHour <= visitList.get(i).getEndTime().getHours() || endHour >= visitList.get(i).getStartTime().getHours() && endHour <= visitList.get(i).getEndTime().getHours()) {
                            if (startMinutes >= visitList.get(i).getStartTime().getMinutes() && startMinutes <= visitList.get(i).getEndTime().getMinutes() || endMinutes >= visitList.get(i).getStartTime().getMinutes() && endMinutes <= visitList.get(i).getEndTime().getMinutes()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Method to delete a visit from the visit list
     *
     * @param visit the visit
     * @return the call to remove a visit from the visit list
     */
    public boolean deleteVisit(Visit visit) {
        return this.visitList.remove(visit);
    }

    /**
     * Method to get a List of visits by a specific agent
     *
     * @param employee the agent
     * @return the list of visits by a specific agent
     */
//For some reason, I can't manage to match up employee objects, so I'm using the email address instead
    public List<Visit> getVisitByAgentList(Employee employee) {
        //print all elements of visitList
        ArrayList<Visit> vList = new ArrayList<>();
        for (Visit visit : visitList) {
            if (visit.getChosenAnnouncement().getRequest().getEmployee().getEmailAddress().equals(employee.getEmailAddress())) {
                vList.add(visit);
            }
        }
        return vList;
    }

    /**
     * Serialize visit.
     */
    public void serializeVisit() {
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
            objectOut.writeObject(visitList);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Desserialize visit.
     */
    public void desserializeVisit() {
        try {
            File file = new File(storageFolder, fileName);
            if (file.exists()) {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                visitList = (List<Visit>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
            } else {

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
