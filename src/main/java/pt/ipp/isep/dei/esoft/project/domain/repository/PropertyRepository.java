package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Property repository.
 */
public class PropertyRepository implements Serializable {

    /**
     * The Properties list.
     */
    private List<Property> propertiesList=new ArrayList<>();

    /**
     * The constant fileName.
     */
    private static final String fileName="PropertyList.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder="serialized.files";

    /**
     * Validate property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    private boolean validateProperty(Land property){
        if(property == null){
            return false;
        } else {
            return !this.propertiesList.contains(property);
        }
    }

    /**
     * Validate property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    private boolean validateProperty(Apartment property){
        if(property == null){
            return false;
        } else {
            return !this.propertiesList.contains(property);
        }
    }

    /**
     * Validate property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    private boolean validateProperty(House property){
        if(property == null){
            return false;
        } else {
            return !this.propertiesList.contains(property);
        }
    }

    /**
     * Add property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    private boolean addProperty(Property property){
        return this.propertiesList.add(property);
    }

    /**
     * Save property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    public boolean saveProperty(Land property) {
        if (!validateProperty(property)) {
            return false;
        } else {
            boolean add=addProperty(property);
            serializeProperty();
            return add;
        }
    }

    /**
     * Save property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    public boolean saveProperty(Apartment property) {
        if (!validateProperty(property)) {
            return false;
        } else {
            boolean add=addProperty(property);
            serializeProperty();
            return add;
        }
    }

    /**
     * Save property boolean.
     *
     * @param property the property
     * @return the boolean
     */
    public boolean saveProperty(House property) {
        if (!validateProperty(property)) {
            return false;
        } else {
            boolean add=addProperty(property);
            serializeProperty();
            return add;
        }
    }

    /**
     * Gets all property list.
     *
     * @return the all property list
     */
//WARNING: This will not retrieve all data there is on a given property, only common data across property classes
    public List<Property> getAllPropertyList() {
        return propertiesList;
    }

    /**
     * Serialize property.
     */
    private void serializeProperty(){
        try {
            File folder = new File(storageFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                System.out.println("an error occurred when creating the folder -> " + storageFolder + " <-");
                return;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(storageFolder + File.separator + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(propertiesList);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize property.
     */
    public void deserializeProperty(){
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            propertiesList = (List<Property>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            // Lidar com a exceção: exibir mensagem de erro ou realizar ação alternativa
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

