package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.domain.repository.*;

import java.util.Comparator;
import java.util.List;

/**
 * The type Post announcement controller.
 */
public class PostAnnouncementController {


    /**
     * The Repositories.
     */
    private final Repositories repositories;
    /**
     * The Announcement repository.
     */
    private AnnouncementRepository announcementRepository;
    /**
     * The Request repository.
     */
    private RequestRepository requestRepository;


    /**
     * Instantiates a new Post announcement controller.
     */
    public PostAnnouncementController() {
        repositories = Repositories.getInstance();
        announcementRepository = repositories.getAnnouncementRepository();
        requestRepository = repositories.getRequestRepository();
    }

    /**
     * method to get the announcements repository
     *
     * @return the announcement repository
     */
    public AnnouncementRepository getAnnouncementRepository(){
        if(announcementRepository == null){
            Repositories repositories = Repositories.getInstance();
            announcementRepository = repositories.getAnnouncementRepository();
        }
        return announcementRepository;
    }

    /**
     * Get requests list.
     *
     * @return the list
     */
    public List<Request> getRequests(){
        this.requestRepository = repositories.getRequestRepository();
        return this.requestRepository.getRequestList();
    }

    /**
     * Method to return the request list by date
     *
     * @param employee the employee
     * @return the request list sorted by date
     */
    public List<Request> getRequestByDate(Employee employee){
            List<Request> requestByDate = requestRepository.getRequestList(employee);
            requestByDate.sort(new Comparator<Request>() {
                @Override
                public int compare(Request request, Request otherRequest) {
                    return request.getDate().compareTo(otherRequest.getDate());
                }
            });
            return requestByDate;
    }

    /**
     * Submit announcement.
     *
     * @param announcement the announcement
     */
    public void submitAnnouncement(Announcement announcement){
        announcementRepository.addAnnouncement(announcement);
    }

    /**
     * Delete request.
     *
     * @param request the request
     */
    public void deleteRequest(Request request) {
        this.requestRepository = repositories.getRequestRepository();
       requestRepository.deleteRequest(request);

    }
}
