package com.example.SwiftCode.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CountryDTO {
    String countryISO2;
    String countryName;
    @JsonProperty("swiftCodes")
    List<BranchDTO> swiftCodes;

    public CountryDTO() {
    }

    public CountryDTO(String countryISO2, String countryName, List<BranchDTO> swiftCodes) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = swiftCodes;
    }

    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<BranchDTO> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<BranchDTO> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}

