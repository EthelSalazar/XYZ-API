package com.DietWater.dietwaterxyz.utils;

import com.DietWater.dietwaterxyz.view.ZipCodeList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "zipcodeapi", url = "https://www.zipcodeapi.com")
public interface ZipCodeFeign {
    @GetMapping(value = "/rest/{apiKey}/{responseFormat}/{city}/{state}")
    public ZipCodeList getZipCodeByCityAndState(@PathVariable String apiKey,
                                                @PathVariable String responseFormat,
                                                @PathVariable String city,
                                                @PathVariable String state);
}
