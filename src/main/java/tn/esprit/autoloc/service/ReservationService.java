package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation update(Long id, Reservation reservation) {
        return reservationRepository.findById(id)
                .map(existing -> {
                    existing.setDateDebut(reservation.getDateDebut());
                    existing.setDateFin(reservation.getDateFin());
                    existing.setStatut(reservation.getStatut());
                    existing.setClient(reservation.getClient());
                    existing.setVehicule(reservation.getVehicule());
                    return reservationRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Réservation introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
