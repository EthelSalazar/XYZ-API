package com.DietWater.dietwaterxyz.controller;

import com.DietWater.dietwaterxyz.service.ContactService;
import com.DietWater.dietwaterxyz.view.ContactView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {

    @MockBean
    ContactService service;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();
    ContactView contactView;
    ContactView contactViewSaved;


    @Before
    public void setUp() throws Exception {
        contactView = new ContactView();
        contactViewSaved = new ContactView();
        setUpServicio();
    }



    @Test
    public void saveContact() throws Exception{
        String inputJson = mapper.writeValueAsString(contactView);
        String outputJson = mapper.writeValueAsString(contactViewSaved);

        mockMvc.perform(
                post("/contact/")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(outputJson));

    }


    private void setUpServicio() {
        contactView.setFirstName("Ethel");
        contactView.setLastName("Salazar");
        contactView.setEmail("test@test.com");
        contactView.setPhoneNumber("332 345 3434");
        contactView.setStreet("Greenleaf");
        contactView.setApt("3");
        contactView.setCity("Chicago");
        contactView.setState("IL");
        contactView.setCountry("US");
        contactView.setZipcode("60626");

        contactViewSaved.setContactId(1);
        contactViewSaved.setFirstName("Ethel");
        contactViewSaved.setLastName("Salazar");
        contactViewSaved.setEmail("test@test.com");
        contactViewSaved.setPhoneNumber("332 345 3434");
        contactViewSaved.setStreet("Greenleaf");
        contactViewSaved.setApt("3");
        contactViewSaved.setCity("Chicago");
        contactViewSaved.setState("IL");
        contactViewSaved.setCountry("US");
        contactViewSaved.setZipcode("60626");

        when(service.saveContact(contactView)).thenReturn(contactViewSaved);
    }
}