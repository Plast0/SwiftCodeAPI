package com.example.SwiftCode.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Headquarter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String address;

    private String bankName;

    private String countryISO2;

    private String countryName;
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;

    private String swiftCode;
    @OneToMany(mappedBy = "headquarter", cascade = CascadeType.ALL)
    private List<Branch> branches = new ArrayList<>();

    public Headquarter() {
    }

    public Headquarter(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
        this.address = address;
        this.bankName = bankName;
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.isHeadquarter = isHeadquarter;
        this.swiftCode = swiftCode;
    }

    @Override
    public String toString() {
        return "Headquarter{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", bankName='" + bankName + '\'' +
                ", countryISO2='" + countryISO2 + '\'' +
                ", countryName='" + countryName + '\'' +
                ", isHeadquarter=" + isHeadquarter +
                ", swiftCode='" + swiftCode + '\'' +
                ", branches=" + branches +
                '}';
    }

    public void setBranches(Branch branch) {
        branches.add(branch);
        branch.setHeadquarter(this);
    }

    public void setBranchesList(List<Branch> branches){
        for (Branch branch: branches
             ) {
            this.setBranches(branch);
        }
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @JsonGetter("isHeadquarter")
    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
