package com.DietWater.dietwaterxyz.service;

import com.DietWater.dietwaterxyz.dao.ContactDao;
import com.DietWater.dietwaterxyz.model.Contact;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private ContactDao contactDao;
    private ZipcodeService zipcodeService;

    @Autowired
    public ContactService(ContactDao dao, ZipcodeService zipcodeService) {
        this.contactDao = dao;
        this.zipcodeService = zipcodeService;
    }


    public ContactView saveContact(ContactView contactView){
        if(zipcodeService.validZipcode(contactView.getCity(), contactView.getState(), contactView.getZipcode())) {
            Contact contact = Contact.fromContactView(contactView);
            try{
                contact = contactDao.save(contact);
            }catch (DataIntegrityViolationException die){
                throw new DataIntegrityViolationException("Email repeated");
            }
            contactView.setContactId(contact.getContactId());
            return contactView;
        }else{
            throw new IllegalArgumentException("Invalid ZipCode");
        }
    }

    public ContactView updateContact(ContactView contactView){
        if( zipcodeService.validZipcode(contactView.getCity(), contactView.getState(), contactView.getZipcode())) {
            Contact contact = Contact.fromContactView(contactView);
            contactDao.save(contact);
            return contactView;
        }else{
            throw new IllegalArgumentException("Invalid ZipCode");
        }
    }

    public ContactView findContactById(Integer id){
        Contact contact = contactDao.findById(id).orElse(null);
        return ContactView.fromContact(contact);

    }


    public List<ContactView> findContacts(String firstName, String lastName, String zipcode){
        List<ContactView> contactViewList;
        List<Contact> contactList;


        if((firstName!= null && !firstName.isEmpty()) && (lastName!= null && !lastName.isEmpty()) && (zipcode!= null && !zipcode.isEmpty())){
            contactList = contactDao.findAllByFirstNameAndLastNameAndZipcode(firstName,lastName,zipcode);

        }else if((firstName!= null && !firstName.isEmpty()) && (lastName!= null && !lastName.isEmpty())){
            contactList = contactDao.findAllByFirstNameAndLastName(firstName,lastName);

        }else if((firstName!= null && !firstName.isEmpty()) && (zipcode!= null && !zipcode.isEmpty())){
            contactList = contactDao.findAllByFirstNameAndZipcode(firstName,zipcode);

        }else if((lastName!= null && !lastName.isEmpty()) && (zipcode!= null && !zipcode.isEmpty())){
            contactList = contactDao.findAllByLastNameAndZipcode(lastName,zipcode);

        }else if(firstName!= null && !firstName.isEmpty()){
            contactList = contactDao.findAllByFirstName(firstName);

        }else if(lastName!= null && !lastName.isEmpty()){
            contactList = contactDao.findAllByLastName(lastName);

        }else if(zipcode!= null && !zipcode.isEmpty()){
            contactList = contactDao.findAllByZipcode(zipcode);

        }else {
            contactList = contactDao.findAll();
        }

        contactViewList = contactList.stream().map(ContactView::fromContact).collect(Collectors.toList());

        return contactViewList;
    }

    public void deleteContact(Integer id){
        contactDao.deleteById(id);
    }


}
