package com.example.SwiftCode.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class BranchDTO{
        @JsonPropertyOrder({"address","bankName","countryISO2","isHeadquarter","swiftCode"})
        String address;
        String bankName;
        String countryISO2;

        boolean isHeadquarter;
        String swiftCode;

        public BranchDTO() {
        }

        public BranchDTO(String address, String bankName, String countryISO2, boolean isHeadquarter, String swiftCode) {
                this.address = address;
                this.bankName = bankName;
                this.countryISO2 = countryISO2;
                this.isHeadquarter = isHeadquarter;
                this.swiftCode = swiftCode;
        }

        public String getAddress() {
                return address;
        }

        public String getBankName() {
                return bankName;
        }

        public String getCountryISO2() {
                return countryISO2;
        }
        @JsonGetter("isHeadquarter")
        public boolean isHeadquarter() {
                return isHeadquarter;
        }

        public String getSwiftCode() {
                return swiftCode;
        }
}
