package tn.esprit.autoloc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.ClientRepository;
import tn.esprit.autoloc.repository.PaiementRepository;
import tn.esprit.autoloc.repository.ReservationRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;
import tn.esprit.autoloc.service.ContratService;
import tn.esprit.autoloc.service.VehiculeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class AutolocApiApplicationTests {

    @Autowired
    private VehiculeService vehiculeService;

    @Autowired
    private AgenceRepository agenceRepository;

    @Autowired
    private EquipementRepository equipementRepository;

    @Autowired
    private ContratService contratService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private PaiementRepository paiementRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    void managesVehicleAgencyAndEquipmentAssignments() {
        Agence agence = agenceRepository.save(createAgence("Agence initiale"));
        Equipement cascadeEquipement = new Equipement();
        cascadeEquipement.setLibelle("GPS");
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-001");
        vehicule.setMarque("Marque");
        vehicule.setModele("Modele");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("45.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setEquipements(new HashSet<>(Set.of(cascadeEquipement)));

        Vehicule saved = vehiculeService.addAndAssignAgence(vehicule, agence.getIdAgence());

        Assertions.assertNotNull(saved.getIdVehicule());
        Assertions.assertEquals(agence.getIdAgence(), saved.getAgence().getIdAgence());
        Assertions.assertNotNull(cascadeEquipement.getIdEquipement());

        Agence autreAgence = agenceRepository.save(createAgence("Agence secondaire"));
        vehiculeService.addAssignAgence(saved.getIdVehicule(), autreAgence.getIdAgence());
        Assertions.assertEquals(autreAgence.getIdAgence(), saved.getAgence().getIdAgence());

        Equipement equipementExistant = new Equipement();
        equipementExistant.setLibelle("Climatisation");
        equipementExistant = equipementRepository.save(equipementExistant);
        vehiculeService.assignEquipment(saved.getIdVehicule(), equipementExistant.getIdEquipement());
        Assertions.assertTrue(saved.getEquipements().contains(equipementExistant));

        vehiculeService.unassignEquipment(saved.getIdVehicule(), equipementExistant.getIdEquipement());
        Assertions.assertFalse(saved.getEquipements().contains(equipementExistant));
    }

    @Test
    @Transactional
    void addsContractAndPersistsItsPayments() {
        Agence agence = agenceRepository.save(createAgence("Agence contrats"));
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("CONTRAT-001");
        vehicule.setMarque("Marque");
        vehicule.setModele("Modele");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(new BigDecimal("45.00"));
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setAgence(agence);
        vehicule = vehiculeRepository.save(vehicule);

        Client client = new Client();
        client.setNom("Nom");
        client.setPrenom("Prenom");
        client.setEmail("contrat-test@example.com");
        client.setTelephone("12345678");
        client.setNumPermis("PERMIS-TEST");
        client.setDateInscription(LocalDate.now());
        client = clientRepository.save(client);

        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.now());
        reservation.setDateFin(LocalDate.now().plusDays(1));
        reservation.setStatut(StatutReservation.CONFIRMEE);
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation = reservationRepository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.now());
        contrat.setMontantTotal(new BigDecimal("90.00"));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        contrat.setPaiements(new ArrayList<>(Set.of(
                createPaiement("45.00"),
                createPaiement("45.00")
        )));

        Contrat saved = contratService.addWithPayments(contrat);

        Assertions.assertNotNull(saved.getIdContrat());
        Assertions.assertEquals(2, saved.getPaiements().size());
        saved.getPaiements().forEach(paiement -> {
            Assertions.assertNotNull(paiement.getIdPaiement());
            Assertions.assertTrue(paiementRepository.existsById(paiement.getIdPaiement()));
            Assertions.assertEquals(saved.getIdContrat(), paiement.getContrat().getIdContrat());
        });
    }

    private Paiement createPaiement(String montant) {
        Paiement paiement = new Paiement();
        paiement.setMontant(new BigDecimal(montant));
        paiement.setDatePaiement(LocalDate.now());
        return paiement;
    }

    private Agence createAgence(String nom) {
        Agence agence = new Agence();
        agence.setNom(nom);
        agence.setVille("Tunis");
        agence.setAdresse("Adresse");
        agence.setTelephone("12345678");
        return agence;
    }
}
