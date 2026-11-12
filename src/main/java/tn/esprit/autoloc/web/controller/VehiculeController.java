package tn.esprit.autoloc.web.controller;

// Atelier 4
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.VehiculeService;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final VehiculeService vehiculeService;

    @GetMapping
    public List<Vehicule> getAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getById(@PathVariable Long id) {
        return vehiculeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.save(vehicule);
    }

    @PutMapping("/{idVehicule}/agence/{idAgence}")
    public ResponseEntity<Vehicule> addAssignAgence(
            @PathVariable Long idVehicule,
            @PathVariable Long idAgence) {
        try {
            return ResponseEntity.ok(vehiculeService.addAssignAgence(idVehicule, idAgence));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public ResponseEntity<Vehicule> assignEquipment(
            @PathVariable Long idVehicule,
            @PathVariable Long idEquipement) {
        try {
            return ResponseEntity.ok(vehiculeService.assignEquipment(idVehicule, idEquipement));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idVehicule}/equipements/{idEquipement}")
    public ResponseEntity<Vehicule> unassignEquipment(
            @PathVariable Long idVehicule,
            @PathVariable Long idEquipement) {
        try {
            return ResponseEntity.ok(vehiculeService.unassignEquipment(idVehicule, idEquipement));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add-and-assign-agence/{idAgence}")
    public ResponseEntity<Vehicule> addAndAssignAgence(
            @RequestBody Vehicule vehicule,
            @PathVariable Long idAgence) {
        try {
            return ResponseEntity.ok(vehiculeService.addAndAssignAgence(vehicule, idAgence));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        try {
            return ResponseEntity.ok(vehiculeService.update(id, vehicule));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
