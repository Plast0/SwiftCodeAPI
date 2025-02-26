package com.example.SwiftCode.components;

import com.example.SwiftCode.models.Branch;
import com.example.SwiftCode.models.Headquarter;
import com.example.SwiftCode.services.SwiftCodeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Component
public class ExcelReaderFromFile {

    private final SwiftCodeService swiftCodeService;

    @Autowired
    public ExcelReaderFromFile(SwiftCodeService swiftCodeService){
        this.swiftCodeService = swiftCodeService;
    }

    public void saveToDatabaseFromExcelFile(InputStream excelFileStream) {
        List<String[]> excelData = readFromExcelFile(excelFileStream);
        List<Headquarter> headquarters = new ArrayList<>();
        List<Branch> branches = new ArrayList<>();

        for (String[] row : excelData) {
            if (row[1].contains("XXX")) {
                Headquarter headquarter = new Headquarter(row[4], row[3], row[0], row[6], true, row[1]);
                headquarters.add(headquarter);
            } else {
                Branch branch = new Branch(row[4], row[3], row[0], row[6], false, row[1]);
                branches.add(branch);
            }
        }

        swiftCodeService.saveDataFromList(headquarters, branches);
    }
    private List<String[]> readFromExcelFile(InputStream excelFileStream) {
        List<String[]> data = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(excelFileStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                data.add(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading Excel file", e);
        }
        return data;
    }


}
