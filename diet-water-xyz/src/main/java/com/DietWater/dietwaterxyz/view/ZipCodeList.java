package com.DietWater.dietwaterxyz.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZipCodeList {
    private List<String> zip_codes = new ArrayList<>();

    public List<String> getZip_codes() {
        return zip_codes;
    }

    public void setZip_codes(List<String> zip_codes) {
        this.zip_codes = zip_codes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ZipCodeList)) return false;
        ZipCodeList that = (ZipCodeList) o;
        return Objects.equals(getZip_codes(), that.getZip_codes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZip_codes());
    }

    @Override
    public String toString() {
        return "ZipCodeList{" +
                "zip_code=" + zip_codes +
                '}';
    }
}
