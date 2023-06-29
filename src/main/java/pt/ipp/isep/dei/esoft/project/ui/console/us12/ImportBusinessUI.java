package pt.ipp.isep.dei.esoft.project.ui.console.us12;

import pt.ipp.isep.dei.esoft.project.domain.controller.ImportBusinessController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

/**
 * The type Delete announcement ui.
 */
public class ImportBusinessUI implements Runnable {
    /**
     * The Controller.
     */
    final ImportBusinessController controller = new ImportBusinessController();


    /**
     * Run.
     */
    public void run() {
        String filePath;
        
        System.out.println("------------------------\n");
        System.out.println("|  Importing Business  |\n");
        System.out.println("------------------------\n");
        boolean isvalidFile;
        boolean isvalidContent = false;
        boolean isFileEmpty;
        int numberOfLines = 0;
        do{
            filePath = chooseFile();
            if(filePath.trim().equals("0")){
                return;
            }
            isvalidFile =verifyFile(filePath);
        if (isvalidFile) {
            try {
                isvalidContent = verifyContent(filePath);
                isFileEmpty = checkFileIsEmpty(filePath);
                if (isvalidContent && !isFileEmpty) {
                    numberOfLines = verifyNumberOfLines(filePath);
                    if (numberOfLines > 2) {
                        importData(readExcel(filePath, numberOfLines), numberOfLines);
                    }else{
                        System.out.println("Error -> This file is empty");
                    }
                }
            } catch (ParseException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            System.out.println("Error -> File not valid");
        }
    }while(!isvalidFile || !isvalidContent || !(numberOfLines > 2));

    }

    /**
     * Choose file string.
     *
     * @return the string
     */
    public String chooseFile(){
        return Utils.readLineFromConsole("Insert the file path (Press 0 to Go Back):");
    }

    /**
     * Verify file boolean.
     *
     * @param path the path
     * @return the boolean
     */
    private boolean verifyFile(String path){
        if(controller.checkFileFormat(path)){
            return true;
        }
        System.out.println("Error -> This excel table is not supported");
        return false;
    }

    /**
     * Verify content boolean.
     *
     * @param path the path
     * @return the boolean
     */
    private boolean verifyContent(String path) {
        return controller.checkFileContent(path);
    }

    /**
     * Verify number of lines int.
     *
     * @param path the path
     * @return the int
     * @throws FileNotFoundException the file not found exception
     */
    private int verifyNumberOfLines(String path) throws FileNotFoundException{
        int numberOfLines = controller.checkNumberOfLines(path);

        return numberOfLines;
    }

    /**
     * Read excel string [ ] [ ].
     *
     * @param path          the path
     * @param numberOfLines the number of lines
     * @return the string [ ] [ ]
     * @throws FileNotFoundException the file not found exception
     */
    private String[][] readExcel(String path, int numberOfLines) throws FileNotFoundException{
        return controller.readFile(path,numberOfLines);
    }

    /**
     * Import data boolean.
     *
     * @param data          the data
     * @param numberOfLines the number of lines
     * @return the boolean
     * @throws FileNotFoundException the file not found exception
     * @throws ParseException        the parse exception
     */
    private boolean importData(String[][] data, int numberOfLines) throws FileNotFoundException, ParseException{
        boolean answer  = controller.importData(data, numberOfLines);
        if(answer){
            System.out.println("\nData imported successfully");
        }else{
            System.out.println("Error -> Data not imported");
        }
        return answer ;
    }

    /**
     * Check file is empty boolean.
     *
     * @param filePath the file path
     * @return the boolean
     * @throws IOException the io exception
     */
    private boolean checkFileIsEmpty(String filePath) throws IOException {
            if(controller.checkFileIsEmpty(filePath)){
                System.out.println("Error -> This file is empty");
                return true;
            }
            return false;
    }

}
