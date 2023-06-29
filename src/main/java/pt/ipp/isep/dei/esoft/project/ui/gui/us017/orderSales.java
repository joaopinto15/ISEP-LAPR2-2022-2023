package pt.ipp.isep.dei.esoft.project.ui.gui.us017;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

/**
 * The type Order sales.
 */
public class orderSales {
    /**
     * The constant listDealFile.
     */
    private static final ListDealFile listDealFile;

    static {
        try {
            listDealFile = new ListDealFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The constant listDeals.
     */
    private static final ListDeals listDeals = new ListDeals();

    /**
     * Instantiates a new Order sales.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public orderSales() throws FileNotFoundException {
    }

    /**
     * Bubble sort list.
     *
     * @param file the file
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public List<Announcement> bubbleSort(String file) throws FileNotFoundException, ParseException {
        List<Announcement> area = listDealFile.transforminObject(file);
        area = listDeals.bubbleSort(area);
        return area;
    }

    /**
     * Bubble sort descending list.
     *
     * @param file the file
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public List<Announcement> bubbleSortDescending(String file) throws FileNotFoundException, ParseException {
        List<Announcement> area = listDealFile.transforminObject(file);
        area = listDeals.bubbleSortDescending(area);
        return area;
    }

    /**
     * Selection sort list.
     *
     * @param file the file
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public List<Announcement> selectionSort(String file) throws FileNotFoundException, ParseException {
        List<Announcement> area = listDealFile.transforminObject(file);
        area = listDeals.selectionSort(area);
        return area;
    }

    /**
     * Selection sort descending list.
     *
     * @param file the file
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public List<Announcement> selectionSortDescending(String file) throws FileNotFoundException, ParseException {
        List<Announcement> area = listDealFile.transforminObject(file);
        area = listDeals.selectionSortDescending(area);
        return area;
    }

    /**
     * Deafault list.
     *
     * @param file the file
     * @return the list
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public List<Announcement> deafault(String file) throws FileNotFoundException, ParseException {
        return listDealFile.transforminObject(file);
    }


}
