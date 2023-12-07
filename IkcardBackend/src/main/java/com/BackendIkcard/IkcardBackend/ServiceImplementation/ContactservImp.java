package com.BackendIkcard.IkcardBackend.ServiceImplementation;

import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;
import com.BackendIkcard.IkcardBackend.Models.User;
import com.BackendIkcard.IkcardBackend.Repository.ContactRepository;
import com.BackendIkcard.IkcardBackend.Repository.UserRepository;
import com.BackendIkcard.IkcardBackend.Service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ContactservImp implements ContactService {
    private ContactRepository contactRepository;
    private UserRepository userRepository;

  /*  @Override
    public ReponseMessage creerContact(Contact contact) {
        if (contactRepository.findByEmail(contact.getEmail()) == null) {
            contactRepository.save(contact);
            ReponseMessage message = new ReponseMessage("Contact ajouté avec succes", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("le Contact existe déjà ", false);

            return message;
        }
    }*/
    public void enregistrerContact(Long userId, Contact nouveauContact) {
        User utilisateur = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur introuvable"));

        nouveauContact.setUser(utilisateur);
        utilisateur.getContacts().add(nouveauContact);

        userRepository.save(utilisateur);
    }

    @Override
    public ReponseMessage modifierContact(Contact contact) {
        if (contactRepository.findById(contact.getId()) != null) {
            return contactRepository.findById(contact.getId())
                    .map(contact1 -> {
                        contact1.setAddresse(contact.getAddresse());
                        contact1.setFixe1(contact.getFixe1());
                        contact1.setEmail1(contact.getEmail1());
                        contact1.setFixe1(contact.getFixe1());
                        contact1.setInstagramLink(contact.getInstagramLink());
                        contact1.setLinkedinLink(contact.getLinkedinLink());
                        contactRepository.save(contact);
                        ReponseMessage message = new ReponseMessage("Contat modifié avec succes", true);
                        return message;
                    }).orElseThrow(() -> new RuntimeException("Désole, contact non trouvée"));
        } else {
            ReponseMessage message = new ReponseMessage("Désole, contact non trouvée", false);

            return message;
        }
    }

    @Override
    public List<Contact> afficherToutLesContact() {
        return contactRepository.findAll();
    }

    @Override
    public ReponseMessage SupprimerContact(Long id) {
        final Contact contact = null;
        if (contactRepository.findById(id) != null) {
            contact.setEtat(false);
            ReponseMessage message = new ReponseMessage("Contact supprimée avec succes", true);
            return message;
        } else {
            ReponseMessage message = new ReponseMessage("Contact non trouvée", false);
            return message;
        }
    }

    @Override
    public Contact saveOrUpdateContact(Contact contact) {
        return null;
    }

    @Override
    public Optional<Contact> getContact(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Contact> getAnnonce() {
        return null;
    }

    @Override
    public void deleteContactById(Long id) {

    }
}
