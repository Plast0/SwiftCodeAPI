package com.example.SwiftCode.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarterId")
    private Headquarter headquarter;

    public Branch(){}
    public Branch( String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", bankName='" + bankName + '\'' +
                ", countryISO2='" + countryISO2 + '\'' +
                ", isHeadquarter=" + isHeadquarter +
                ", swiftCode='" + swiftCode + '\'' +
                '}';
    }

    public void setHeadquarter(Headquarter headquarter) {
        this.headquarter = headquarter;
    }

    public Long getId() {
        return id;
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

    public String getCountryName() {
        return countryName;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }
    public String getSwiftCode() {
        return swiftCode;
    }

}
