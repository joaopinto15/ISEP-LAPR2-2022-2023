package pt.ipp.isep.dei.esoft.project.ui.console.us18;

import pt.ipp.isep.dei.esoft.project.ui.gui.us18.AnalyseDealController;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 * The type Analy deal ui.
 */
public class AnalyDealUI implements Runnable {
    /**
     * The Analyse deal controller.
     */
    AnalyseDealController analyseDealController=new AnalyseDealController();

    /**
     * Instantiates a new Analy deal ui.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public AnalyDealUI() throws FileNotFoundException {
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        try {
            AnalyseDealController.sa();
        } catch (FileNotFoundException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
