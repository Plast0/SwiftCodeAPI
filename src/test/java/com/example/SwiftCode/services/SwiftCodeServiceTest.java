package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.dto.HeadquarterDTO;
import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.models.Branch;
import com.example.SwiftCode.models.Headquarter;
import com.example.SwiftCode.repository.BranchRepository;
import com.example.SwiftCode.repository.HeadquarterRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SwiftCodeServiceTest {
    @Mock
    private HeadquarterRepository headquarterRepository;
    @Mock
    private BranchRepository branchRepository;
    @InjectMocks
    private SwiftCodeService swiftCodeService;
    @Mock
    private HeadquarterDTOMapper headquarterDTOMapper;
    @Mock
    private CountryISO2CodeMapper countryISO2CodeMapper;
    @Mock
    private OfficeDTOMapper officeDTOMapper;

    @Test
    public void SwiftCodeService_findBySwiftCode_ReturnObjectWithSwiftCode(){
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");
        HeadquarterDTO headquarterDTO = new HeadquarterDTO(headquarter.getAddress(), headquarter.getBankName(),headquarter.getCountryISO2(),headquarter.getCountryName(),headquarter.isHeadquarter(),headquarter.getSwiftCode(), null );

        when(headquarterRepository.findBySwiftCode("swiftcodeXXX")).thenReturn(Optional.of(headquarter));
        when(headquarterDTOMapper.apply(any(Headquarter.class))).thenReturn(headquarterDTO);

        HeadquarterDTO foundHeadquarter = (HeadquarterDTO) swiftCodeService.findBySwiftCode("swiftcodeXXX");

        Assertions.assertThat(foundHeadquarter).isNotNull();
    }

    @Test
    public void SwiftCodeService_findByCountryISO2Code_ReturnObjectsWithISO2Code(){
        String countryISO2 = "US";
        List<Headquarter> headquarters = List.of(new Headquarter());
        List<Branch> branches = List.of(new Branch());
        CountryDTO expectedCountryDTO = new CountryDTO();

        when(headquarterRepository.findAllByCountryISO2(countryISO2)).thenReturn(headquarters);
        when(branchRepository.findAllByCountryISO2(countryISO2)).thenReturn(branches);
        when(countryISO2CodeMapper.toCountryDTO(headquarters, branches)).thenReturn(expectedCountryDTO);

        CountryDTO savedCountry = swiftCodeService.findByCountryISO2Code(countryISO2);

        Assertions.assertThat(savedCountry).isEqualTo(expectedCountryDTO);

    }
    @Test
    public void SwiftCodeService_setNewSwiftCodeOffice_ShouldSaveHeadquarter(){
        Headquarter headquarter = new Headquarter();
        OfficeDTO officeDTO = new OfficeDTO(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        when(officeDTOMapper.toHeadquarterOfficeMapper(officeDTO)).thenReturn(headquarter);
        swiftCodeService.setNewSwiftCodeOffice(officeDTO);
        Mockito.verify(headquarterRepository, times(1)).save(headquarter);
    }
    @Test
    public void SwiftCodeService_deleteSwiftCodeData_ShouldDeleteHeadquarter() {
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        headquarter.setId(1L);

        when(headquarterRepository.findBySwiftCode("swiftcodeXXX")).thenReturn(Optional.of(headquarter));
        when(branchRepository.findBySwiftCode("swiftcodeXXX")).thenReturn(Optional.empty());

        swiftCodeService.deleteSwiftCodeData("swiftcodeXXX");

        verify(headquarterRepository, times(1)).deleteById(1L);
        verify(branchRepository, never()).deleteById(anyLong());
    }

}
