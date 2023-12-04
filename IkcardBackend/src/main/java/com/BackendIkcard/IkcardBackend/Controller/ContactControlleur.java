package com.BackendIkcard.IkcardBackend.Controller;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8200", "http://localhost:8100"}, maxAge = 3600, allowCredentials = "true")
@Api(value = "admin", description = "Les actions reslisables par les admin du systeme.")
@RestController
@Controller
@RequestMapping("/contact")
public class ContactControlleur {

    private ContactService contactService;

    @PostMapping("/ajouter")
    ReponseMessage Ajouter(@RequestBody Contact contact) {
        System.out.println(contact.email);
        System.out.println(contact.id);
        System.out.println(contact.nomComplet);
        return contactService.creerContact(contact);
    }

    //**************************************************Modifier*****************************************
    @GetMapping("/modifier")
    public ReponseMessage Modifier(@RequestBody Contact contact) {
        return contactService.modifierContact(contact);
    }
}
