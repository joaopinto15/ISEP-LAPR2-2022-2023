package pt.ipp.isep.dei.esoft.project.domain.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.RequestDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Request mapper.
 */
public class RequestMapper implements Serializable {

    /**
     * To dto list.
     *
     * @param requests the requests
     * @return the list
     */
    public static List<RequestDTO> toDto(List<Request> requests){
        List<RequestDTO> requestsDto = new ArrayList<>();

        for (Request request : requests){
            RequestDTO requestDto;

            requestDto = toDto(request);
            requestsDto.add(requestDto);
        }
        return requestsDto;
    }

    /**
     * To model list.
     *
     * @param requestsDto the requests dto
     * @return the list
     */
    public static List<Request> toModel(List<RequestDTO> requestsDto){
        List<Request> requests = new ArrayList<>();

        for(RequestDTO requestDto : requestsDto){
            Request request;

            request = toModel(requestDto);
            requests.add(request);
        }
        return requests;
    }

    /**
     * To dto request dto.
     *
     * @param request the request
     * @return the request dto
     */
    public static RequestDTO toDto(Request request){
        RequestDTO requestDto;

        requestDto = new RequestDTO(request.getEmployee(),request.getUser(),request.getProperty(),request.isPending(),request.getDate());
        return requestDto;
    }

    /**
     * To model request.
     *
     * @param requestDto the request dto
     * @return the request
     */
    public static Request toModel(RequestDTO requestDto){
        Request request;

        request = new Request(requestDto.getEmployee(), requestDto.getPerson(), requestDto.getProperty(), requestDto.isPending(),requestDto.getDate());
        return request;
    }
}
