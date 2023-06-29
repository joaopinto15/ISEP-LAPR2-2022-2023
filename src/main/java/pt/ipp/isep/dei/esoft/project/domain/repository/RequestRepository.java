package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Request Repository
 */
public class RequestRepository implements Serializable {

    /**
     * the list containing the request registered in the system
     */
    private List<Request> requestList = new ArrayList<>();

    /**
     * this function will add n request to the request list
     *
     * @param request represents n request through employee, person, property and isPending
     * @return the call of a function that add to the request list
     */
    public boolean addRequest(Request request){

        return this.requestList.add(request);
    }

    /**
     * this function validates the request if not null
     *
     * @param request represents n request through employee, person, property and isPending
     * @return false if the instance request is null, if not will return !this.requestList.contains(request)
     */
    private boolean validateRequest(Request request){
        if(request == null){
            return false;
        } else {
            return !this.requestList.contains(request);
        }
    }

    /**
     * this function creates an instance of request
     * @param employee  holds the employee of the request
     * @param person    holds the person of the request
     * @param property  holds the property of the request
     * @param isPending holds the isPending of the request
     * @return a new instance of announcement
     */
//    public Request createRequest(Employee employee, Person person, Property property, boolean isPending){
//        return new Request(employee, person, property, isPending);
//    }

    /**
     * this function saves the request if is validated
     *
     * @param request represents n request through employee, person, property and isPending
     * @return save the instance of announcement in announcementList
     */
    public boolean saveRequest(Request request){
        if(!validateRequest(request)){
            return false;
        } else {
            boolean add=addRequest(request);
            serializeRequest();
            return add;

        }
    }


    /**
     * this function returns the announcement list
     *
     * @return the announcement list
     */
    public List<Request> getRequestList() {

        return requestList;
    }

    /**
     * this method returns a list of requests by a specific Agent
     *
     * @param employee holds the employee responsible for the request
     * @return a list of requests by a specific Agent
     */
    public List<Request> getRequestList(Employee employee){

        ArrayList<Request> rList = new ArrayList<>();

        for (Request request : requestList){
            if(request.getEmployee() == employee){
               rList.add(request);
            }
        }
        return rList;
    }

    /**
     * This function deletes the request from the request list
     *
     * @param request represents a request through employee, person, property and isPending
     * @return the call of a function that remove from the request List
     */
    public boolean deleteRequest(Request request){
        return this.requestList.remove(request);
    }

    /**
     * Serialize request.
     */
    private void serializeRequest(){
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
            objectOut.writeObject(requestList);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize request.
     */
    public void deserializeRequest(){
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            requestList = (List<Request>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

        } catch (FileNotFoundException e) {
            // Lidar com a exceção: exibir mensagem de erro ou realizar ação alternativa
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The constant fileName.
     */
    private static final String fileName="request.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder="serialized.files";

}