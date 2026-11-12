package tn.esprit.autoloc.repository;

// Atelier 4
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Employe;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
}
