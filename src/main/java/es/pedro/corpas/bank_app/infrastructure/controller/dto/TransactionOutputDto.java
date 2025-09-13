package es.pedro.corpas.bank_app.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionOutputDto {
    private Integer id;
    private LocalDate date;
    private LocalDate valueDate;
    private String description;
    private String category;
    private Double amount;
    private Double balance;
}
