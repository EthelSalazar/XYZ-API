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
}
