package com.DietWater.dietwaterxyz.dao;

import com.DietWater.dietwaterxyz.model.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactDaoTest {

    @Autowired
    private ContactDao dao;
    private Contact contact;
    private Contact contact2;

    @Before
    public void setUp() {
        contact = new Contact();
        contact2 = new Contact();
        dao.deleteAll();

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
    }

    @Test
    public void shouldSaveNewContact(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldGiveErrorByEmailRepeated(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        Contact contactEmailRepeated = new Contact();
        contactEmailRepeated.setFirstName("Charlott");
        contactEmailRepeated.setLastName("Salazar");
        contactEmailRepeated.setEmail("test@test.com");
        contactEmailRepeated.setPhoneNumber("332 345 6754");
        contactEmailRepeated.setStreet("Greenleaf");
        contactEmailRepeated.setApt("3");
        contactEmailRepeated.setCity("Chicago");
        contactEmailRepeated.setState("IL");
        contactEmailRepeated.setCountry("US");
        contactEmailRepeated.setZipcode("60626");

         dao.save(contactEmailRepeated);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldGiveErrorByEmailNull(){
        contact.setEmail(null);
        dao.save(contact);
    }

    @Test
    public void shouldFindContactById(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        assertEquals(contact,dao.findById(contact.getContactId()).get());

    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowExceptionByWrongId(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        dao.findById(contact.getContactId()+500).get();

    }

    @Test
    public void shouldUpdateContact(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        contact.setPhoneNumber("321 567 8989");
        Contact contactUpdated = dao.save(contact);
        assertEquals(contactUpdated,contact);

    }

    @Test
    public void shouldFindContactByFirstName(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> firstNameContactList = new ArrayList<>();
        firstNameContactList.add(contact);

        assertEquals(dao.findAllByFirstName("Ethel"),firstNameContactList);
    }

    @Test
    public void shouldFindContactByLastName(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> lastNameContactList = new ArrayList<>();
        lastNameContactList.add(contact2);

        assertEquals(dao.findAllByLastName("Gonzalez"),lastNameContactList);
    }

    @Test
    public void shouldFindContactByZipCode(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> zipCodeContactList = new ArrayList<>();
        zipCodeContactList.add(contact);

        assertEquals(dao.findAllByZipcode("60626"),zipCodeContactList);
    }

    @Test
    public void shouldReturnEmptyListByWrongParams(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        assertEquals(new ArrayList<>(), dao.findAllByZipcode("45678"));
    }

    @Test
    public void shouldFindContactByFirstNameAndLastName(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> firstNameContactList = new ArrayList<>();
        firstNameContactList.add(contact);

        assertEquals(firstNameContactList, dao.findAllByFirstNameAndLastName("Ethel", "Salazar"));
    }

    @Test
    public void shouldFindContactByFirstNameAndZipcode(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> firstNameContactList = new ArrayList<>();
        firstNameContactList.add(contact);

        assertEquals(firstNameContactList, dao.findAllByFirstNameAndZipcode("Ethel", "60626"));
    }

    @Test
    public void shouldFindContactByLastNameAndZipcode(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> firstNameContactList = new ArrayList<>();
        firstNameContactList.add(contact);

        assertEquals(firstNameContactList, dao.findAllByLastNameAndZipcode("Salazar", "60626"));
    }

    @Test
    public void shouldFindContactByFirstAndLastNameAndZipcode(){
        dao.save(contact);
        dao.save(contact2);

        List<Contact> mainContactList = new ArrayList<>();
        mainContactList.add(contact);
        mainContactList.add(contact2);

        assertEquals(dao.findAll(),mainContactList);

        List<Contact> firstNameContactList = new ArrayList<>();
        firstNameContactList.add(contact);

        assertEquals(firstNameContactList, dao.findAllByFirstNameAndLastNameAndZipcode("Ethel", "Salazar","60626"));
    }

    @Test
    public void shouldDeleteContactSuccessfully(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        dao.deleteById(contact.getContactId());
        assertNull(dao.findById(contact.getContactId()).orElse(null));

    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldDeleteContact(){
        Contact contactAfterSave = dao.save(contact);
        contact.setContactId(contactAfterSave.getContactId());
        assertEquals(contactAfterSave,contact);

        dao.deleteById(contact.getContactId()+500);

    }

}