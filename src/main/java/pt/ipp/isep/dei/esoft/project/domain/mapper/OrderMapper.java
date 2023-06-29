package pt.ipp.isep.dei.esoft.project.domain.mapper;

import pt.ipp.isep.dei.esoft.project.domain.dto.OrderDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The OrderMapper class provides static methods to convert between Order and OrderDTO objects.
 */
public class OrderMapper implements Serializable {

    /**
     * Converts a list of Order objects to a list of OrderDTO objects.
     *
     * @param orders the list of Order objects to convert
     * @return a list of OrderDTO objects
     */
    public static List<OrderDTO> toDto(List<Order> orders) {
        List<OrderDTO> ordersDto = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDto = toDto(order);
            ordersDto.add(orderDto);
        }

        return ordersDto;
    }

    /**
     * Converts a list of OrderDTO objects to a list of Order objects.
     *
     * @param orderDTOs the list of OrderDTO objects to convert
     * @return a list of Order objects
     */
    public static List<Order> toModel(List<OrderDTO> orderDTOs) {
        List<Order> orders = new ArrayList<>();

        for (OrderDTO orderDto : orderDTOs) {
            Order order = toModel(orderDto);
            orders.add(order);
        }

        return orders;
    }

    /**
     * Converts an Order object to an OrderDTO object.
     *
     * @param order the Order object to convert
     * @return an OrderDTO object
     */
    public static OrderDTO toDto(Order order) {
        return new OrderDTO(order.getPerson(), order.getPrice(), order.getDate(), order.isAccepted());
    }

    /**
     * Converts an OrderDTO object to an Order object.
     *
     * @param orderDto the OrderDTO object to convert
     * @return an Order object
     */
    public static Order toModel(OrderDTO orderDto) {
        return new Order(orderDto.getPerson(), orderDto.getPrice(), orderDto.getDate(), orderDto.isAccepted());
    }
}