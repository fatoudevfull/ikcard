package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.Reponse.ResponseMessage;
import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Entreprise;
import com.BackendIkcard.IkcardBackend.Repository.EntrepriseRepository;
import com.BackendIkcard.IkcardBackend.Service.EntrepriseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/entreprise")
public class EntrepriseController {
    private EntrepriseService entrepriseService;
    private EntrepriseRepository entrepriseRepository;

  @PostMapping("/ajouter")
    ReponseMessage Ajouter(@RequestBody Entreprise entreprise) {
      System.out.println(entreprise.email);
      System.out.println(entreprise.getId());
      System.out.println(entreprise.nom);
        return entrepriseService.creerEntreprise(entreprise);
    }

//**************************************************Modifier*****************************************
    @GetMapping("/modifier")
    public ReponseMessage Modifier(@RequestBody Entreprise entreprise) {
        return entrepriseService.modifierEntreprise(entreprise);
    }

    @GetMapping("/liste")
    public List<Entreprise> ListeEntreprise() {

      return entrepriseService.afficherToutLesEntreprise();
    }

    @PutMapping("/{id}/activer")
    public ResponseEntity<String> activerEntreprise(@PathVariable("id") Long id) {
        entrepriseService.activerEntreprise(id);
        return ResponseEntity.ok("Le entreprise a été activé avec succès.");
    }

    @DeleteMapping("/supprimer")
    public ReponseMessage Supprimer(@PathVariable Long id) {
        return entrepriseService.SupprimerEntreprise(id);
    }

    @ApiOperation(value = "Pour la creation d'un commentaire.")
    @PostMapping("/add")
    public ResponseEntity<?> registerEntreprise(@RequestBody Entreprise entreprise) {

        return ResponseMessage.generateResponse("ok", HttpStatus.OK, entrepriseService.saveEntreprise(entreprise));
    }
}
