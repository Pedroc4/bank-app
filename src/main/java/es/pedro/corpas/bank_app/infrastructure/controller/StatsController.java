package es.pedro.corpas.bank_app.infrastructure.controller;

import es.pedro.corpas.bank_app.application.StatsService;
import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.CategoryStatsDto;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.TransactionOutputDto;
import es.pedro.corpas.bank_app.infrastructure.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class StatsController {
    private final StatsService statsService;
    private final TransactionMapper transactionMapper;

    @GetMapping("/monthly")
    public ResponseEntity<List<TransactionOutputDto>> getMonthlyTransactions(
            @RequestParam int month, @RequestParam int year,
            @RequestParam(required = false) String category) {
        List<TransactionEntity> stats = statsService.getTransactionsMonthly(month, year, category);
        return ResponseEntity.ok(transactionMapper.entityListToDtoList(stats));
    }

    @GetMapping("/stats/monthly")
    public ResponseEntity<List<CategoryStatsDto>> getMonthlyStats(@RequestParam int month, @RequestParam int year) {
        List<CategoryStatsDto> stats = statsService.getMonthlyStats(month, year);
        return ResponseEntity.ok(stats);
    }
}
