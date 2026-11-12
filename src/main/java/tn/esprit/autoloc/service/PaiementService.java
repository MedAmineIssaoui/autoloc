package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.PaiementRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementService {

    private final PaiementRepository paiementRepository;

    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    public Optional<Paiement> findById(Long id) {
        return paiementRepository.findById(id);
    }

    public Paiement save(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public Paiement update(Long id, Paiement paiement) {
        return paiementRepository.findById(id)
                .map(existing -> {
                    existing.setMontant(paiement.getMontant());
                    existing.setDatePaiement(paiement.getDatePaiement());
                    existing.setContrat(paiement.getContrat());
                    return paiementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Paiement introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        paiementRepository.deleteById(id);
    }
}
