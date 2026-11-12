package tn.esprit.autoloc.service;

// Atelier 4
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AgenceRepository agenceRepository;
    private final EquipementRepository equipementRepository;

    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    public Optional<Vehicule> findById(Long id) {
        return vehiculeRepository.findById(id);
    }

    public Vehicule save(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Transactional
    public Vehicule addAssignAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable avec l'id : " + idVehicule));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id : " + idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Transactional
    public Vehicule assignEquipment(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable avec l'id : " + idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Équipement introuvable avec l'id : " + idEquipement));
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Transactional
    public Vehicule unassignEquipment(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Véhicule introuvable avec l'id : " + idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Équipement introuvable avec l'id : " + idEquipement));
        vehicule.getEquipements().remove(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Transactional
    public Vehicule addAndAssignAgence(Vehicule vehicule, Long idAgence) {
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence introuvable avec l'id : " + idAgence));
        vehicule.setIdVehicule(null);
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    public Vehicule update(Long id, Vehicule vehicule) {
        return vehiculeRepository.findById(id)
                .map(existing -> {
                    existing.setImmatriculation(vehicule.getImmatriculation());
                    existing.setMarque(vehicule.getMarque());
                    existing.setModele(vehicule.getModele());
                    existing.setCategorie(vehicule.getCategorie());
                    existing.setTarifJournalier(vehicule.getTarifJournalier());
                    existing.setStatut(vehicule.getStatut());
                    existing.setAgence(vehicule.getAgence());
                    return vehiculeRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        vehiculeRepository.deleteById(id);
    }
}
