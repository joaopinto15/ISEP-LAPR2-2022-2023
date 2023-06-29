package pt.ipp.isep.dei.esoft.project.ui.gui.us017;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;

import java.util.List;

/**
 * The type List deals.
 */
public class ListDeals {

    /**
     * Bubble sort.
     *
     * @param area the area
     * @return list
     */
    public List<Announcement> bubbleSort(List<Announcement> area) {
        long start=System.nanoTime();
        int n = area.size();
        for (int i = 0; i < n - 1; i=i+1) {
            for (int j = 0; j < n - i - 1; j=j+1) {
                if (area.get(j).getRequest().getProperty().getArea() > area.get(j + 1).getRequest().getProperty().getArea()) {
                    Announcement aux = area.get(j);//    int temp = array[j];
                    area.set(j, area.get(j + 1));//      array[j] = array[j + 1];
                    area.set(j + 1, aux);//              array[j + 1] = temp;
                }
            }
        }
        long end=System.nanoTime();
        System.out.println("Tempo Total de execução bubble sort: "+ (end-start));
        System.out.println("Tempo inicial: "+ start);
        System.out.println("Tempo final:   "+ end);
        return area;
    }

    /**
     * Bubble sort descending.
     *
     * @param area the area
     * @return list
     */
    public List<Announcement> bubbleSortDescending(List<Announcement> area) {//fazer com array list?
        long start=System.nanoTime();
        int n = area.size();
        for (int i = 0; i < n - 1; i=i+1) {
            for (int j = 0; j < n - i - 1; j=j+1) {
                if (area.get(j).getRequest().getProperty().getArea() < area.get(j + 1).getRequest().getProperty().getArea()) {
                    Announcement aux = area.get(j);//    int temp = array[j];
                    area.set(j, area.get(j + 1));//      array[j] = array[j + 1];
                    area.set(j + 1, aux);//              array[j + 1] = temp;
                }
            }
        }
        long end=System.nanoTime();
        System.out.println("Tempo Total de execução bubbleSortDescending: "+ (end-start));
        System.out.println("Tempo inicial: "+ start);
        System.out.println("Tempo final: "+ end);
        return area;
    }

    /**
     * Selection sort descending.
     *
     * @param areaList the area list
     * @return list
     */
    public List<Announcement> selectionSortDescending(List<Announcement> areaList) {
        long start=System.nanoTime();
        int n = areaList.size();
        for (int i = 0; i < n - 1; i=i+1) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j=j+1) {
                if (areaList.get(j).getRequest().getProperty().getArea() > areaList.get(maxIndex).getRequest().getProperty().getArea()) {
                    maxIndex = j;
                }
            }
            Announcement temp = areaList.get(i);
            areaList.set(i, areaList.get(maxIndex));
            areaList.set(maxIndex, temp);
        }
        long end=System.nanoTime();
        System.out.println("Tempo Total de execução selectionSortDescending: "+ (end-start));
        System.out.println("Tempo inicial: "+ start);
        System.out.println("Tempo final: "+ end);
        return areaList;
    }

    /**
     * Selection sort.
     *
     * @param areaList the area list
     * @return list
     */
    public List<Announcement> selectionSort(List<Announcement> areaList) {
        long start=System.nanoTime();
        int n = areaList.size();
        for (int i = 0; i < n - 1; i=i+1) {
            int maxIndex = i;
            for (int j = i + 1; j < n; j=j+1) {
                if (areaList.get(j).getRequest().getProperty().getArea() < areaList.get(maxIndex).getRequest().getProperty().getArea()) {
                    maxIndex = j;
                }
            }
            Announcement temp = areaList.get(i);
            areaList.set(i, areaList.get(maxIndex));
            areaList.set(maxIndex, temp);
        }
        long end=System.nanoTime();
        System.out.println("Tempo Total de execução selectionSort: "+ (end-start));
        System.out.println("Tempo inicial: "+ start);
        System.out.println("Tempo final: "+ end);
        return areaList;
    }

}
