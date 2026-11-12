package tn.esprit.autoloc.web.controller;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.AgenceService;

import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final AgenceService agenceService;

    @GetMapping
    public List<Agence> getAll() {
        return agenceService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agence> getById(@PathVariable Long id) {
        return agenceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Agence create(@RequestBody Agence agence) {
        return agenceService.save(agence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agence> update(@PathVariable Long id, @RequestBody Agence agence) {
        try {
            return ResponseEntity.ok(agenceService.update(id, agence));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
