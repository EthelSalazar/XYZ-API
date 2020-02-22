package com.DietWater.dietwaterxyz.service;

import com.DietWater.dietwaterxyz.view.ZipCodeList;
import com.DietWater.dietwaterxyz.utils.ZipCodeFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZipcodeService {
    @Value("${api.key.zipcode}")
    private String apiKeyZipcode;

    @Value("${api.response.format.zipcode}")
    private String apiResponseFormatZipcode;

    @Autowired
    private ZipCodeFeign zipCodeFeign;

    public boolean validZipcode(String city, String state, String zipCode){
        ZipCodeList zipCodeList;

        try{
            zipCodeList = zipCodeFeign.getZipCodeByCityAndState(apiKeyZipcode, apiResponseFormatZipcode,city, state);
        }catch (Exception e){
            throw new IllegalArgumentException("API problems please check properties");
        }

        if(zipCodeList.getZip_codes().isEmpty()){
            throw new IllegalArgumentException("The combination City and State does not return ZipCode values");
        }
        return zipCodeList.getZip_codes().contains(zipCode);
    }
}
