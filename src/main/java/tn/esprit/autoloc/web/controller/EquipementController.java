package tn.esprit.autoloc.web.controller;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.EquipementService;

import java.util.List;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final EquipementService equipementService;

    @GetMapping
    public List<Equipement> getAll() {
        return equipementService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> getById(@PathVariable Long id) {
        return equipementService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.save(equipement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> update(@PathVariable Long id, @RequestBody Equipement equipement) {
        try {
            return ResponseEntity.ok(equipementService.update(id, equipement));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
