package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;

import java.util.List;

/**
 * The interface Filtro.
 */
public interface Filtro {
    /**
     * Type filter list.
     *
     * @param properties the properties
     * @return the list
     */
    List<Announcement> typeFilter(List<Announcement> properties);

    /**
     * Rooms filter list.
     *
     * @param properties the properties
     * @param number     the number
     * @return the list
     */
    List<Announcement> roomsFilter(List<Announcement> properties, int number);

}
//os filtos estrao no repositorio das property