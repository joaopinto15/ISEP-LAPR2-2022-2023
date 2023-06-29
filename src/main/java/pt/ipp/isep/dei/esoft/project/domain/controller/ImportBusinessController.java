package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.repository.*;
import pt.ipp.isep.dei.esoft.project.domain.service.TransformFileInAnnouncement;

import java.io.*;
import java.text.ParseException;
import java.util.Properties;
import java.util.Scanner;

/**
 * The ImportBusinessController class handles the importing of business data
 * from a CSV file.
 */
public class ImportBusinessController {


    /**
     * The Announcement repository.
     */
    private final AnnouncementRepository announcementRepository;
    /**
     * The Agency repository.
     */
    private final AgencyRepository agencyRepository;


    /**
     * Constructor for ImportBusinessController class.
     * Initializes repositories and mapper.
     */
    public ImportBusinessController() {
        announcementRepository = Repositories.getInstance().getAnnouncementRepository();
        agencyRepository = Repositories.getInstance().getAgencyRepository();
    }

    /**
     * Checks if the file path has a CSV file extension.
     *
     * @param filePath The path of the file to check.
     * @return true if the file has a CSV extension, false otherwise.
     */
    public  boolean checkFileFormat(String filePath) {
        return checkFilePathEmpty(filePath) && checkFilePathIsCsv(filePath) && checkFileExists(filePath) ;
    }

    /**
     * Check file exists boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    private boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Check file path empty boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    private  boolean checkFilePathEmpty(String filePath) {
        return !filePath.trim().isEmpty();
    }

    /**
     * Check file path is csv boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    private  boolean checkFilePathIsCsv(String filePath) {
        return filePath.endsWith(".csv");
    }

    /**
     * Check file is empty boolean.
     *
     * @param filePath the file path
     * @return the boolean
     * @throws IOException the io exception
     */
    public  boolean checkFileIsEmpty(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));     
        if (br.readLine() == null) {
            return true;
        }else{
            return br.readLine() == null;
        }

    }

    /**
     * Checks if the file content matches the expected format.
     *
     * @param filePath The path of the file to check.
     * @return true if the file content matches the expected format, false         otherwise.
     */
    public  boolean checkFileContent(String filePath) {
        try (InputStream input = ImportBusinessController.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            String headerContent;
            
            if(file.exists() && file.isFile()){
                BufferedReader br = new BufferedReader(new FileReader(filePath));  
                if (br.readLine() == null) {
                    return false;
                }
                headerContent = sc.nextLine();
                
                if(headerContent.equals(prop.getProperty("Csv.Header")) && sc.nextLine().trim().length() > 0  ){
                    sc.close();
                    return true;
                }
            }
            
            sc.close();
            
            return false;
            
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Checks the number of lines in the file.
     *
     * @param filePath The path of the file to check.
     * @return The number of non-empty lines in the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public  int checkNumberOfLines(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        int count = 0;
        String line;
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.trim().length() > 0) {
                count++;
            }
        }
        sc.close();
        return count;
    }

    /**
     * Reads the file and returns the data as a two-dimensional array.
     *
     * @param filePath     The path of the file to read.
     * @param numberOfrows The number of rows to read from the file.
     * @return The data read from the file as a two-dimensional array.
     * @throws FileNotFoundException If the file is not found.
     */
    public  String[][] readFile(String filePath, int numberOfrows) throws FileNotFoundException {

        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        int count = 0;
        numberOfrows -= 1;
        sc.nextLine();
        String[][] data = new String[numberOfrows][30];

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] value = line.split(";");
            int numberOfcolumns = value.length;
            int j;
            for (j = 0; j < numberOfcolumns; j++) {
                data[count][j] = value[j];
            }
            count++;
        }
        sc.close();
        return data;
    }

    /**
     * Import data boolean.
     *
     * @param data          the data
     * @param numberOFLines the number of lines
     * @return the boolean
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    public boolean importData(String[][] data, int numberOFLines) throws FileNotFoundException, ParseException {
        TransformFileInAnnouncement announcementsList;
        announcementRepository.removeAllAnnouncements();

        announcementsList = TransformFileInAnnouncement.transforminObject(data,numberOFLines);
        agencyRepository.saveAgency(announcementsList.getAgencyList());
        return announcementRepository.saveAnnouncement(announcementsList.getAnnouncementsList());
    }
}