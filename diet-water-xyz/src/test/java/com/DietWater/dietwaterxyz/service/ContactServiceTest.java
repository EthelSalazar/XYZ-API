package com.DietWater.dietwaterxyz.service;

import com.DietWater.dietwaterxyz.dao.ContactDao;
import com.DietWater.dietwaterxyz.model.Contact;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {

    private ContactDao dao;
    private ContactService service;
    private ContactView contactView;
    private ZipcodeService zipcodeService;

    @Before
    public void setUp() throws Exception {
        dao = mock(ContactDao.class);
        zipcodeService = mock(ZipcodeService.class);
        service = new ContactService(dao, zipcodeService);
        setUpContactDao();
        setUpZipCodeService();
    }



    @Test
    public void shouldSaveContactSuccessfully() {
        //Arrange
        contactView = new ContactView();
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

        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        contactView.setContactId(contactViewSaved.getContactId());

        //Assert
        assertEquals(contactView,contactViewSaved);

    }

    @Test(expected = IllegalArgumentException.class)
    public void saveContactWithZipCodeInvalid() {
        //Arrange
        contactView = new ContactView();
        contactView.setFirstName("Ethel");
        contactView.setLastName("Salazar");
        contactView.setEmail("test@test.com");
        contactView.setPhoneNumber("332 345 3434");
        contactView.setStreet("Greenleaf");
        contactView.setApt("3");
        contactView.setCity("Chicago");
        contactView.setState("IL");
        contactView.setCountry("US");
        contactView.setZipcode("80897");

        //Act
        ContactView contactViewSaved = service.saveContact(contactView);

    }

    @Test
    public void updateContact() {
    }

    @Test
    public void findContactById() {
    }

    @Test
    public void findContacts() {
    }

    @Test
    public void deleteContact() {
    }


    private void setUpContactDao() {
        Contact contact = new Contact();
        contact.setFirstName("Ethel");
        contact.setLastName("Salazar");
        contact.setEmail("test@test.com");
        contact.setPhoneNumber("332 345 3434");
        contact.setStreet("Greenleaf");
        contact.setApt("3");
        contact.setCity("Chicago");
        contact.setState("IL");
        contact.setCountry("US");
        contact.setZipcode("60626");

        Contact contact1 = new Contact();
        contact1.setContactId(1);
        contact1.setFirstName("Ethel");
        contact1.setLastName("Salazar");
        contact1.setEmail("test@test.com");
        contact1.setPhoneNumber("332 345 3434");
        contact1.setStreet("Greenleaf");
        contact1.setApt("3");
        contact1.setCity("Chicago");
        contact1.setState("IL");
        contact1.setCountry("US");
        contact1.setZipcode("60626");

        Optional<Contact> contactOptional = Optional.of(contact1);

        Contact contactUpdated = new Contact();
        contactUpdated.setContactId(1);
        contactUpdated.setFirstName("Ethel");
        contactUpdated.setLastName("Salazar");
        contactUpdated.setEmail("test@test.com");
        contactUpdated.setPhoneNumber("456 789 5656");
        contactUpdated.setStreet("Greenleaf");
        contactUpdated.setApt("8");
        contactUpdated.setCity("Chicago");
        contactUpdated.setState("IL");
        contactUpdated.setCountry("US");
        contactUpdated.setZipcode("60626");

        Contact contact2 = new Contact();
        contact2.setContactId(2);
        contact2.setFirstName("Miryam");
        contact2.setLastName("Gonzalez");
        contact2.setEmail("mgonzalez@test.com");
        contact2.setPhoneNumber("58 456 567 5656");
        contact2.setStreet("Miranda");
        contact2.setApt("54");
        contact2.setCity("Barquisimeto");
        contact2.setState("Lara");
        contact2.setCountry("Venezuela");
        contact2.setZipcode("3020");

        List<Contact> contactListFirstAndZipCode = new ArrayList<>();
        contactListFirstAndZipCode.add(contact1);

        List<Contact> contactListLastName = new ArrayList<>();
        contactListLastName.add(contact2);

        Contact contactWrongZipCode = new Contact();
        contactWrongZipCode.setFirstName("Ethel");
        contactWrongZipCode.setLastName("Salazar");
        contactWrongZipCode.setEmail("test@test.com");
        contactWrongZipCode.setPhoneNumber("332 345 3434");
        contactWrongZipCode.setStreet("Greenleaf");
        contactWrongZipCode.setApt("3");
        contactWrongZipCode.setCity("Chicago");
        contactWrongZipCode.setState("IL");
        contactWrongZipCode.setCountry("US");
        contactWrongZipCode.setZipcode("80897");

        doReturn(contact1).when(dao).save(contact);
        doReturn(contactUpdated).when(dao).save(contactUpdated);
        doReturn(contactOptional).when(dao).findById(1);
        doReturn(contactListFirstAndZipCode).when(dao).findAllByFirstName("Ethel");
        doReturn(contactListFirstAndZipCode).when(dao).findAllByFirstName("606026");
        doReturn(contactListLastName).when(dao).findAllByLastName("Gonzalez");

    }

    private void setUpZipCodeService() {

        doReturn(true).when(zipcodeService).validZipcode("Chicago","IL", "60626");
        doThrow(IllegalArgumentException.class).when(zipcodeService).validZipcode("Chicago","IL", "80897");
        doThrow(IllegalArgumentException.class).when(zipcodeService).validZipcode("Chicago","", "80897");
    }
}