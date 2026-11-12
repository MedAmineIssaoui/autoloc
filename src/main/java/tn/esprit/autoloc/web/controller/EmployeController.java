package tn.esprit.autoloc.web.controller;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.EmployeService;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @GetMapping
    public List<Employe> getAll() {
        return employeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> getById(@PathVariable Long id) {
        return employeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Employe create(@RequestBody Employe employe) {
        return employeService.save(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> update(@PathVariable Long id, @RequestBody Employe employe) {
        try {
            return ResponseEntity.ok(employeService.update(id, employe));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeService.deleteById(id);
    }
}
