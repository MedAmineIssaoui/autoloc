package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.MaintenanceRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    public Optional<Maintenance> findById(Long id) {
        return maintenanceRepository.findById(id);
    }

    public Maintenance save(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    public Maintenance update(Long id, Maintenance maintenance) {
        return maintenanceRepository.findById(id)
                .map(existing -> {
                    existing.setDateDebut(maintenance.getDateDebut());
                    existing.setDateFin(maintenance.getDateFin());
                    existing.setDescription(maintenance.getDescription());
                    existing.setVehicule(maintenance.getVehicule());
                    return maintenanceRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Maintenance introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        maintenanceRepository.deleteById(id);
    }
}
