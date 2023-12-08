package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.ContactRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContactservImp implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public ReponseMessage enregistrerContact(Contact nouveauContact, long userId) {
        User utilisateur = userRepository.findById(userId)
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

        // Mettez à jour les champs du contact existant avec les nouvelles valeurs
        contactExistant.setNomComplet(contactModifie.getNomComplet());
        contactExistant.setEmail(contactModifie.getEmail());
        contactExistant.setFixe1(contactModifie.getFixe1());
        contactExistant.setInstagramLink(contactModifie.getInstagramLink());
        contactExistant.setLinkedinLink(contactModifie.getLinkedinLink());
        contactExistant.setFacebookLink(contactModifie.getFacebookLink());
        contactExistant.setEmail1(contactModifie.getEmail1());
        contactExistant.setAddresse(contactModifie.getAddresse());
        contactExistant.setWebSite(contactExistant.getWebSite());
        contactExistant.setCompagnie(contactModifie.getCompagnie());
        contactExistant.setMobile3(contactModifie.getMobile3());
        contactExistant.setMobile1(contactModifie.getMobile1());
        contactExistant.setAutrelink(contactModifie.getAutrelink());
        contactExistant.setFixe2(contactModifie.getFixe2());
        contactExistant.setTweetterLink(contactModifie.getTweetterLink());
        contactExistant.setTiktoklink(contactModifie.getTiktoklink());
        // Ajoutez d'autres champs à mettre à jour

        contactRepository.save(contactExistant);

        return new ReponseMessage("Contact modifié avec succès", true);
    }

    public List<Contact> afficherTousLesContacts(long userId) {
        User utilisateur = userRepository.findById(userId)
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
