package gr.ekpa.citizen.client;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import gr.ekpa.citizen.domain.Citizen;

@SpringBootApplication
public class CitizenClientApplication implements CommandLineRunner {
	
	@Autowired
    private CitizenClient citizenClient;
	
    public static void main(String[] args) {
        SpringApplication.run(CitizenClientApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("1. Insert Citizen");
			    System.out.println("2. Delete Citizen");
			    System.out.println("3. Update Citizen");
			    System.out.println("4. Get All Citizens");
			    System.out.println("5. Show Citizen");
			    System.out.println("6. Exit");
			    System.out.print("Select: ");
			    String choice = scanner.nextLine();
			    if (!handleChoice(choice)) {
			    	System.out.println("Terminating...");
			        System.exit(0);
			    }
			}
		}
    }
    
    private boolean handleChoice(String choice) {
    	Citizen citizen = new Citizen();
        citizen.setId("A1234562");
        citizen.setFirstName("John");
        citizen.setLastName("Doe");
        citizen.setGender("Male");
        citizen.setBirthDate("01-01-1990");
        citizen.setTaxId("123456781");
        citizen.setAddress("123 Main St,  12341");
        
        switch (choice) {
            case "1":
                // Insert Citizen
            	Citizen createdCitizen = citizenClient.createCitizen(citizen);
                System.out.println("Created Citizen: " + createdCitizen);
                break;
            case "2":
                // Delete Citizen
            	citizenClient.deleteCitizen(citizen.getId());
                System.out.println("Deleted Citizen: " + citizen.getId());
                break;
            case "3":
                // Update Citizen
            	citizen.setTaxId("122222222");
                citizen.setAddress("222 Secondary St,  12222");
            	citizenClient.updateCitizen(citizen.getId(), citizen);
                System.out.println("Updated Citizen: " + citizenClient.getCitizenById(citizen.getId()));
                break;
            case "4":
                // Get All Citizens
            	System.out.println("All Citizens: " + citizenClient.getAllCitizens());
                break;
            case "5":
                // Show Citizen
            	Citizen fetchedCitizen = citizenClient.getCitizenById(citizen.getId());
                System.out.println("Fetched Citizen: " + fetchedCitizen);
                break;
            case "6":
                return false;
            default:
                System.out.println("No valid option.");
                return false;
        }
        return true;
    }
}