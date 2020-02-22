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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private ContactView contactView;
    private ContactView contactViewSaved;
    private ContactView contactViewSaved2;
    private ContactView contactViewWrongZip;
    private ContactView contactViewEmailRepeated;
    private ContactView contactViewUpdated;
    private List<ContactView> contactViewList;
    private List<ContactView> contactViewListContact1;
    private List<ContactView> contactViewListContact2;


    @Before
    public void setUp() {
        contactView = new ContactView();
        contactViewSaved = new ContactView();
        contactViewSaved2 = new ContactView();
        contactViewWrongZip = new ContactView();
        contactViewEmailRepeated = new ContactView();
        contactViewUpdated = new ContactView();
        contactViewList = new ArrayList<>();
        contactViewListContact1 = new ArrayList<>();
        contactViewListContact2 = new ArrayList<>();
        setUpServicio();
    }

    @Test
    public void shouldSaveContact() throws Exception{
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

    @Test
    public void shouldThrowExceptionByWrongZipCode () throws Exception{
        String inputJson = mapper.writeValueAsString(contactViewWrongZip);

        mockMvc.perform(
                post("/contact/")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Invalid ZipCode")));

    }

    @Test
    public void shouldThrowExceptionByRepeatedEmail () throws Exception{
        String inputJson = mapper.writeValueAsString(contactViewEmailRepeated);

        mockMvc.perform(
                post("/contact/")
                        .content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Email repeated")));

    }

    @Test
    public void shouldUpdateContact() throws Exception{
        String inputOutputJson = mapper.writeValueAsString(contactViewUpdated);

        mockMvc.perform(
                put("/contact/")
                        .content(inputOutputJson)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(inputOutputJson));

    }
    @Test
    public void shouldFindContactById() throws Exception {
        String outputJson = mapper.writeValueAsString(contactViewSaved);
        mockMvc.perform(
                get("/contact/{id}", contactViewSaved.getContactId())
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldThrowExceptionByWrongId() throws Exception{
        mockMvc.perform(
                get("/contact/{id}", contactViewSaved.getContactId()+500)
        ).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Id not found")));
    }

    @Test
    public void shouldReturnContactListWithOutParams() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewList);

        mockMvc.perform(
                get("/contact/search/")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturnContactListByFirstName() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact2);

        mockMvc.perform(
                get("/contact/search/")
                .param("firstName", "Miryam")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturnContactListByLastName() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact1);

        mockMvc.perform(
                get("/contact/search/")
                        .param("lastName", "Salazar")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturnContactListByZipcode() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact2);

        mockMvc.perform(
                get("/contact/search/")
                        .param("zipCode", "3020")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturnContactListByFirstAndLastName() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact1);

        mockMvc.perform(
                get("/contact/search/")
                        .param("firstName", "Ethel")
                        .param("lastName", "Salazar")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturnContactListByFirstAndZipCode() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact2);

        mockMvc.perform(
                get("/contact/search/")
                        .param("firstName", "Miryam")
                        .param("zipCode", "3020")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }
    @Test
    public void shouldReturnContactListByLastNameAndZipCode() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact2);

        mockMvc.perform(
                get("/contact/search/")
                        .param("lastName", "Gonzalez")
                        .param("zipCode", "3020")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void shouldReturnContactListByFirstAndLastNameAndZipCode() throws Exception{
        String outputJson = mapper.writeValueAsString(contactViewListContact1);

        mockMvc.perform(
                get("/contact/search/")
                        .param("firstName", "Ethel")
                        .param("lastName", "Salazar")
                .param("zipCode", "60626")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));
    }

    @Test
    public void ShouldReturnEmptyListByWrongParams() throws Exception{
        //Arrange
        List<ContactView> emptyList = new ArrayList<>();

        String outputJson = mapper.writeValueAsString(emptyList);

        mockMvc.perform(
                get("/contact/search/")
                        .param("firstName", "Jose")
                        .param("lastName", "Molina")
                        .param("zipCode", "60634")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputJson));

    }

    @Test
    public void shouldDeleteContact() throws Exception{

        mockMvc.perform(
                delete("/contact/{id}", contactViewSaved.getContactId())
        ).andDo(print())
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteContact(contactViewSaved.getContactId());
    }

    @Test
    public void shouldReturnExceptionWhenDeleteWithWrongId() throws Exception{

        mockMvc.perform(
                delete("/contact/{id}", contactViewSaved.getContactId()+500)
        ).andDo(print())
                .andExpect(status().isNotFound());
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

        contactViewWrongZip.setFirstName("Ethel");
        contactViewWrongZip.setLastName("Salazar");
        contactViewWrongZip.setEmail("test@test.com");
        contactViewWrongZip.setPhoneNumber("332 345 3434");
        contactViewWrongZip.setStreet("Greenleaf");
        contactViewWrongZip.setApt("3");
        contactViewWrongZip.setCity("Chicago");
        contactViewWrongZip.setState("IL");
        contactViewWrongZip.setCountry("US");
        contactViewWrongZip.setZipcode("80890");

        contactViewEmailRepeated.setFirstName("Charlott");
        contactViewEmailRepeated.setLastName("Salazar");
        contactViewEmailRepeated.setEmail("test@test.com");
        contactViewEmailRepeated.setPhoneNumber("332 345 6789");
        contactViewEmailRepeated.setStreet("Greenleaf");
        contactViewEmailRepeated.setApt("8");
        contactViewEmailRepeated.setCity("Chicago");
        contactViewEmailRepeated.setState("IL");
        contactViewEmailRepeated.setCountry("US");
        contactViewEmailRepeated.setZipcode("60626");

        contactViewUpdated.setContactId(1);
        contactViewUpdated.setFirstName("Ethel");
        contactViewUpdated.setLastName("Salazar");
        contactViewUpdated.setEmail("test@test.com");
        contactViewUpdated.setPhoneNumber("456 456 7899");
        contactViewUpdated.setStreet("Greenleaf");
        contactViewUpdated.setApt("8");
        contactViewUpdated.setCity("Chicago");
        contactViewUpdated.setState("IL");
        contactViewUpdated.setCountry("US");
        contactViewUpdated.setZipcode("60640");

        contactViewSaved2.setContactId(2);
        contactViewSaved2.setFirstName("Miryam");
        contactViewSaved2.setLastName("Gonzalez");
        contactViewSaved2.setEmail("mgonzalez@test.com");
        contactViewSaved2.setPhoneNumber("58 456 567 5656");
        contactViewSaved2.setStreet("Miranda");
        contactViewSaved2.setApt("54");
        contactViewSaved2.setCity("Barquisimeto");
        contactViewSaved2.setState("Lara");
        contactViewSaved2.setCountry("Venezuela");
        contactViewSaved2.setZipcode("3020");

        contactViewList.add(contactViewSaved);
        contactViewList.add(contactViewSaved2);

        contactViewListContact1.add(contactViewSaved);

        contactViewListContact2.add(contactViewSaved2);

        when(service.saveContact(contactView)).thenReturn(contactViewSaved);
        when(service.updateContact(contactViewUpdated)).thenReturn(contactViewUpdated);
        when(service.findContactById(contactViewSaved.getContactId())).thenReturn(contactViewSaved);
        when(service.findContacts(null,null,null)).thenReturn(contactViewList);
        when(service.findContacts("Miryam",null,null)).thenReturn(contactViewListContact2);
        when(service.findContacts(null,"Salazar",null)).thenReturn(contactViewListContact1);
        when(service.findContacts(null,null,"3020")).thenReturn(contactViewListContact2);
        when(service.findContacts("Miryam",null,"3020")).thenReturn(contactViewListContact2);
        when(service.findContacts("Ethel","Salazar",null)).thenReturn(contactViewListContact1);
        when(service.findContacts(null,"Gonzalez","3020")).thenReturn(contactViewListContact2);
        when(service.findContacts("Ethel","Salazar","60626")).thenReturn(contactViewListContact1);
        doNothing().when(service).deleteContact(contactViewSaved.getContactId());


        given(service.saveContact(contactViewWrongZip)).willThrow(new IllegalArgumentException("Invalid ZipCode"));
        given(service.saveContact(contactViewEmailRepeated)).willThrow(new DataIntegrityViolationException("Email repeated"));
        given(service.findContactById(contactViewSaved.getContactId() + 500)).willThrow(new NoSuchElementException("Id not found"));
        doThrow(new EmptyResultDataAccessException("Id not found", 0)).when(service).deleteContact(contactViewSaved.getContactId()+500);

    }
}