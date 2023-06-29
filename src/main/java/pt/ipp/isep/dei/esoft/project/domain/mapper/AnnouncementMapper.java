package pt.ipp.isep.dei.esoft.project.domain.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.dto.OrderDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The AnnouncementMapper class provides static methods to convert between Announcement and AnnouncementDTO objects.
 */
public class AnnouncementMapper implements Serializable {

    /**
     * Converts a list of Announcement objects to a list of AnnouncementDTO objects.
     *
     * @param announcements the list of Announcement objects to convert
     * @return a list of AnnouncementDTO objects
     */
    public static List<AnnouncementDTO> toDto(List<Announcement> announcements) {
        List<AnnouncementDTO> announcementsDto = new ArrayList<>();

        for (Announcement announcement : announcements) {
            AnnouncementDTO announcementDto = toDto(announcement);
            announcementsDto.add(announcementDto);
        }

        return announcementsDto;
    }

    /**
     * Converts a list of AnnouncementDTO objects to a list of Announcement objects.
     *
     * @param announcementDTOs the list of AnnouncementDTO objects to convert
     * @return a list of Announcement objects
     */
    public static List<Announcement> toModel(List<AnnouncementDTO> announcementDTOs) {
        List<Announcement> announcements = new ArrayList<>();

        for (AnnouncementDTO announcementDto : announcementDTOs) {
            Announcement announcement = toModel(announcementDto);
            announcements.add(announcement);
        }

        return announcements;
    }

    /**
     * Converts an Announcement object to an AnnouncementDTO object.
     *
     * @param announcement the Announcement object to convert
     * @return an AnnouncementDTO object
     */
    public static AnnouncementDTO toDto(Announcement announcement) {
        List<OrderDTO> ordersDto = null;

        if (announcement.getOrders() != null) {
            ordersDto = OrderMapper.toDto(announcement.getOrders());
        }

        return new AnnouncementDTO(
                announcement.getRequest(),
                announcement.getCommission(),
                announcement.getDate(),
                announcement.isSold(),
                ordersDto
        );
    }

    /**
     * Converts an AnnouncementDTO object to an Announcement object.
     *
     * @param announcementDto the AnnouncementDTO object to convert
     * @return an Announcement object
     */
    public static Announcement toModel(AnnouncementDTO announcementDto) {
        List<Order> orders = null;

        if (announcementDto.getOrders() != null) {
            orders = OrderMapper.toModel(announcementDto.getOrders());
        }

        return new Announcement(
                announcementDto.getRequest(),
                announcementDto.getCommission(),
                announcementDto.getDate(),
                announcementDto.isSold(),
                orders
        );
    }

}