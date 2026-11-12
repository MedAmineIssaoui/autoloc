package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.EmployeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;

    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    public Optional<Employe> findById(Long id) {
        return employeRepository.findById(id);
    }

    public Employe save(Employe employe) {
        return employeRepository.save(employe);
    }

    public Employe update(Long id, Employe employe) {
        return employeRepository.findById(id)
                .map(existing -> {
                    existing.setNom(employe.getNom());
                    existing.setPrenom(employe.getPrenom());
                    existing.setRole(employe.getRole());
                    existing.setAgence(employe.getAgence());
                    return employeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Employé introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        employeRepository.deleteById(id);
    }
}
