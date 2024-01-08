package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Models.Users;
import com.BackendIkcard.IkcardBackend.Models.UserSimple;
import com.BackendIkcard.IkcardBackend.Repository.ContactRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserSimpleRepository;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContactservImp implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserSimpleRepository userRepository;

    public ReponseMessage enregistrerContact(Contact nouveauContact, long userId) {
        UserSimple utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        nouveauContact.setUser(utilisateur);
        utilisateur.getContacts().add(nouveauContact);

        userRepository.save(utilisateur);

        return new ReponseMessage("Contact enregistré avec succès", true);
    }

    public List<Contact> getAllCartes() {
        return contactRepository.findAll();
    }

    public ReponseMessage modifierContact(long contactId, Contact contactModifie) {
        Contact contactExistant = contactRepository.findById(contactId)
                .orElseThrow(() -> new NoSuchElementException("Contact introuvable"));

        // Mettez à jour les champs non-nulls du contact existant avec les nouvelles valeurs
        if (contactModifie.getNomComplet() != null) {
            contactExistant.setNomComplet(contactModifie.getNomComplet());
        }

        if (contactModifie.getEmail() != null) {
            contactExistant.setEmail(contactModifie.getEmail());
        }

        if (contactModifie.getFixe1() != null) {
            contactExistant.setFixe1(contactModifie.getFixe1());
        }

        // Ajoutez d'autres champs à mettre à jour de la même manière

        contactRepository.save(contactExistant);

        return new ReponseMessage("Contact modifié avec succès", true);
    }


    public List<Contact> afficherTousLesContacts(long userId) {
        Users utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        return utilisateur.getContacts();
    }

 /*   @Override
    public void activerContact(Long id) {
        Optional<Contact> existingAdmin = contactRepository.findById(id);
        existingAdmin.ifPresent(contact -> {
            // Set etat to true
            contact.setEtat(true);

            // Save the updated entity
            contactRepository.save(contact);
        });
    }*/
 public void desactiverCompte(Long userId) {
     Contact contact = contactRepository.findById(userId)
             .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

     contact.setEtat(false); // Mettez à false pour désactiver le compte
     contactRepository.save(contact);
 }

    public void activerCompte(Long userId) {
        Contact contact = contactRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        contact.setEtat(true); // Mettez à true pour activer le compte
        contactRepository.save(contact);
    }

    public ReponseMessage supprimerContact(long contactId) {
        if (contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);
            return new ReponseMessage("Contact supprimé avec succès", true);
        } else {
            return new ReponseMessage("Contact non trouvé", false);
        }
    }
}
