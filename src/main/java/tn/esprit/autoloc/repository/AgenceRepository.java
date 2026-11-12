package tn.esprit.autoloc.repository;

// Atelier 4
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.autoloc.domain.Agence;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
}
