package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/contact")
public class ContactControlleur {

    private ContactService contactService;

    @PostMapping("/ajouter/{userId}")
    public ResponseEntity<String> ajouterContact(@RequestBody Contact nouveauContact, @PathVariable Long userId) {
        try {
            contactService.enregistrerContact(userId, nouveauContact);
            return ResponseEntity.ok("Le contact a été ajouté avec succès.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }
    }


    //**************************************************Modifier*****************************************
    @GetMapping("/modifier")
    public ReponseMessage Modifier(@RequestBody Contact contact) {
        return contactService.modifierContact(contact);
    }
}
