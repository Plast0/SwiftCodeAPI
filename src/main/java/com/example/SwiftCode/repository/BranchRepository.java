package com.example.SwiftCode.repository;

import com.example.SwiftCode.models.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    @Query("SELECT b FROM Branch b WHERE b.swiftCode = ?1")
    Optional<Branch> findBySwiftCode(String swiftCode);
    List<Branch> findAllByCountryISO2(String countryISO2Code);
    @Query("SELECT b FROM Branch b WHERE b.swiftCode LIKE CONCAT(:swiftCode, '%')")
    Optional<Branch> findBySwiftCodeStartsWith(String swiftCode);

}
