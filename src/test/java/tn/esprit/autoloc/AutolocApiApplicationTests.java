package tn.esprit.autoloc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.EquipementRepository;
import tn.esprit.autoloc.service.VehiculeService;

import java.math.BigDecimal;
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

    private Agence createAgence(String nom) {
        Agence agence = new Agence();
        agence.setNom(nom);
        agence.setVille("Tunis");
        agence.setAdresse("Adresse");
        agence.setTelephone("12345678");
        return agence;
    }
}
