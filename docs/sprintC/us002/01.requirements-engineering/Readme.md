# US 002 - Publish announcement

## 1. Requirements Engineering


### 1.1. User Story Description

As an agent, I can publish any sale announcement on the system, for example received through a phone call.

### 1.2. Customer Specifications and Clarifications


**From the specifications document:**

>	The agent receives the information about the property from the concerned owner by meeting with him or receives it through the company's application for the same purpose

>	Upon receiving the order, the agent analyzes and sets the commission and publishes the offer in the system.



**From the client clarifications:**

> **Question:** Is the owner able to edit an already active listing of a property? If so, do the edits need to be accepted by the agent before being published?
>
> **Answer:** No.
>
> [source](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=22582)

> **Question:** In one of the previous questions you have stated that for now the only way that a agent can receive the request to publish an announcement is through a phone call, however in the primary date necessary to create an announcement it's said that it's necessary to upload at least one photo of the property. Taking that into consideration, the announcement can't be fully made by only communicating with the owner through a phone call, how is it possible for an agent to publish an announcement without all the necessary date?
>
> **Answer:** The owner can send the photograph by e-mail or any other means. The owner can even deliver the photograph to the store.
>
> [source](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=22174)

> **Question:** The act of publishing a sale announcement means that the agent is publishing a new property for sale in the system (receiving the information of the seller and publishing the new property for sale) or is it the buyer giving positive feedback to the agent with intent to buy the property(actually closing a sale, buying a property and de-listing said property)?
>
> **Answer:** In US1 we get "As an agent, I can publish any sale announcement on the system, for example received through a phone call". Additional information related with this question is also avaliable in the project description. Asking the customer something that is clear or has already been clarified is unprofessional.
>
> [source](https://moodle.isep.ipp.pt/mod/forum/discuss.php?d=21974#p27745)

### 1.3. Acceptance Criteria


* **AC1:** USD (Dollar $) is the business currency.
* **AC2:** Area (m2)
* **AC3:** The commission can be a fixed amount or a percentage
* **AC4:** It isn’t possible to submit multiple listing for the same property and type of listing.
* **AC5:** If it is an apartment/house, add the number of bedrooms, number of bathrooms, number of parking spaces, and available equipment such as central heating and/or air conditioning.
* **AC6:** If the property is a house, the existence of a basement, a habitable attic, and the sun exposure should also be recorded.


### 1.4. Found out Dependencies

* There is a strong independence with us003, being fundamental to obtain the necessary permissions to use us002


### 1.5 Input and Output Data

**Input Data:**

* Typed date:
	* proprety type,
	* property characteristics,
	* business type,
	* commission

* Selected date:
	* Country

**Output Data:**

* Summary of date entered and confirm Bhutan to confirm
* Operation sucessfully completed message

### 1.6. System Sequence Diagram (SSD)

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us002-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks

* When an agent publishes a property, the information must be sent in a single sub-mission (it cannot be saved to be finished later).