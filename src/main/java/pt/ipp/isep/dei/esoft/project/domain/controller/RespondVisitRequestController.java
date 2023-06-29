package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.Message;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import pt.ipp.isep.dei.esoft.project.domain.service.DomainCheck;

/**
 * The type Respond visit request controller.
 */
public class RespondVisitRequestController {


    /**
     * Notify client boolean.
     *
     * @param visit      the visit
     * @param text       the text
     * @param isAccepted the is accepted
     * @return the boolean
     */
    public boolean notifyClient(Visit visit, String text, boolean isAccepted) {
        if(isAccepted && DomainCheck.checkDomain()){
            return sendAcceptanceEmail(visit, text);
        } else {
            return sendRejectionEmail(visit, text);
        }
    }

    /**
     * Send acceptance email boolean.
     *
     * @param visit the visit
     * @param text  the text
     * @return the boolean
     */
    public boolean sendAcceptanceEmail(Visit visit, String text) {
        String subject = "Dear " + visit.getChosenAnnouncement().getRequest().getUser().getName() +
                "Your visit has been scheduled!";
        String msg = "Dear " +
                visit.getChosenAnnouncement().getRequest().getUser().getName() +
                "Your visit to the following property has been rejected.\n\n" +
                visit.getChosenAnnouncement().getRequest().getProperty() + ((!text.isEmpty()) ? "\n\n Added notes: " + text : "");
        Message message = new Message(subject,visit.getChosenAnnouncement().getRequest().getEmployee(),
                visit.getEmail(), text);
        return message.send();
    }

    /**
     * Send rejection email boolean.
     *
     * @param visit the visit
     * @param text  the text
     * @return the boolean
     */
    public boolean sendRejectionEmail(Visit visit, String text) {
        String subject = "Dear " + visit.getChosenAnnouncement().getRequest().getUser().getName() +
                "Your visit has been rejected.";
        String msg = "Dear " +
                visit.getChosenAnnouncement().getRequest().getUser().getName() +
                "Your visit to the following property has been rejected.\n\n" +
                visit.getChosenAnnouncement().getRequest().getProperty() + ((!text.isEmpty()) ? "\n\n Reason: " + text : "");
        Message message = new Message(subject,visit.getChosenAnnouncement().getRequest().getEmployee(),
                visit.getEmail(), msg);
        return message.send();

    }

    /**
     * Delete visit boolean.
     *
     * @param visit the visit
     * @return the boolean
     */
    public boolean deleteVisit(Visit visit){
        return Repositories.getInstance().getVisitRepository().deleteVisit(visit);
    }
    
}
