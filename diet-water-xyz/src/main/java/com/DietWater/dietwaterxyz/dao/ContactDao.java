package com.DietWater.dietwaterxyz.dao;

import com.DietWater.dietwaterxyz.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDao extends JpaRepository<Contact,Integer> {

    List<Contact> findAllByZipcode(String zipcode);
    List<Contact> findAllByFirstName(String firstName);
    List<Contact> findAllByLastName(String lastName);
    List<Contact> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Contact> findAllByFirstNameAndZipcode(String firstName, String zipcode);
    List<Contact> findAllByLastNameAndZipcode(String lastName, String zipcode);
    List<Contact> findAllByFirstNameAndLastNameAndZipcode(String firstName, String lastName, String zipcode);

}
