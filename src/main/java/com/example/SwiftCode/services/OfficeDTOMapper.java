package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.models.Branch;
import com.example.SwiftCode.models.Headquarter;
import org.springframework.stereotype.Service;

@Service
public class OfficeDTOMapper {
    public Headquarter toHeadquarterOfficeMapper(OfficeDTO officeDTO){
        return new Headquarter(
                officeDTO.getAddress(),
                officeDTO.getBankName(),
                officeDTO.getCountryISO2(),
                officeDTO.getCountryName(),
                officeDTO.isHeadquarter(),
                officeDTO.getSwiftCode());
    }
    public Branch toBranchOfficeMapper(OfficeDTO officeDTO){
        return new Branch(
                officeDTO.getAddress(),
                officeDTO.getBankName(),
                officeDTO.getCountryISO2(),
                officeDTO.getCountryName(),
                officeDTO.isHeadquarter(),
                officeDTO.getSwiftCode());
    }
}
