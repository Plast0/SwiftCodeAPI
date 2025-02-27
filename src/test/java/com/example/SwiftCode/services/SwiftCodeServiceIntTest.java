package com.example.SwiftCode.services;

import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.dto.HeadquarterDTO;
import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.exceptions.ItemNotFoundException;
import com.example.SwiftCode.models.Headquarter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Transactional
public class SwiftCodeServiceIntTest {

    @Autowired
    private SwiftCodeService swiftCodeService;

    @Autowired
    public SwiftCodeServiceIntTest(SwiftCodeService swiftCodeService){
        this.swiftCodeService = swiftCodeService;
    }

    @Test
    public void SwiftCodeService_findBySwiftCode_ReturnObjectWithSwiftCode(){
        HeadquarterDTO foundHeadquarter = (HeadquarterDTO) swiftCodeService.findBySwiftCode("BPBIBGSFXXX");

        Assertions.assertThat(foundHeadquarter).isNotNull();
    }

    @Test
    public void SwiftCodeService_findByCountryISO2Code_ReturnObjectsWithISO2Code(){
        String countryISO2 = "BG";
        CountryDTO foundCountry = swiftCodeService.findByCountryISO2Code(countryISO2);

        Assertions.assertThat(foundCountry).isNotNull();
        Assertions.assertThat(foundCountry.getCountryISO2()).isEqualTo(countryISO2);
    }

    @Test
    public void SwiftCodeService_setNewSwiftCodeOffice_ShouldSaveHeadquarter(){
        OfficeDTO officeDTO = new OfficeDTO(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        Object result = swiftCodeService.setNewSwiftCodeOffice(officeDTO);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isInstanceOf(Headquarter.class);

        swiftCodeService.deleteSwiftCodeData("swiftcodeXXX");
    }
    @Test
    public void SwiftCodeService_deleteSwiftCodeData_ShouldDeleteHeadquarter() {
        OfficeDTO officeDTO = new OfficeDTO(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swift1234XXX");
        swiftCodeService.setNewSwiftCodeOffice(officeDTO);

        boolean result = swiftCodeService.deleteSwiftCodeData("swift1234XXX");
        Assertions.assertThat(result).isTrue();
        Assertions.assertThatThrownBy(() -> swiftCodeService.findBySwiftCode("swift1234XXX"))
                .isInstanceOf(ItemNotFoundException.class)
                .hasMessageContaining("swift1234XXX number Swift code not found in database");
    }

}
