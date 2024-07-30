package gr.ekpa.citizen.service;

import gr.ekpa.citizen.domain.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @PostMapping
    public ResponseEntity<Object> createCitizen(@Valid @RequestBody Citizen citizen) {
        Optional<Citizen> existingCitizen = citizenService.findById(citizen.getId());
        if (existingCitizen.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Citizen with this ID already exists");
        }
        Citizen savedCitizen = citizenService.save(citizen);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCitizen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCitizen(@PathVariable String id) {
    	if (id.length() != 8) {
            return ResponseEntity.badRequest().body("Invalid ID format. ID must be exactly 8 characters.");
        }

        boolean isDeleted = citizenService.deleteById(id);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen with this ID not found.");
        }
        return ResponseEntity.ok("Citizen deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCitizen(@PathVariable String id, @RequestBody Map<String, String> citizen) {
    	Citizen existingCitizen = citizenService.findById(id).orElseThrow(() 
    			-> new IllegalArgumentException("Citizen with this ID not found."));
                
        if (citizen.containsKey("taxId")) {
            String taxId = citizen.get("taxId");
            if (!taxId.matches("\\d{9}")) {
                return ResponseEntity.badRequest().body("Invalid Tax ID. Tax ID must be exactly 9 digits.");
            }
            existingCitizen.setTaxId(taxId);
        }

        if (citizen.containsKey("address")) {
            existingCitizen.setAddress(citizen.get("address"));
        }

        Citizen updatedCitizen = citizenService.update(existingCitizen);
        return ResponseEntity.ok(updatedCitizen);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCitizen(@PathVariable String id) {
    	if (id.length() != 8) {
            return ResponseEntity.badRequest().body("Invalid ID format. ID must be exactly 8 characters.");
        }
    	
    	Optional<Citizen> citizen = citizenService.findById(id);
        if (!citizen.isPresent()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Citizen with this ID not found.");
        }
        
        return ResponseEntity.ok(citizen);
    }

    @GetMapping
    public ResponseEntity<?> searchCitizens(@RequestParam(required = false) String firstName,
    										@RequestParam(required = false) String lastName,
    										@RequestParam(required = false) String gender,
    										@RequestParam(required = false) String birthDate,
    										@RequestParam(required = false) String taxId,
    										@RequestParam(required = false) String address) {
    	if (birthDate != null && !birthDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
            return ResponseEntity.badRequest().body("Invalid date format. Date must be in the format DD-MM-YYYY.");
        }
    	
    	if (taxId != null && !taxId.matches("\\d{9}")) {
            return ResponseEntity.badRequest().body("Invalid Tax ID. Tax ID must be exactly 9 digits.");
        }
    	
    	Specification<Citizen> specs = Specification.where(null);

        if (firstName != null) {
            specs = specs.and((root, query, cb) -> cb.like(root.get("firstName"), "%" + firstName + "%"));
        }

        if (lastName != null) {
            specs = specs.and((root, query, cb) -> cb.like(root.get("lastName"), "%" + lastName + "%"));
        }

        if (gender != null) {
            specs = specs.and((root, query, cb) -> cb.equal(root.get("gender"), gender));
        }

        if (birthDate != null) {
            specs = specs.and((root, query, cb) -> cb.equal(root.get("birthDate"), birthDate));
        }

        if (taxId != null) {
            specs = specs.and((root, query, cb) -> cb.equal(root.get("taxId"), taxId));
        }

        if (address != null) {
            specs = specs.and((root, query, cb) -> cb.like(root.get("address"), "%" + address + "%"));
        }

        List<Citizen> citizens = citizenService.search(specs);

        if (citizens == null || citizens.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No citizens found.");
        }

        return ResponseEntity.ok(citizens);
    }
}
