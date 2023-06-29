package pt.ipp.isep.dei.esoft.project.domain.service;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.ui.gui.us017.ListDealFile;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

/**
 * The type Property subset service.
 *
 * @param <T> the type parameter
 */
public class PropertySubsetService<T> {
    /**
     * The Properties.
     */
    List<Agency> properties;
    /**
     * The List deal file.
     */
    ListDealFile listDealFile=new ListDealFile();

    /**
     * Instantiates a new Property subset service.
     *
     * @param properties the properties
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public PropertySubsetService(String ficheiro) throws FileNotFoundException, ParseException {
        this.properties = listDealFile.transforminAgency(ficheiro);
    }

    /**
     * Gets best subset.
     *
     * @param n        the n
     * @param filePath
     * @return the best subset
     */
    public Map.Entry<ArrayList<Agency>, ArrayList<Agency>> getBestSubset(int n, String filePath) throws FileNotFoundException, ParseException {
        long start = System.nanoTime();
        int lowestDifference = Integer.MAX_VALUE;
        Map.Entry<ArrayList<Agency>, ArrayList<Agency>> bestSubset = null;

        for (int i = 1; i < Math.pow(2, n - 1); i++) { // starting with 1 (and ending in 2^n-1) to prevent empty subset
            ArrayList<Agency> firstSubset = new ArrayList<>();
            ArrayList<Agency> secondSubset = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0)
                    firstSubset.add(properties.get(j));
                else
                    secondSubset.add(properties.get(j));
            }

            Map.Entry<ArrayList<Agency>, ArrayList<Agency>> e = new AbstractMap.SimpleEntry<>(firstSubset, secondSubset);

            int difference = Math.abs(getgetSumOfProperties(firstSubset,filePath) - getgetSumOfProperties(secondSubset,filePath));

            if (difference < lowestDifference) {
                bestSubset = new AbstractMap.SimpleEntry<>(firstSubset, secondSubset);
                lowestDifference = difference;
            }

            long end = System.nanoTime();
            long elapsedTime = end - start;
            System.out.println("Start time: " + start + " nanoseconds");
            System.out.println("End time: " + end + " nanoseconds");
            System.out.println("Elapsed time: " + elapsedTime + " nanoseconds");

        }

        return bestSubset;
    }

    /**
     * Soma uma sublista toda
     * This function returns the sum of the properties of the agencies
     *
     * @param agencies the agencies
     * @return sum of properties
     */
    private int getgetSumOfProperties(List<Agency> agencies,String file) throws FileNotFoundException, ParseException {
        List<Announcement> announcements = listDealFile.transforminObject(file);
        int soma = 0;
        for (Agency agency :agencies) {
            for (Announcement announcement : announcements) {
                if (agency.getId().equalsIgnoreCase(announcement.getRequest().getEmployee().getAgencyId())) {
                    soma++;
                }
            }

        }
        return soma;
    }

    /**
     * Gets by id.
     *
     * @param agencyId the agency id
     * @return the by id
     */
    public int getpropertiesByID(String agencyId,String file) throws FileNotFoundException, ParseException {
        List<Announcement> announcements = listDealFile.transforminObject(file);
        int soma = 0;
        for (Announcement announcement : announcements) {
            if (agencyId.equalsIgnoreCase(announcement.getRequest().getEmployee().getAgencyId())) {
                soma++;
            }
        }

        return soma;
    }


}