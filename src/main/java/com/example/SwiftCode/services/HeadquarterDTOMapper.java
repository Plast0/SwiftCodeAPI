package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.BranchDTO;
import com.example.SwiftCode.dto.HeadquarterDTO;
import com.example.SwiftCode.models.Headquarter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class HeadquarterDTOMapper implements Function<Headquarter, HeadquarterDTO> {
    @Override
    public HeadquarterDTO apply(Headquarter headquarter) {
        List<BranchDTO> mappedBranches = headquarter.getBranches().stream()
                .map(b -> {
                    BranchDTO branchDTO = new BranchDTO(
                            b.getAddress(),
                            b.getBankName(),
                            b.getCountryISO2(),
                            b.isHeadquarter(),
                            b.getSwiftCode());
                    return branchDTO;
                }).collect(Collectors.toList());
        return new HeadquarterDTO(
                headquarter.getAddress(),
                headquarter.getBankName(),
                headquarter.getCountryISO2(),
                headquarter.getCountryName(),
                headquarter.isHeadquarter(),
                headquarter.getSwiftCode(),
                mappedBranches
                );
    }
}
