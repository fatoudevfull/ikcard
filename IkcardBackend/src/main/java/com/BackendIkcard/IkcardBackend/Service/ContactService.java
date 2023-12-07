package com.BackendIkcard.IkcardBackend.Service;




import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    // Création d'un Contact
   // ReponseMessage creerContact(Contact contact);
    void enregistrerContact(Long userId, Contact nouveauContact);

    // Mise à jour d'un Contact
    ReponseMessage modifierContact (Contact contact);

    //affichage d'un Contact

    List<Contact> afficherToutLesContact ();

    //Suppression d'un Contact
    ReponseMessage SupprimerContact (Long id);


    Contact saveOrUpdateContact(Contact contact);

    Optional<Contact> getContact(Long id);

    List<Contact> getAnnonce();

    void deleteContactById(Long id);
}
