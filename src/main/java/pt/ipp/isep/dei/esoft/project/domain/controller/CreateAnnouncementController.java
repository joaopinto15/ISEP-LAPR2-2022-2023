package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Message;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.List;


/**
 * The CreateAnnouncementController class is responsible for handling the creation and publication of announcements.
 */
public class CreateAnnouncementController {

    /**
     * The Announcement repository.
     */
    private final AnnouncementRepository announcementRepository;

    /**
     * Constructs a CreateAnnouncementController object.
     * Retrieves the AnnouncementRepository instance from Repositories and assigns it to the announcementRepository variable.
     */
    public CreateAnnouncementController() {
        Repositories repositories = Repositories.getInstance();
        announcementRepository = repositories.getAnnouncementRepository();
    }

    /**
     * Publishes an announcement by converting the provided AnnouncementDTO to an Announcement object
     * and saving it in the announcement repository.
     *
     * @param announcementDto the AnnouncementDTO to be published
     * @return true if the announcement is successfully saved, false otherwise
     */
    public boolean publishAnnouncement(AnnouncementDTO announcementDto) {
        Announcement announcement = AnnouncementMapper.toModel(announcementDto);
        return announcementRepository.saveAnnouncement(announcement);
    }

    /**
     * Retrieves the list of unsold announcements from the announcement repository and converts them
     * to a list of AnnouncementDTO objects.
     *
     * @return a list of AnnouncementDTO objects representing the unsold announcements
     */
    public List<AnnouncementDTO> getAnnouncementList() {
        return AnnouncementMapper.toDto(announcementRepository.getNotSoldAnnouncementList());
    }

    /**
     * Notifies the client about the publication of an announcement by creating and saving a message.
     * Converts the provided AnnouncementDTO to an Announcement object and retrieves relevant information
     * to compose the message.
     *
     * @param announcementDto the AnnouncementDTO for which the client should be notified
     */
    public void notifyClient(AnnouncementDTO announcementDto) {
        Announcement announcement = AnnouncementMapper.toModel(announcementDto);

        String messageBody = "Dear " +
                announcement.getRequest().getUser().getName() + ",\n\n" +
                "Your announcement was published with success in the system.\n\n" +
                announcement.toString(false);
        Message message = new Message(announcement.getRequest().getEmployee(),
                announcement.getRequest().getUser().getPhoneNumber(), messageBody);
        message.send();
    }
}