package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.AgenceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgenceService {

    private final AgenceRepository agenceRepository;

    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    public Optional<Agence> findById(Long id) {
        return agenceRepository.findById(id);
    }

    public Agence save(Agence agence) {
        return agenceRepository.save(agence);
    }

    public Agence update(Long id, Agence agence) {
        return agenceRepository.findById(id)
                .map(existing -> {
                    existing.setNom(agence.getNom());
                    existing.setVille(agence.getVille());
                    existing.setAdresse(agence.getAdresse());
                    existing.setTelephone(agence.getTelephone());
                    return agenceRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Agence introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        agenceRepository.deleteById(id);
    }
}
