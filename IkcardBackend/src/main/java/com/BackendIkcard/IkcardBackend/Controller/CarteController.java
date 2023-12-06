package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Models.Carte;
import com.BackendIkcard.IkcardBackend.Service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cartes")
public class CarteController {

    @Autowired
    private CarteService carteService;

    @GetMapping
    public List<Carte> getAllCartes() {
        return carteService.getAllCartes();
    }

    @GetMapping("/{id}")
    public Carte getCarteById(@PathVariable Long id) {
        return carteService.getCarteById(id).orElse(null); // Handle not found case
    }

    @PostMapping
    public Carte createCarte(@RequestBody Carte carte) {
        return carteService.createCarte(carte);
    }

    @PutMapping("/{id}")
    public Carte updateCarte(@PathVariable Long id, @RequestBody Carte updatedCarte) {
        return carteService.updateCarte(id, updatedCarte);
    }

    @DeleteMapping("/{id}")
    public void deleteCarte(@PathVariable Long id) {
        carteService.deleteCarte(id);
    }
}
