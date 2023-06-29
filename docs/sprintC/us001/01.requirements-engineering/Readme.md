# US 001 - List properties 

## 1. Requirements Engineering


### 1.1. User Story Description


As a client, I want to leave a message to the agent to schedule a visit to a property of my interest.



### 1.2. Customer Specifications and Clarifications 


**From the specifications document:**



**From the client clarifications:**

### 1.3. Acceptance Criteria


* **AC1:** A list of available properties must be shown, sorted from the most recent entries to the oldest.
* **AC2:** The message must also include the client's name, phone number, preferred date and time slot (from x hour to y hour) for the property visit.  
* **AC3:** A client may post multiple visit requests, but only if those do not overlap each other.
* **AC4:** The client must receive a success message when the request is valid and registered in the system.


### 1.4. Found out Dependencies

* There is a dependece with us7.

### 1.5 Input and Output Data


**Input Data:**

* Typed date:
	* price 
	* number of bedrooms 
	* type of property
  
* Selected date:
	* price low to high
	* price high to low
    * number of bedrooms(1,2,3...)
    * buy
    * rent

**Output Data:**

* List of houses by the sorting critiria
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)
![System Sequence Diagram](svg/us009-SSD.svg)
### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.