package com.DietWater.dietwaterxyz.view;

import com.DietWater.dietwaterxyz.model.Contact;

import java.util.Objects;

public class ContactView {
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

    public ContactView() {
    }

    public ContactView(Integer contactId, String firstName, String lastName, String email, String phoneNumber, String street, String apt, String city, String state, String country, String zipcode) {
        this.contactId = contactId;
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
        if (!(o instanceof ContactView)) return false;
        ContactView that = (ContactView) o;
        return Objects.equals(getContactId(), that.getContactId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getApt(), that.getApt()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getState(), that.getState()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getZipcode(), that.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContactId(), getFirstName(), getLastName(), getEmail(), getPhoneNumber(), getStreet(), getApt(), getCity(), getState(), getCountry(), getZipcode());
    }

    @Override
    public String toString() {
        return "ContactView{" +
                "contactId=" + contactId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", street='" + street + '\'' +
                ", apt='" + apt + '\'' +
                ", city='" + city + '\'' +
                ", State='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipcode='" + zipcode + '\'' +
                '}';
    }


    public static ContactView fromContact(Contact contact) {
        return new ContactView(
                contact.getContactId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getStreet(),
                contact.getApt(),
                contact.getCity(),
                contact.getState(),
                contact.getCountry(),
                contact.getZipcode()
        );
    }
}
