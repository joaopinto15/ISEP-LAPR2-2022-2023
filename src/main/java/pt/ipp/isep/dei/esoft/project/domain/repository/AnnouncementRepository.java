package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Announcement Repository
 */
public class AnnouncementRepository implements Serializable {

    /**
     * the list containing the not sold announcement registered in the system
     */
    private List<Announcement> announcementList = new ArrayList<>();


    /**
     * this function validates the announcement if not null
     *
     * @param announcement represents an announcement through request and commission
     * @return false if the instance announcement is null, if not will return !this.announcementList.contains(announcement)
     */
    private boolean validateAnnouncement(Announcement announcement) {
        if (announcement == null) {
            return false;
        } else {
            return !this.announcementList.contains(announcement);
        }
    }

    /**
     * this function will add an announcement to the announcement list
     *
     * @param announcement represents an announcement through request and commission
     * @return the call of a function that add to the announcement list
     */
    public boolean addAnnouncement(Announcement announcement) {
        return this.announcementList.add(announcement);
    }

    /**
     * Add announcement boolean.
     *
     * @param announcementList the announcement list
     * @return the boolean
     */
    private boolean addAnnouncement(List<Announcement> announcementList) {
        return this.announcementList.addAll(announcementList);
    }

    /**
     * this function will remove an announcement from the announcement list
     *
     * @param announcement represents an announcement through request and commission
     * @return the call of a function that remove from the announcement list
     */
    public boolean removeAnnouncement(Announcement announcement) {
        return this.announcementList.remove(announcement);
    }

    /**
     * Remove order from announcement boolean.
     *
     * @param announcement the announcement
     * @param order        the order
     * @return the boolean
     */
    public boolean removeOrderFromAnnouncement(Announcement announcement, Order order) {

        for (int i = 0; i < this.announcementList.size(); i++) {
            if (this.announcementList.get(i).equals(announcement)) {
                announcement.getOrders().remove(order);
                this.announcementList.set(i, announcement);
                return true;
            }
        }
        return false;
    }

    /**
     * this function saves the announcement if is validated
     *
     * @param announcement announcement represents an announcement through request and commission
     * @return save the instance of announcement in announcementList
     */
    public boolean saveAnnouncement(Announcement announcement) {
        if (validateAnnouncement(announcement)) {
            boolean add = addAnnouncement(announcement);
            serializeAnnouncementList();
            return add;
        } else {
            return false;
        }
    }

    /**
     * Save announcement boolean.
     *
     * @param announcementList the announcement list
     * @return the boolean
     */
    public boolean saveAnnouncement(List<Announcement> announcementList) {
        List<Announcement> validAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if (validateAnnouncement(announcement)) {
                validAnnouncements.add(announcement);
            }    
        }
        boolean add = addAnnouncement(validAnnouncements);
        if(add){
            serializeAnnouncementList();
        }
        return add;
    }

    /**
     * Gets not sold announcement list.
     *
     * @return the not sold announcement list
     */
    public List<Announcement> getNotSoldAnnouncementList() {
        List<Announcement> notSoldAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if(!announcement.isSold()){
                notSoldAnnouncements.add(announcement);
            }
        }
        return notSoldAnnouncements;
    }

    /**
     * Gets sold announcement list.
     *
     * @return the sold announcement list
     */
    public List<Announcement> getSoldAnnouncementList() {
        List<Announcement> soldAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if(announcement.isSold()){
                soldAnnouncements.add(announcement);
            }
        }
        return soldAnnouncements;
    }

    /**
     * Gets announcement list.
     *
     * @return the announcement list
     */
    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }


    /**
     * Serialize announcement list.
     */
    public void serializeAnnouncementList() {
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
            objectOut.writeObject(announcementList);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove all announcements.
     */
    public void removeAllAnnouncements() {
        this.announcementList.clear();
    }

    /**
     * Deserialize announcement list.
     */
    public void deserializeAnnouncementList() {
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            announcementList = (List<Announcement>) objectIn.readObject();
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
    private static final String fileName = "AnnouncementList.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder="serialized.files";
}