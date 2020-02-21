package com.DietWater.dietwaterxyz.controller;

import com.DietWater.dietwaterxyz.service.ContactService;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @Autowired
    ContactService service;

    @RequestMapping(value = "/contact/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ContactView saveContact(@RequestBody ContactView newContact){
        return service.saveContact(newContact);
    }
}
