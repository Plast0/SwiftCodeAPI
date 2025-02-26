package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.models.Branch;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class BranchDTOMapper implements Function<Branch, OfficeDTO> {

    @Override
    public OfficeDTO apply(Branch branch) {
        return new OfficeDTO(
                branch.getAddress(),
                branch.getBankName(),
                branch.getCountryISO2(),
                branch.getCountryName(),
                branch.isHeadquarter(),
                branch.getSwiftCode());
    }
}
