package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteServIpml implements CarteService {

    @Autowired
    private CarteRepository carteRepository;

    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

    public Optional<Carte> getCarteById(Long id) {
        return carteRepository.findById(id);
    }

    public Carte createCarte(Carte carte) {
        return carteRepository.save(carte);
    }

    public Carte updateCarte(Long id, Carte updatedCarte) {
        if (carteRepository.existsById(id)) {
            updatedCarte.setId(id);
            return carteRepository.save(updatedCarte);
        }
        return null; // Handle not found case
    }

    public void deleteCarte(Long id) {
        carteRepository.deleteById(id);
    }
}
