package gr.ekpa.citizen.service;

import gr.ekpa.citizen.domain.Citizen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    public Citizen save(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public Optional<Citizen> findById(String id) {
        return citizenRepository.findById(id);
    }

    public boolean deleteById(String id) {
    	Optional<Citizen> citizen = citizenRepository.findById(id);
        if (citizen.isPresent()) {
            citizenRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean existsById(String id) {
        return citizenRepository.existsById(id);
    }

    public Citizen update(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public List<Citizen> search(Specification<Citizen> specs) {
        return citizenRepository.findAll(specs);
    }
}
