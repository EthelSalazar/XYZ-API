package com.DietWater.dietwaterxyz.utils;

import com.DietWater.dietwaterxyz.view.ZipCodeList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "zipcodeapi", url = "https://www.zipcodeapi.com")
public interface ZipCodeFeign {
    @RequestMapping(value = "/rest/{apiKey}/{responseFormat}/{city}/{state}", method = RequestMethod.GET)
    public ZipCodeList getZipCodeByCityAndState(@PathVariable String apiKey,
                                                @PathVariable String responseFormat,
                                                @PathVariable String city,
                                                @PathVariable String state);
}
