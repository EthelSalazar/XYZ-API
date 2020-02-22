package com.DietWater.dietwaterxyz.controller;

import com.DietWater.dietwaterxyz.service.ContactService;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    ContactService service;

    @RequestMapping(value = "/contact/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ContactView saveContact(@RequestBody ContactView newContact){
        return service.saveContact(newContact);
    }

    @RequestMapping(value = "/contact/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ContactView updateContact(@RequestBody ContactView contactView){
        return service.updateContact(contactView);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ContactView findContactById(@PathVariable Integer id){
        return service.findContactById(id);
    }

    @RequestMapping(value = "/contact/search/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ContactView> findContact( @RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String lastName,
                                          @RequestParam(required = false) String zipCode){
        return service.findContacts(firstName, lastName, zipCode);
    }

    @RequestMapping(value = "/contact/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactById(@PathVariable Integer id){
        service.deleteContact(id);
    }
}
