package es.pedro.corpas.bank_app.application.impl;

import es.pedro.corpas.bank_app.application.TransactionService;
import es.pedro.corpas.bank_app.application.UtilsService;
import es.pedro.corpas.bank_app.domain.entity.CategoryEntity;
import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilsServiceImpl implements UtilsService {
    private final CategoryRepository categoryRepository;
    private final TransactionService transactionService;
    private static final String OTRO = "OTRO";
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public void readExcel() {
        String excelFilePath = "movimientos.xls";
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new HSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);

            boolean primeraFila = true;
            for (Row row : sheet) {
                if (primeraFila) {
                    primeraFila = false;
                    continue;
                }

                if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK)
                    continue;

                if (row.getCell(0).getStringCellValue().contains("Movimientos de cuenta"))
                    continue;

                if (row.getCell(0).getStringCellValue().contains("fecha"))
                    continue;

                String dateStr = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                String valueDateStr = row.getCell(2).getStringCellValue();
                Double amount = row.getCell(3).getNumericCellValue();
                Double balance = row.getCell(4).getNumericCellValue();

                LocalDate date = parseDate(dateStr);
                LocalDate valueDate = parseDate(valueDateStr);

                CategoryEntity category = assignCategory(description);

                TransactionEntity transaction = new TransactionEntity();
                transaction.setDate(date);
                transaction.setValueDate(valueDate);
                transaction.setDescription(description.trim());
                transaction.setCategory(category);
                transaction.setAmount(amount);
                transaction.setBalance(balance);
                transactionService.save(transaction);
            }

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public CategoryEntity assignCategory(String concepto) {
        if (concepto == null || concepto.isBlank())
            return categoryRepository.findByName(OTRO).orElse(null);

        String lower = concepto.toLowerCase();
        CategoryEntity result = null;

        for (CategoryEntity cat : categoryRepository.findAll()) {
            for (String keyword : cat.getKeywords()) {
                if (lower.contains(keyword)) {
                    result = cat;
                    break;
                }
            }
            if (result != null) {
                break;
            }
        }

        if (result == null) {
            result = categoryRepository.findByName(OTRO).orElse(null);
        }

        return result;
    }

    private static LocalDate parseDate(String date) {
        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            fecha = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            log.error(e.getLocalizedMessage());
        }
        return fecha;
    }

}
