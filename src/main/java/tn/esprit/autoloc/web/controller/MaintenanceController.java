package tn.esprit.autoloc.web.controller;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.MaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @GetMapping
    public List<Maintenance> getAll() {
        return maintenanceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getById(@PathVariable Long id) {
        return maintenanceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.save(maintenance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        try {
            return ResponseEntity.ok(maintenanceService.update(id, maintenance));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
