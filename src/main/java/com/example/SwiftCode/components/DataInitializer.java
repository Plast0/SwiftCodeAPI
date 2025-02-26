package com.example.SwiftCode.components;

import com.example.SwiftCode.services.SwiftCodeService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class DataInitializer implements ApplicationRunner {
    private final SwiftCodeService swiftCodeService;

    public DataInitializer(SwiftCodeService swiftCodeService) {
        this.swiftCodeService = swiftCodeService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        ExcelReaderFromFile reader = new ExcelReaderFromFile(swiftCodeService);
        try (InputStream excelStream = getExcelFileStream()) {
            reader.saveToDatabaseFromExcelFile(excelStream);
        } catch (Exception e) {
            throw new RuntimeException("Error while reading an Excel file!", e);
        }
    }

    private InputStream getExcelFileStream() throws Exception {
        String excelFilePath = System.getenv("EXCEL_FILE_PATH");
        if (excelFilePath != null && !excelFilePath.isEmpty()) {
            Path path = Paths.get(excelFilePath);
            if (Files.exists(path)) {
                return Files.newInputStream(path);
            }
        }
        Path localPath = Paths.get("data", "Interns_2025_SWIFT_CODES.xlsx");
        if (Files.exists(localPath)) {
            return Files.newInputStream(localPath);
        }
        InputStream resourceStream = getClass().getClassLoader()
                .getResourceAsStream("Interns_2025_SWIFT_CODES.xlsx");
        if (resourceStream != null) {
            return resourceStream;
        }

        throw new RuntimeException("Excel file not found in ‘resources/’ or ‘data/’!");
    }
}
