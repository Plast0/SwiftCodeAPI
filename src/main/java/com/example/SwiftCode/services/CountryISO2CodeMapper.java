package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.BranchDTO;
import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.models.Branch;
import com.example.SwiftCode.models.Headquarter;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryISO2CodeMapper  {
    public CountryDTO toCountryDTO(List<Headquarter> headquarters, List<Branch> branches){
        List<BranchDTO> mappedOffices = headquarters.stream().map(o -> {
            BranchDTO branchDTO = new BranchDTO(
                    o.getAddress(),
                    o.getBankName(),
                    o.getCountryISO2(),
                    o.isHeadquarter(),
                    o.getSwiftCode());
            return branchDTO;
        }).collect(Collectors.toList());
        List<BranchDTO> mappedBranches = branches.stream()
                .map(b -> {
                    BranchDTO branchDTO = new BranchDTO(
                            b.getAddress(),
                            b.getBankName(),
                            b.getCountryISO2(),
                            b.isHeadquarter(),
                            b.getSwiftCode());
                    return branchDTO;
                }).collect(Collectors.toList());
        mappedOffices.addAll(mappedBranches);
        return new CountryDTO(
                headquarters.getFirst().getCountryISO2(),
                headquarters.getFirst().getCountryName(),
                mappedOffices
        );
    }

}
