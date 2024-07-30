package gr.ekpa.citizen.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import gr.ekpa.citizen.domain.Citizen;

public class CitizenValidationTest {

	private static Validator validator;
	
	@BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@ParameterizedTest
    @ValueSource(strings = { "id", "INVALID", "INVALID123", ""})
    public void testInvalidId(String Id) {
        Citizen citizen = new Citizen(Id, "John", "Doe", "Male", "12-11-1980", "123456789", "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("ID must be exactly 8 characters", violation.getMessage());
    }
	
	@ParameterizedTest
    @ValueSource(strings = { "A1234562", "AB234561"})
    public void testValidId(String Id) {
        Citizen citizen = new Citizen(Id, "John", "Doe", "Male", "12-11-1980", "123456789", "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "t", "taxId", "123", "12345678a"})
    public void testInvalidTaxId(String TaxId) {
    	Citizen citizen = new Citizen("A1234561", "John", "Doe", "Male", "12-11-1980", TaxId, "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("Tax ID must be exactly 9 digits", violation.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "123456789", "123456781"})
    public void testValidTaxId(String TaxId) {
    	Citizen citizen = new Citizen("A1234561", "John", "Doe", "Male", "12-11-1980", TaxId, "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "d", "Date", "12/12/2012", "1996-14-12"})
    public void testInvalidBirthDate(String BirthDate) {
    	Citizen citizen = new Citizen("A1234561", "John", "Doe", "Male", BirthDate, "123456789", "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("Birth date must be in the format dd-MM-yyyy", violation.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "12-12-2012", "01-05-2000"})
    public void testValidBirthDate(String BirthDate) {
    	Citizen citizen = new Citizen("A1234561", "John", "Doe", "Male", BirthDate, "123456789", "123 Main St");

        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "", " ", "		", "\t", "\n"})
    public void testEmptyFirstName(String firstName) {
    	Citizen citizen = new Citizen("A1234562", firstName, "Doe", "Male", "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("First name is mandatory", violation.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "Panos", "Giannis", "Christina"})
    public void testValidFirstName(String firstName) {
    	Citizen citizen = new Citizen("A1234562", firstName, "Doe", "Male", "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "", " ", "		", "\t", "\n"})
    public void testEmptyLastName(String LastName) {
    	Citizen citizen = new Citizen("A1234562", "John", LastName, "Male", "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("Last name is mandatory", violation.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "Stavrinakis", "Papadopoulou", "Pizza"})
    public void testValidLastName(String LastName) {
    	Citizen citizen = new Citizen("A1234562", "John", LastName, "Male", "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "", " ", "		", "\t", "\n"})
    public void testEmptyGender(String Gender) {
    	Citizen citizen = new Citizen("A1234562", "John", "Doe", Gender, "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertFalse(violations.isEmpty());

        ConstraintViolation<Citizen> violation = violations.iterator().next();
        assertEquals("Gender is mandatory", violation.getMessage());
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "Male", "Female", "NonBinary"})
    public void testValidGender(String Gender) {
    	Citizen citizen = new Citizen("A1234562", "John", "Doe", Gender, "01-01-1990", "123456781", "123 Main St,  12342");
        
        Set<ConstraintViolation<Citizen>> violations = validator.validate(citizen);
        assertTrue(violations.isEmpty());
    }
}