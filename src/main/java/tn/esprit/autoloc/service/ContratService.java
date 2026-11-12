package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.ContratRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratService {

    private final ContratRepository contratRepository;

    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    public Optional<Contrat> findById(Long id) {
        return contratRepository.findById(id);
    }

    public Contrat save(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Transactional
    public Contrat addWithPayments(Contrat contrat) {
        if (contrat.getPaiements() == null) {
            throw new IllegalArgumentException("La liste des paiements ne peut pas être nulle");
        }
        contrat.getPaiements().forEach(paiement -> paiement.setContrat(contrat));
        return contratRepository.save(contrat);
    }

    public Contrat update(Long id, Contrat contrat) {
        return contratRepository.findById(id)
                .map(existing -> {
                    existing.setDateSignature(contrat.getDateSignature());
                    existing.setMontantTotal(contrat.getMontantTotal());
                    existing.setValide(contrat.isValide());
                    existing.setReservation(contrat.getReservation());
                    return contratRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        contratRepository.deleteById(id);
    }
}
