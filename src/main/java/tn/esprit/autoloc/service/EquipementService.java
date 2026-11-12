package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.EquipementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipementService {

    private final EquipementRepository equipementRepository;

    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    public Optional<Equipement> findById(Long id) {
        return equipementRepository.findById(id);
    }

    public Equipement save(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    public Equipement update(Long id, Equipement equipement) {
        return equipementRepository.findById(id)
                .map(existing -> {
                    existing.setLibelle(equipement.getLibelle());
                    return equipementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Equipement introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        equipementRepository.deleteById(id);
    }
}
