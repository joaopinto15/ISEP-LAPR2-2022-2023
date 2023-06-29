package pt.ipp.isep.dei.esoft.project.ui.console.us1;

import pt.ipp.isep.dei.esoft.project.domain.controller.ListPropertyController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

/**
 * The type List properties ui.
 */
public class ListPropertiesUI implements Runnable {


    /**
     * The Ctrl.
     */
    private final ListPropertyController ctrl = new ListPropertyController();
    /**
     * The Properties.
     */
    private final List<Announcement> properties = ctrl.getAnnouncementList();
    /**
     * The Novo.
     */
    private List<Announcement> novo = properties;

    /**
     * Run.
     */
    @Override
    public void run() {
        try {
            novo=properties;
            list();//meter referencia
            filter();
            sort();
        }catch(Exception e){
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * List.
     */
    public void list() {
        try {
            System.out.println();
            ListPropertyController.displayProperties(properties);
        } catch (Exception e) {
            System.out.println("An error occurred while listing properties: " + e.getMessage());
        }
    }


    /**
     * Filter.
     */
    private void filter() {
        try {
            int rooms = -1;
            System.out.println();
            boolean confirm = Utils.confirm("you want to filter?(y/n)");
            if (confirm) {
                String filter = Utils.readNonNullLineFromConsole("Choose one filter \n1. Land\n2. House\n3. Apartment \n4. Sale\n5. Rent\n6. number of bathrooms\n7. number of bedrooms\n");
                System.out.println();
                while (confirm) {
                    if (filter.equals("6") || filter.equals("7")) {
                        rooms = Utils.readIntegerFromConsole("Insert the number of rooms");
                    }
                    novo = ctrl.filtro(filter, novo, rooms);
                    confirm = Utils.confirm("you want to filter one more time?(y/n)");
                    if (confirm) {
                        filter = Utils.readNonNullLineFromConsole("Choose one filter \n1. Land\n2. House\n3. Apartment \n4. Sale\n5. Rent\n6. number of bathrooms\n7. number of bedrooms\n");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while filtering properties: " + e.getMessage());
        }
    }


    /**
     * Sort.
     */
    private void sort() {
        try {
            System.out.println();
            boolean confirm = Utils.confirm("you want to sort  the properties?(y/n)");
            if (confirm) {
                String filter = Utils.readNonNullLineFromConsole("Choose sorting method \n1. Ascending city\n2. Descending city\n3. Price ascending \n4. Price descending\n");
                System.out.println();
                while (confirm) {
                    ctrl.sort(filter, novo);
                    confirm = Utils.confirm("you want to sort  the properties one more time?(y/n)");
                    if (confirm) {
                        filter = Utils.readNonNullLineFromConsole("Choose sorting method \n1. Ascending city\n2. Descending city\n3. Price ascending \n4. Price descending\n");
                    }//fazer um ciclo while que vai atualizando a property list com um bolean que recebe uma confirmação de continuação
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while sorting properties: " + e.getMessage());
        }
    }



}
