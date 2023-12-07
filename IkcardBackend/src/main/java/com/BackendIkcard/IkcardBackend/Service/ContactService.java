package com.BackendIkcard.IkcardBackend.Service;




import com.BackendIkcard.IkcardBackend.Message.ReponseMessage;
import com.BackendIkcard.IkcardBackend.Models.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactService {
     ReponseMessage enregistrerContact(Contact nouveauContact, long userId);
     ReponseMessage modifierContact(long contactId, Contact contactModifie);
    List<Contact> afficherTousLesContacts(long userId);
    ReponseMessage supprimerContact(long contactId);
}
