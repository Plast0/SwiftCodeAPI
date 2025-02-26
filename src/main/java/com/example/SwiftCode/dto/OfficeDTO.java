package com.example.SwiftCode.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OfficeDTO {
    @JsonPropertyOrder({"address","bankName","countryISO2","countryName","isHeadquarter","swiftCode"})
    @NotBlank(message = "Address cannot be empty")
    String address;
    @NotBlank(message = "Bank Name cannot be empty")
    String bankName;
    @NotBlank(message = "Country ISO2 Code cannot be empty")
    String countryISO2;
    @NotBlank(message = "The country name cannot be empty")
    String countryName;
    @NotNull(message = "Define whether the office is the headquarter")
    @JsonProperty("isHeadquarter")
    boolean isHeadquarter;
    @NotBlank(message = "Swift Code cannot be empty")
    String swiftCode;

    public OfficeDTO() {
    }
    public OfficeDTO(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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
    @JsonGetter("isHeadquarter")
    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
