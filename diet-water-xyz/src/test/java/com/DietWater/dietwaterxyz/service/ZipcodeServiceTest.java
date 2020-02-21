package com.DietWater.dietwaterxyz.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZipcodeServiceTest {

    @Autowired
    ZipcodeService zipcodeService;

    String city;
    String state;
    String zipCode;
    boolean result;

    @Test
    public void shouldReturnTrueForAValidZipCode() {
        //Arrange
        city = "Chicago";
        state = "IL";
        zipCode = "60626";

        //Act
        result = zipcodeService.validZipcode(city, state, zipCode);

        //Assert
        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseForAnInvalidZipCode() {
        //Arrange
        city = "Chicago";
        state = "IL";
        zipCode = "80897";

        //Act
        result = zipcodeService.validZipcode(city, state, zipCode);

        //Assert
        assertFalse(result);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnExceptionByWrongParams() {
        //Arrange
        city = "Caracas";
        state = "";
        zipCode = "3020";

        //Act
        result = zipcodeService.validZipcode(city, state, zipCode);

    }
}