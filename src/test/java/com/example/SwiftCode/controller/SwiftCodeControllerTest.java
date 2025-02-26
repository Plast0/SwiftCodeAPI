package com.example.SwiftCode.controller;

import com.example.SwiftCode.controllers.SwiftCodeController;
import com.example.SwiftCode.dto.BranchDTO;
import com.example.SwiftCode.dto.CountryDTO;
import com.example.SwiftCode.dto.OfficeDTO;
import com.example.SwiftCode.models.Headquarter;
import com.example.SwiftCode.services.SwiftCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


@WebMvcTest(controllers = SwiftCodeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SwiftCodeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SwiftCodeService swiftCodeService;
    @Autowired
    private ObjectMapper objectMapper;
    private OfficeDTO officeDTO;
    private Headquarter headquarter;
    private CountryDTO countryDTO;
    private BranchDTO branchDTO;
    @BeforeEach
    public void init(){
        officeDTO = new OfficeDTO("address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");
        headquarter = new Headquarter(
                "address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");
        branchDTO = new BranchDTO(
                "address",
                "bankName",
                "countryISO2",
                true,
                "swiftcodeXXX");
        countryDTO = new CountryDTO(
                "countryISO2",
                "countryName",
                null
        );

    }

    @Test
    public void SwiftCodeController_addNewSwiftCodeEntry_ShouldSaveOffice() throws Exception{
        given(swiftCodeService.setNewSwiftCodeOffice(any(OfficeDTO.class))).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/v1/swift-codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeDTO)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void SwiftCodeController_getBySwiftCode_ReturnObjectWithSwiftCode() throws Exception{
        when(swiftCodeService.findBySwiftCode("swiftcodeXXX")).thenReturn(headquarter);

        ResultActions response = mockMvc.perform(get("/v1/swift-codes/swiftcodeXXX")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(headquarter)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void SwiftCodeController_getByCountryCode_ReturnObjectWithCountryISO2Code() throws Exception{
        List<BranchDTO> branches = new ArrayList<>();
        branches.add(branchDTO);
         countryDTO.setSwiftCodes(branches);
        when(swiftCodeService.findByCountryISO2Code("countryISO2")).thenReturn(countryDTO);

        ResultActions response = mockMvc.perform(get("/v1/swift-codes/countryISO2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(countryDTO)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void SwiftCodeController_deleteSwiftCodeData_ReturnString() throws Exception {
        boolean deleted = true;
        when(swiftCodeService.deleteSwiftCodeData("swiftcodeXXX")).thenReturn(deleted);

        ResultActions response = mockMvc.perform(delete("/v1/swift-codes/swiftcodeXXX")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
