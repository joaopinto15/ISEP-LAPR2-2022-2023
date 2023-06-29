package pt.ipp.isep.dei.esoft.project.domain.model.property;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Store.
 */
public class Store{
    /**
     * The Name.
     */
    private final String name;
    /**
     * The Email.
     */
    private final String email;
    /**
     * The Properties.
     */
    private final List<Property> properties;

    /**
     * Instantiates a new Store.
     *
     * @param name  the name
     * @param email the email
     */
    public Store(String name, String email) {
        this.name = name;
        this.email = email;
        this.properties = new ArrayList<>();
    }

    /**
     * Add property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    public boolean addProperty(Property property) {
        if (this.properties.contains(property))
            return false;
        return this.properties.add(property);
    }

    /**
     * Gets number of properties.
     *
     * @return the number of properties
     */
    public int getNumberOfProperties() {
        return this.properties.size();
    }
}
