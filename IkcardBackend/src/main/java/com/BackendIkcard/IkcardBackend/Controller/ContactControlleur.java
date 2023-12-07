package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/contact")
public class ContactControlleur {

    private final ContactService contactService;


    public ContactControlleur(ContactService contactService) {
        this.contactService = contactService;
    }


    @PostMapping("/enregistrer/{userId}")
    public ReponseMessage enregistrerContact(@RequestBody Contact nouveauContact, @PathVariable Long userId) {
        return contactService.enregistrerContact(nouveauContact, userId);
    }

    @PutMapping("/modifier/{contactId}")
    public ReponseMessage modifierContact(@PathVariable Long contactId, @RequestBody Contact contactModifie) {
        return contactService.modifierContact(contactId, contactModifie);
    }

    @GetMapping("/afficher/{userId}")
    public List<Contact> afficherTousLesContacts(@PathVariable Long userId) {
        return contactService.afficherTousLesContacts(userId);
    }

    @DeleteMapping("/supprimer/{contactId}")
    public ReponseMessage supprimerContact(@PathVariable Long contactId) {
        return contactService.supprimerContact(contactId);
    }
}
