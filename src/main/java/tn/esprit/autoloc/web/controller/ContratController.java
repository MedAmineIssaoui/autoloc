package tn.esprit.autoloc.web.controller;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.ContratService;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService contratService;

    @GetMapping
    public List<Contrat> getAll() {
        return contratService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getById(@PathVariable Long id) {
        return contratService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.save(contrat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contrat> update(@PathVariable Long id, @RequestBody Contrat contrat) {
        try {
            return ResponseEntity.ok(contratService.update(id, contrat));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}
