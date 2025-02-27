package com.example.SwiftCode.controllers;

import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.exceptions.ItemNotFoundException;
import com.example.SwiftCode.models.Headquarter;
import com.example.SwiftCode.services.SwiftCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeService swiftCodeService;
    @Autowired
    public SwiftCodeController(SwiftCodeService swiftCodeService) {
        this.swiftCodeService = swiftCodeService;
    }

    @GetMapping("/{swift-code}")
    public Object getBySwiftCode(@PathVariable("swift-code") String swift_Code){
        return swiftCodeService.findBySwiftCode(swift_Code);
        }
    @GetMapping("/country/{countryISO2code}")
    public CountryDTO getByCountryCode(@PathVariable("countryISO2code") String countryCode){
        return swiftCodeService.findByCountryISO2Code(countryCode);
    }
    @PostMapping
    public ResponseEntity<Object> addNewSwiftCodeEntry(@Valid @RequestBody OfficeDTO officeDTO){
       Object savedSwiftCode = swiftCodeService.setNewSwiftCodeOffice(officeDTO);
        Map<String, Object> message = new HashMap<>();
        message.put("message", "Swift Code entry has been created");
        message.put("data", savedSwiftCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    @DeleteMapping("/{swift-code}")
    public ResponseEntity<String> deleteSwiftCodeData(@PathVariable("swift-code")String swift_Code) {
        boolean deleted =  swiftCodeService.deleteSwiftCodeData(swift_Code);
        if(!deleted){
            throw new ItemNotFoundException("No such Swift code has been found: " + swift_Code);
        }else {
            return ResponseEntity.ok("Swift Code " + swift_Code  + " has been deleted");
        }
    }
}

