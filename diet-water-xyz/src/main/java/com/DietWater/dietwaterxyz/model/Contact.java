package com.DietWater.dietwaterxyz.model;

import com.DietWater.dietwaterxyz.view.ContactView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String apt;
    private String city;
    private String state;
    private String country;
    private String zipcode;

    public Contact() { }

    public Contact(String firstName, String lastName, String email, String phoneNumber, String street, String apt, String city, String state, String country, String zipcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.street = street;
        this.apt = apt;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipcode = zipcode;
    }

    public static Contact fromContactView(ContactView contactView) {
        return new Contact(
                contactView.getFirstName(),
                contactView.getLastName(),
                contactView.getEmail(),
                contactView.getPhoneNumber(),
                contactView.getStreet(),
                contactView.getApt(),
                contactView.getCity(),
                contactView.getState(),
                contactView.getCountry(),
                contactView.getZipcode()
        );
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApt() {
        return apt;
    }

    public void setApt(String apt) {
        this.apt = apt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getContactId(), contact.getContactId()) &&
                Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getLastName(), contact.getLastName()) &&
                Objects.equals(getEmail(), contact.getEmail()) &&
                Objects.equals(getPhoneNumber(), contact.getPhoneNumber()) &&
                Objects.equals(getStreet(), contact.getStreet()) &&
                Objects.equals(getApt(), contact.getApt()) &&
                Objects.equals(getCity(), contact.getCity()) &&
                Objects.equals(getState(), contact.getState()) &&
                Objects.equals(getCountry(), contact.getCountry()) &&
                Objects.equals(getZipcode(), contact.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getStreet(), getApt(), getCity(), getState(), getCountry(), getZipcode());
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", apt='" + apt + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }
}
