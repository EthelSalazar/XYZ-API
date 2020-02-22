package com.DietWater.dietwaterxyz.service;

import com.DietWater.dietwaterxyz.dao.ContactDao;
import com.DietWater.dietwaterxyz.model.Contact;
import com.DietWater.dietwaterxyz.view.ContactView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {

    private ContactDao dao;
    private ContactService service;
    private ContactView contactView;
    private ContactView contactView2;
    private ZipcodeService zipcodeService;

    @Before
    public void setUp() {
        dao = mock(ContactDao.class);
        zipcodeService = mock(ZipcodeService.class);
        service = new ContactService(dao, zipcodeService);
        setUpContactDao();
        setUpZipCodeService();

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

        contactView2 = new ContactView();
        contactView2.setFirstName("Miryam");
        contactView2.setLastName("Gonzalez");
        contactView2.setEmail("mgonzalez@test.com");
        contactView2.setPhoneNumber("58 456 567 5656");
        contactView2.setStreet("Miranda");
        contactView2.setApt("54");
        contactView2.setCity("Barquisimeto");
        contactView2.setState("Lara");
        contactView2.setCountry("Venezuela");
        contactView2.setZipcode("3020");

    }

    @Test
    public void shouldSaveContactSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        contactView.setContactId(contactViewSaved.getContactId());

        //Assert
        assertEquals(contactView,contactViewSaved);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnExceptionByZipCodeInvalid() {
        //Arrange
        contactView.setZipcode("80897");

        //Act
        service.saveContact(contactView);

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldReturnExceptionByEmailRepeated() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        contactView.setContactId(contactViewSaved.getContactId());

        //Assert
        assertEquals(contactView,contactViewSaved);

        //Arrange
        ContactView contactViewEmailRepeated = new ContactView();
        contactViewEmailRepeated.setFirstName("Charlott");
        contactViewEmailRepeated.setLastName("Salazar");
        contactViewEmailRepeated.setEmail("test@test.com");
        contactViewEmailRepeated.setPhoneNumber("332 345 5678");
        contactViewEmailRepeated.setStreet("Greenleaf");
        contactViewEmailRepeated.setApt("3");
        contactViewEmailRepeated.setCity("Chicago");
        contactViewEmailRepeated.setState("IL");
        contactViewEmailRepeated.setCountry("US");
        contactViewEmailRepeated.setZipcode("60626");

        //Act
        service.saveContact(contactViewEmailRepeated);
    }

    @Test
    public void updateContact() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        contactView.setContactId(contactViewSaved.getContactId());

        //Assert
        assertEquals(contactView,contactViewSaved);

        //Arrange
        contactView.setPhoneNumber("456 789 5656");
        contactView.setApt("8");

        //Act
        contactViewSaved = service.updateContact(contactView);

        //Assert
        assertEquals(contactView,contactViewSaved);
    }

    @Test
    public void shouldFindContactByIdSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        contactView.setContactId(contactViewSaved.getContactId());

        //Assert
        assertEquals(contactView,contactViewSaved);

        //Act
        ContactView contactById = service.findContactById(contactView.getContactId());
        //Assert
        assertEquals(contactView,contactById);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnNullByFindingWrongId() {
        //Assert
        service.findContactById(2);
    }

    @Test
    public void findSavedContactsSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        ContactView contactViewSaved2 = service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);
        contactViewList.add(contactViewSaved2);

        //Act
        List<ContactView> contactViewListResult = service.findContacts(null,null,null);

        //Assert
        assertEquals(contactViewList, contactViewListResult);
    }

    @Test
    public void findContactsByFirstNameSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);
        //Assert
        assertEquals(contactViewList, service.findContacts("Ethel",null,null));
    }

    @Test
    public void findContactsByLastNameSuccessfully() {
        //Act
        service.saveContact(contactView);
        ContactView contactViewSaved = service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);

        //Assert
        assertEquals(contactViewList, service.findContacts(null,"Gonzalez",null));
    }


    @Test
    public void findContactsByZipCodeSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);
        //Assert
        assertEquals(contactViewList, service.findContacts(null,null,"60626"));
    }


    @Test
    public void findContactsByFirstAndLastNameSuccessfully() {
        //Act
        service.saveContact(contactView);
        ContactView contactViewSaved = service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);

        //Assert
        assertEquals(contactViewList, service.findContacts("Miryam","Gonzalez",null));
    }

    @Test
    public void findContactsByFirstNameAndZipCodeSuccessfully() {
        //Act
        ContactView contactViewSaved = service.saveContact(contactView);
        service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);
        //Assert
        assertEquals(contactViewList, service.findContacts("Ethel",null,"60626"));

    }

    @Test
    public void findContactsByLastNameAndZipCodeSuccessfully() {
        //Act
        service.saveContact(contactView);
        ContactView contactViewSaved = service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);

        //Assert
        assertEquals(contactViewList, service.findContacts(null,"Gonzalez","3020"));
    }

    @Test
    public void findContactsByFirstAndLastNameAndZipCodeSuccessfully() {
        //Act
        service.saveContact(contactView);
        ContactView contactViewSaved = service.saveContact(contactView2);

        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();
        contactViewList.add(contactViewSaved);

        //Assert
        assertEquals(contactViewList, service.findContacts("Miryam","Gonzalez","3020"));
    }

    @Test
    public void ShouldReturnEmptyListByWrongParams() {
        //Arrange
        List<ContactView> contactViewList = new ArrayList<>();

        //Assert
        assertEquals(contactViewList, service.findContacts("Jose","Molina","60634"));
    }

    @Test
    public void deleteContact() {
        //Act
        service.saveContact(contactView);
        ContactView contactViewSaved = service.saveContact(contactView2);

        service.deleteContact(contactViewSaved.getContactId());

        verify(dao, times(1)).deleteById(contactViewSaved.getContactId());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void shouldReturnExceptionWhenDeleteWithWrongId() {
        service.deleteContact(500);

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

        Contact contact2New = new Contact();
        contact2New.setFirstName("Miryam");
        contact2New.setLastName("Gonzalez");
        contact2New.setEmail("mgonzalez@test.com");
        contact2New.setPhoneNumber("58 456 567 5656");
        contact2New.setStreet("Miranda");
        contact2New.setApt("54");
        contact2New.setCity("Barquisimeto");
        contact2New.setState("Lara");
        contact2New.setCountry("Venezuela");
        contact2New.setZipcode("3020");

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

        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact1);
        contactList.add(contact2);

        List<Contact> contactListFirstAndZipCode = new ArrayList<>();
        contactListFirstAndZipCode.add(contact1);

        List<Contact> contactListLastName = new ArrayList<>();
        contactListLastName.add(contact2);

        Contact contactEmailRepeated = new Contact();
        contactEmailRepeated.setFirstName("Charlott");
        contactEmailRepeated.setLastName("Salazar");
        contactEmailRepeated.setEmail("test@test.com");
        contactEmailRepeated.setPhoneNumber("332 345 5678");
        contactEmailRepeated.setStreet("Greenleaf");
        contactEmailRepeated.setApt("3");
        contactEmailRepeated.setCity("Chicago");
        contactEmailRepeated.setState("IL");
        contactEmailRepeated.setCountry("US");
        contactEmailRepeated.setZipcode("60626");

        doReturn(contact1).when(dao).save(contact);
        doReturn(contact2).when(dao).save(contact2New);
        doReturn(contactUpdated).when(dao).save(contactUpdated);
        doThrow(DataIntegrityViolationException.class).when(dao).save(contactEmailRepeated);

        doReturn(contactOptional).when(dao).findById(1);
        doThrow(NoSuchElementException.class).when(dao).findById(2);

        doReturn(contactList).when(dao).findAll();

        doReturn(contactListFirstAndZipCode).when(dao).findAllByFirstName("Ethel");
        doReturn(contactListFirstAndZipCode).when(dao).findAllByZipcode("60626");
        doReturn(contactListLastName).when(dao).findAllByLastName("Gonzalez");
        doReturn(contactListLastName).when(dao).findAllByFirstNameAndLastName("Miryam","Gonzalez");
        doReturn(contactListFirstAndZipCode).when(dao).findAllByFirstNameAndZipcode("Ethel","60626");
        doReturn(contactListLastName).when(dao).findAllByLastNameAndZipcode("Gonzalez","3020");
        doReturn(contactListLastName).when(dao).findAllByFirstNameAndLastNameAndZipcode("Miryam","Gonzalez","3020");
        doReturn(new ArrayList<>()).when(dao).findAllByFirstNameAndLastNameAndZipcode("Jose","Molina","60634");

        doThrow(EmptyResultDataAccessException.class).when(dao).deleteById(500);

    }

    private void setUpZipCodeService() {

        doReturn(true).when(zipcodeService).validZipcode("Chicago","IL", "60626");
        doReturn(true).when(zipcodeService).validZipcode("Barquisimeto","Lara", "3020");
        doThrow(IllegalArgumentException.class).when(zipcodeService).validZipcode("Chicago","IL", "80897");
        doThrow(IllegalArgumentException.class).when(zipcodeService).validZipcode("Chicago","", "80897");
    }
}