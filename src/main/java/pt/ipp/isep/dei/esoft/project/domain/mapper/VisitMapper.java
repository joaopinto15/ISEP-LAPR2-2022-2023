package pt.ipp.isep.dei.esoft.project.domain.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.VisitDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Visit mapper.
 */
public class VisitMapper implements Serializable {

    /**
     * To dto list.
     *
     * @param visits the visits
     * @return the list
     */
    public static List<VisitDTO> toDto(List<Visit> visits){
        List<VisitDTO> visitsDto = new ArrayList<>();

        for (Visit visit : visits){
            VisitDTO visitDto;

            visitDto = toDto(visit);
            visitsDto.add(visitDto);
        }
        return visitsDto;
    }

    /**
     * To mapper list.
     *
     * @param visitsDto the visits dto
     * @return the list
     */
    public static List<Visit> toMapper(List<VisitDTO> visitsDto){
        List<Visit> visits = new ArrayList<>();

        for (VisitDTO visitDto : visitsDto){
            Visit visit;

            visit = toModel(visitDto);
            visits.add(visit);
        }
        return visits;
    }


    /**
     * To dto visit dto.
     *
     * @param visit the visit
     * @return the visit dto
     */
    public static VisitDTO toDto(Visit visit){
        VisitDTO visitDto;

        visitDto = new VisitDTO(visit.getName(), visit.getEmail().toString(),visit.getPhoneNumber(), visit.getDate(),visit.getStartTime(),visit.getEndTime(), visit.getChosenAnnouncement());
        return visitDto;
    }

    /**
     * To model visit.
     *
     * @param visitDto the visit dto
     * @return the visit
     */
    public static Visit toModel(VisitDTO visitDto){
        Visit visit;

        visit = new Visit(visitDto.getName(), visitDto.getPhoneNumber(), visitDto.getDate(),visitDto.getStartTime(),visitDto.getEndTime(), visitDto.getEmail(), visitDto.getChosenAnnouncement());
        return visit;
    }
}
