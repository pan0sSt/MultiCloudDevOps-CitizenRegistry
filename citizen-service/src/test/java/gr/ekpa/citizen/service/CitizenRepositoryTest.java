package gr.ekpa.citizen.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;

import gr.ekpa.citizen.domain.Citizen;

@DataJpaTest
public class CitizenRepositoryTest {

    @Autowired
    private CitizenRepository citizenRepository;

    @Test
    public void testCreateCitizen() {
    	Citizen citizen = new Citizen("AB123456", "John", "Doe", "Male", "12-12-1990", "123456789", "123 Main St");
        citizenRepository.save(citizen);

        Citizen foundCitizen = citizenRepository.findById("AB123456").orElse(null);
        assertNotNull(foundCitizen);
        assertEquals("John", foundCitizen.getFirstName());
        assertEquals("Doe", foundCitizen.getLastName());
        assertEquals("Male", foundCitizen.getGender());
        assertEquals("12-12-1990", foundCitizen.getBirthDate());
        assertEquals("123456789", foundCitizen.getTaxId());
        assertEquals("123 Main St", foundCitizen.getAddress());
    }

    @Test
    public void testUpdateCitizen() {
    	Citizen citizen = new Citizen("AB123456", "John", "Doe", "Male", "12-12-1990", "123456789", "123 Main St");
        citizenRepository.save(citizen);

        citizen.setTaxId("122222222");
        citizen.setAddress("222 Second St");
        citizenRepository.save(citizen);
        
        Citizen foundCitizen = citizenRepository.findById("AB123456").orElse(null);
        assertNotNull(foundCitizen);
        assertEquals("John", foundCitizen.getFirstName());
        assertEquals("Doe", foundCitizen.getLastName());
        assertEquals("Male", foundCitizen.getGender());
        assertEquals("12-12-1990", foundCitizen.getBirthDate());
        assertEquals("122222222", foundCitizen.getTaxId());
        assertEquals("222 Second St", foundCitizen.getAddress());
    }
    
    @Test
    public void testDeleteCitizen() {
        Citizen citizen = new Citizen("AB123456", "John", "Doe", "Male", "12-11-1980", "123456789", "123 Main St");
        citizenRepository.save(citizen);

        citizenRepository.deleteById("AB123456");
        Citizen foundCitizen = citizenRepository.findById("AB123456").orElse(null);
        assertNull(foundCitizen);
    }
    
    @Test
    public void testCitizenRetrieval() {
    	Citizen citizen1 = new Citizen("AB123451", "John", "Doe", "Male", "12-12-1990", "123456781", "121 Main St");
    	Citizen citizen2 = new Citizen("AB123452", "Ellie", "Doe", "Female", "13-12-1990", "123456782", "122 Main St");
    	Citizen citizen3 = new Citizen("AB123453", "Daugh", "Doe", "Male", "14-12-1990", "123456783", "123 Main St");

        citizenRepository.save(citizen1);
        citizenRepository.save(citizen2);
        citizenRepository.save(citizen3);

        List<Citizen> citizens = citizenRepository.findAll();
        assertEquals(citizens.size(), 3);
        
        // Check with search parameter "firstName LIKE John", we get one citizen
        Specification<Citizen> specs = Specification.where(null);
        specs = specs.and((root, query, cb) -> cb.like(root.get("firstName"), "%John%"));
        citizens = citizenRepository.findAll(specs);
        assertEquals(citizens.size(), 1);
        assertEquals(citizens.get(0).getFirstName(), citizen1.getFirstName());
        
        // Check with search parameter "gender=Female", we get one citizen
        specs = Specification.where(null);
        specs = specs.and((root, query, cb) -> cb.equal(root.get("gender"), "Female"));
        citizens = citizenRepository.findAll(specs);
        assertEquals(citizens.size(), 1);
        assertEquals(citizens.get(0).getGender(), citizen2.getGender());
        
        // Check with search parameter "lastName LIKE Doe", we get three citizens
        int matches = 0;
        specs = Specification.where(null);
        specs = specs.and((root, query, cb) -> cb.like(root.get("lastName"), "%Doe%"));
        citizens = citizenRepository.findAll(specs);
        assertEquals(citizens.size(), 3);
        for (Citizen citizen: citizens) {
        	if (citizen1.getId().equals(citizen.getId()) || 
        		citizen2.getId().equals(citizen.getId()) || 
        		citizen3.getId().equals(citizen.getId())) {
        		matches++;
        	}
        }
        assertEquals(matches, 3);
    }
}
