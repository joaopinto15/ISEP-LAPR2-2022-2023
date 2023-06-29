package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * The MenuItem class represents a menu item in the user interface.
 * It encapsulates a description and a corresponding UI action to execute.
 */
public class MenuItem {
    /**
     * The Description.
     */
    private final String description;
    /**
     * The Ui.
     */
    private final Runnable ui;

    /**
     * Constructs a new MenuItem object with the specified description and UI action.
     *
     * @param description The description of the menu item.
     * @param ui          The UI action to execute when the menu item is selected.
     * @throws IllegalArgumentException if the description is null or empty, or if the UI action is null.
     */
    public MenuItem(String description, Runnable ui) {
        if (StringUtils.isBlank(description)) {
            throw new IllegalArgumentException("MenuItem description cannot be null or empty.");
        }
        if (Objects.isNull(ui)) {
            throw new IllegalArgumentException("MenuItem does not support a null UI.");
        }

        this.description = description;
        this.ui = ui;
    }

    /**
     * Executes the UI action associated with the menu item.
     */
    public void run() {
        this.ui.run();
    }

    /**
     * Checks if the menu item has the specified description.
     *
     * @param description The description to compare.
     * @return true if the menu item has the specified description, false otherwise.
     */
    public boolean hasDescription(String description) {
        return this.description.equals(description);
    }

    /**
     * Returns the string representation of the menu item.
     *
     * @return The description of the menu item.
     */
    public String toString() {
        return this.description;
    }
}