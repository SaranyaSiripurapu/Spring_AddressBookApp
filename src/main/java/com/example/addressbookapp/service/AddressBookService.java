package com.example.addressbookapp.service;

import com.example.addressbookapp.dto.ContactDTO;
import com.example.addressbookapp.exception.AddressBookException;
import com.example.addressbookapp.model.Contact;
import com.example.addressbookapp.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService implements IAddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;
    List<Contact> contactList = new ArrayList<>();
    @Override
    public List<Contact> getContact() {

        return addressBookRepository.findAll();
    }

    @Override
    public Contact getContactById(int contactId) {
        return contactList.stream().filter(contact -> contact.getContactId() == contactId).findFirst()
                .orElseThrow(() -> new AddressBookException("Contact not found"));
    }

    @Override
    public Contact createContact(ContactDTO contactDTO) {

        Contact contact=null;
        contact=new Contact(contactDTO);
        contactList.add(contact);
        return addressBookRepository.save(contact);
    }

    @Override
    public Contact updateContact(int contactId, ContactDTO contactDTO) {
        Contact contact = this.getContactById(contactId);
        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setAddress(contactDTO.getAddress());
        contact.setState(contactDTO.getState());
        contact.setCity(contactDTO.getCity());
        contact.setZip(contactDTO.getZip());
        contact.setPhone(contactDTO.getPhone());
        contactList.set(contactId - 1, contact);
        return contact;
    }

    @Override
    public void deleteContact(int contactId) {
        contactList.remove(contactId-1);

    }
}