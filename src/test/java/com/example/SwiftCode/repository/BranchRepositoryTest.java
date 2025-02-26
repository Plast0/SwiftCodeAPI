package com.example.SwiftCode.repository;

import com.example.SwiftCode.models.Branch;
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
public class BranchRepositoryTest {
    @Autowired
    private BranchRepository branchRepository;
    @Test
    public void BranchRepository_Save_SaveBranchToDB(){
        Branch branch = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode123");

        Branch savedBranch = branchRepository.save(branch);

        Assertions.assertThat(savedBranch).isNotNull();
        Assertions.assertThat(savedBranch.getId()).isGreaterThan(0);
    }
    @Test
    public void BranchRepository_findBySwiftCode_ReturnBranchNotNull(){
        Branch branch = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode123");

        branchRepository.save(branch);
        Branch savedBranch = branchRepository.findBySwiftCode(branch.getSwiftCode()).orElseThrow();

        Assertions.assertThat(savedBranch).isNotNull();
        Assertions.assertThat(savedBranch.getId()).isGreaterThan(0);
        Assertions.assertThat(savedBranch.getSwiftCode()).isEqualTo(branch.getSwiftCode());
    }
    @Test
    public void BranchRepository_findBySwiftCodeStartsWith_ReturnBranchNotNull(){
        Branch branch = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode123");

        branchRepository.save(branch);
        String trimmedSwiftCode = branch.getSwiftCode().substring(0,8);
        Branch savedBranch = branchRepository.findBySwiftCodeStartsWith(trimmedSwiftCode).orElseThrow();

        Assertions.assertThat(savedBranch).isNotNull();
        Assertions.assertThat(savedBranch.getId()).isGreaterThan(0);
        Assertions.assertThat(savedBranch.getSwiftCode()).isEqualTo(branch.getSwiftCode());
    }
    @Test
    public void BranchRepository_findByCountryISO2_ReturnBranchesNotNull(){
        Branch branch1 = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode123");
        Branch branch2 = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode456");

        branchRepository.save(branch1);
        branchRepository.save(branch2);
        List<Branch> savedBranches = branchRepository.findAllByCountryISO2(branch1.getCountryISO2());

        Assertions.assertThat(savedBranches).isNotNull();
        Assertions.assertThat(savedBranches.size()).isEqualTo(2);
        Assertions.assertThat(savedBranches.get(0).getCountryISO2()).isEqualTo(branch1.getCountryISO2());
        Assertions.assertThat(savedBranches.get(0).getCountryISO2()).isEqualTo(branch2.getCountryISO2());
    }

    @Test
    public void BranchRepository_delete_ReturnBranchIsEmpty(){
        Branch branch = new Branch(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                false,
                "swiftcode123");

        branchRepository.save(branch);
        branchRepository.deleteById(branch.getId());

        Optional<Branch> branchReturn = branchRepository.findById(branch.getId());

        Assertions.assertThat(branchReturn).isEmpty();
    }
}
