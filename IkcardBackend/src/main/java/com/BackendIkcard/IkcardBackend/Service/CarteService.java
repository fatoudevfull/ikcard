package com.BackendIkcard.IkcardBackend.Service;

import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CarteService {


    public List<Carte> getAllCartes();

    public Optional<Carte> getCarteById(Long id) ;

    public Carte createCarte(Carte carte);

    public Carte updateCarte(Long id, Carte updatedCarte);

    public void deleteCarte(Long id) ;
}
