package tn.esprit.autoloc.service;

// Atelier 4
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        return clientRepository.findById(id)
                .map(existing -> {
                    existing.setNom(client.getNom());
                    existing.setPrenom(client.getPrenom());
                    existing.setEmail(client.getEmail());
                    existing.setTelephone(client.getTelephone());
                    existing.setNumPermis(client.getNumPermis());
                    existing.setDateInscription(client.getDateInscription());
                    return clientRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'id : " + id));
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
