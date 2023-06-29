package pt.ipp.isep.dei.esoft.project.ui.gui;

import pt.ipp.isep.dei.esoft.project.domain.model.Visit;

/**
 * The type Context.
 */
public class Context {
    /**
     * The constant instance.
     */
    private final static Context instance = new Context();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        return instance;
    }

    /**
     * The Visit.
     */
    private Visit visit;

    /**
     * Sets visit.
     *
     * @param visit the visit
     */
    public void setVisit(Visit visit)
    {
        System.out.println("Visit set");
        this.visit=visit;
    }

    /**
     * Gets visit.
     *
     * @return the visit
     */
    public Visit getVisit() {
        System.out.println("Visit get");
        return visit;
    }

}