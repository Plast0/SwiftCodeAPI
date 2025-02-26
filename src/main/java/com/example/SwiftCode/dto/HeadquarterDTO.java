package com.example.SwiftCode.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record HeadquarterDTO(
        String address,
        String bankName,
        String countryISO2,
        String countryName,
        @JsonProperty("isHeadquarter")
        boolean isHeadquarter,
        String swiftCode,
        @JsonProperty("branches")
        List<BranchDTO> branchesDTO
        )
        {



        }
