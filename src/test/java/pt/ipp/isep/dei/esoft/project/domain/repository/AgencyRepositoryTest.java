package pt.ipp.isep.dei.esoft.project.domain.repository;

import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;

import static org.junit.jupiter.api.Assertions.*;

class AgencyRepositoryTest {
    @Test
    public void testGetAgencyList() {
        AgencyRepository agencyRepository = new AgencyRepository();
        assertNotNull(agencyRepository.getAgencyList());
        assertTrue(agencyRepository.getAgencyList().isEmpty());
    }
    @Test
    public void testAddAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        String email = "agency1@example.com";
        Address address = new Address("123 Main St", "New York", "NY", "10001", "12345");
        Agency agency = new Agency("A001", address, "Agency 1", "123-456-7890", email);
        assertTrue(agencyRepository.addAgency(agency));
        assertFalse(agencyRepository.getAgencyList().isEmpty());
        assertTrue(agencyRepository.getAgencyList().contains(agency));
    }
    @Test
    public void testAddNullAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        assertFalse(agencyRepository.addAgency(null));
        assertTrue(agencyRepository.getAgencyList().isEmpty());
    }

    @Test
    public void testAddDuplicateAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        String email = "agency1@example.com";
        Address address = new Address("123 Main St", "New York", "NY", "10001", "12345");
        Agency agency1 = new Agency("A001", address, "Agency 1", "123-456-7890", email);
        assertTrue(agencyRepository.addAgency(agency1));
        assertFalse(agencyRepository.addAgency(agency1));
        assertEquals(1, agencyRepository.getAgencyList().size());
    }

    @Test
    public void testValidateExistingAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        String email = "agency1@example.com";
        Address address = new Address("123 Main St", "New York", "NY", "10001", "12345");
        Agency agency1 = new Agency("A001", address, "Agency 1", "123-456-7890", email);
        assertTrue(agencyRepository.addAgency(agency1));
        assertTrue(agencyRepository.validateAgency(agency1));
    }
    @Test
    public void testValidateNonExistingAgency() {
        AgencyRepository agencyRepository = new AgencyRepository();
        String email = "agency1@example.com";
        Address address = new Address("123 Main St", "New York", "NY", "10001", "12345");
        Agency agency1 = new Agency("A001", address, "Agency 1", "123-456-7890", email);
        assertFalse(agencyRepository.validateAgency(agency1));
    }

}