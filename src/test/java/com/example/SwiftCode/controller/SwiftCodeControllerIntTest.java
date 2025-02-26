package com.example.SwiftCode.controller;

import com.example.SwiftCode.models.Headquarter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class SwiftCodeControllerIntTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    private Headquarter headquarter;

    @BeforeEach
    public void init(){
        headquarter = new Headquarter("address",
                "bankName",
                "countryISO2",
                "countryName",
                true,
                "swiftcodeXXX");
    }
    @AfterEach
    public void tearDown(){
        headquarter =null;
    }

    @Test
    public void SwiftCodeController_getBySwiftCode_ReturnObjectWithSwiftCode() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/swift-codes/BPBIBGSFXXX"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void SwiftCodeController_getByCountryCode_ReturnObjectWithCountryISO2Code() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/v1/swift-codes/country/BG"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void SwiftCodeController_addNewSwiftCodeEntry_ShouldSaveOffice() throws Exception{
    try{
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/swift-codes")
                        .content(objectMapper.writeValueAsString(headquarter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Swift Code entry has been created"));

    } finally {
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/swift-codes/{swift-code}", headquarter.getSwiftCode()));
    }
    }

    @Test
    public void SwiftCodeController_deleteSwiftCodeData_ReturnString() throws Exception {
        String swiftCode = "swiftcodeXXX";
    try{
        mockMvc
                .perform(MockMvcRequestBuilders.post("/v1/swift-codes")
                        .content(objectMapper.writeValueAsString(headquarter))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    } finally {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/v1/swift-codes/{swift-code}", swiftCode))
                .andExpect(status().isOk())
                .andExpect(content().string("Swift Code " + swiftCode + " has been deleted"));
    }

    }

}
