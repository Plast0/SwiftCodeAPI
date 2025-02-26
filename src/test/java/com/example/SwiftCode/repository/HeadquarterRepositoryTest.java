package com.example.SwiftCode.repository;

import com.example.SwiftCode.models.Headquarter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HeadquarterRepositoryTest {
    @Autowired
    private HeadquarterRepository headquarterRepository;

    @Test
    public void HeadquarterRepository_Save_SaveHeadquarterToDB(){
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

       Headquarter savedHeadquarter = headquarterRepository.save(headquarter);

        Assertions.assertThat(savedHeadquarter).isNotNull();
        Assertions.assertThat(savedHeadquarter.getId()).isGreaterThan(0);
    }
    @Test
    public void HeadquarterRepository_findBySwiftCode_ReturnHeadquarterNotNull(){
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        headquarterRepository.save(headquarter);
        Headquarter savedHeadquarter = headquarterRepository.findBySwiftCode(headquarter.getSwiftCode()).orElseThrow();

        Assertions.assertThat(savedHeadquarter).isNotNull();
        Assertions.assertThat(savedHeadquarter.getId()).isGreaterThan(0);
    }
    @Test
    public void HeadquarterRepository_findBySwiftCodeStartsWith_ReturnHeadquarterNotNull(){
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        headquarterRepository.save(headquarter);
        String trimmedSwiftCode = headquarter.getSwiftCode().substring(0,8);
        Headquarter savedHeadquarter = headquarterRepository.findBySwiftCodeStartsWith(trimmedSwiftCode);

        Assertions.assertThat(savedHeadquarter).isNotNull();
        Assertions.assertThat(savedHeadquarter.getId()).isGreaterThan(0);
    }
    @Test
    public void HeadquarterRepository_findByCountryISO2_ReturnHeadquartersNotNull(){
        Headquarter headquarter1 = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");
        Headquarter headquarter2 = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcod2XXX");

        headquarterRepository.save(headquarter1);
        headquarterRepository.save(headquarter2);
        List<Headquarter> savedHeadquarter = headquarterRepository.findAllByCountryISO2(headquarter1.getCountryISO2());

        Assertions.assertThat(savedHeadquarter).isNotNull();
        Assertions.assertThat(savedHeadquarter.size()).isEqualTo(2);
    }
    @Test
    public void HeadquarterRepository_delete_ReturnHeadquarterIsEmpty(){
        Headquarter headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");

        headquarterRepository.save(headquarter);
        headquarterRepository.deleteById(headquarter.getId());
        Optional<Headquarter> headquarterReturn = headquarterRepository.findById(headquarter.getId());

        Assertions.assertThat(headquarterReturn).isEmpty();
    }
}
