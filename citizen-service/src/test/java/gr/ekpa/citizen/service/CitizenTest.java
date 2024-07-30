package gr.ekpa.citizen.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import gr.ekpa.citizen.domain.Citizen;

public class CitizenTest {
	
    @Test
    public void testCitizenConstructor() {
        Citizen citizen = new Citizen("A1234562", "John", "Doe", "Male", "01-01-1990", "123456781", "123 Main St,  12342");

        assertEquals("A1234562", citizen.getId());
        assertEquals("John", citizen.getFirstName());
        assertEquals("Doe", citizen.getLastName());
        assertEquals("Male", citizen.getGender());
        assertEquals("01-01-1990", citizen.getBirthDate());
        assertEquals("123456781", citizen.getTaxId());
        assertEquals("123 Main St,  12342", citizen.getAddress());
    }
    
    @Test
    public void testEmptyConstructor() {
        Citizen citizen = new Citizen();

        assertNull(citizen.getId());
        assertNull(citizen.getFirstName());
        assertNull(citizen.getLastName());
        assertNull(citizen.getGender());
        assertNull(citizen.getBirthDate());
        assertNull(citizen.getTaxId());
        assertNull(citizen.getAddress());
    }

    @Test
    public void testCitizenSetters() {
        Citizen citizen = new Citizen();
        citizen.setId("A1234561");
        citizen.setFirstName("Ellie");
        citizen.setLastName("Doe");
        citizen.setGender("Female");
        citizen.setBirthDate("02-02-1990");
        citizen.setTaxId("123456782");
        citizen.setAddress("123 Main St,  12342");

        assertEquals("A1234561", citizen.getId());
        assertEquals("Ellie", citizen.getFirstName());
        assertEquals("Doe", citizen.getLastName());
        assertEquals("Female", citizen.getGender());
        assertEquals("02-02-1990", citizen.getBirthDate());
        assertEquals("123456782", citizen.getTaxId());
        assertEquals("123 Main St,  12342", citizen.getAddress());
    }
    
    @ParameterizedTest
    @NullSource
    public void testEmptyTaxId(String TaxId) {
    	Citizen citizen = new Citizen();
    	citizen.setTaxId(TaxId);
        assertNull(citizen.getTaxId());
    }
    
    @ParameterizedTest
    @NullSource
    public void testEmptyAddress(String Address) {
    	Citizen citizen = new Citizen();
    	citizen.setAddress(Address);
        assertNull(citizen.getAddress());
    }
}