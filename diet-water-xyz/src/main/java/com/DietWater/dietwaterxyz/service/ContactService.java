package com.DietWater.dietwaterxyz.service;

import com.DietWater.dietwaterxyz.dao.ContactDao;
import com.DietWater.dietwaterxyz.model.Contact;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    ContactDao contactDao;
    ZipcodeService zipcodeService;

    @Autowired
    public ContactService(ContactDao dao, ZipcodeService zipcodeService) {
        this.contactDao = dao;
        this.zipcodeService = zipcodeService;
    }


    public ContactView saveContact(ContactView contactView){
        boolean zipCodeValid;
        try {
            zipCodeValid = zipcodeService.validZipcode(contactView.getCity(), contactView.getState(), contactView.getZipcode());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("ZipCode is not valid");
        }
        Contact contact = Contact.fromContactView(contactView);
        contactDao.save(contact);
        contactView.setContactId(contact.getContactId());
        return contactView;

    }

    public ContactView updateContact(ContactView contactView){
        return null;
    }

    public ContactView findContactById(Integer id){
        return null;
    }

    public List<ContactView> findContacts(String firstName, String lastName, String zipcode){
        return null;
    }

    public void deleteContact(Integer id){
        contactDao.deleteById(id);
    }


}
