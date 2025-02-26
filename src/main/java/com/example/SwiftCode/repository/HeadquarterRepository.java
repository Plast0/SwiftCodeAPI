package com.example.SwiftCode.repository;

import com.example.SwiftCode.models.Headquarter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HeadquarterRepository extends JpaRepository<Headquarter, Long> {
    @Query("SELECT h FROM Headquarter h WHERE h.swiftCode = ?1")
    Optional<Headquarter> findBySwiftCode(String swiftCode);
    @Query("SELECT h FROM Headquarter h WHERE h.swiftCode LIKE CONCAT(:swiftCode, '%')")
    Headquarter findBySwiftCodeStartsWith(String swiftCode);
    List<Headquarter> findAllByCountryISO2(String countryISO2Code);
}

