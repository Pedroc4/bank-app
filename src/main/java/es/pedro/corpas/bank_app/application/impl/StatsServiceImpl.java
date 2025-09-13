package es.pedro.corpas.bank_app.application.impl;

import es.pedro.corpas.bank_app.application.StatsService;
import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.CategoryStatsDto;
import es.pedro.corpas.bank_app.infrastructure.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final TransactionRepository transactionRepository;

    public List<TransactionEntity> getTransactionsMonthly(int month, int year, String category) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate previousMonth = startOfMonth.minusMonths(1);

        LocalDate previousSalaryDate = transactionRepository.findSalaryDatesBetween(previousMonth, startOfMonth);

        LocalDate currentSalaryDate = transactionRepository.findSalaryDateForMonth(month, year)
                .orElse(LocalDate.of(year, month, startOfMonth.lengthOfMonth()));

        return transactionRepository.findMonthlyTransactions(previousSalaryDate, currentSalaryDate, category);
    }

    public List<CategoryStatsDto> getMonthlyStats(int month, int year) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate previousMonth = startOfMonth.minusMonths(1);

        LocalDate previousSalaryDate = transactionRepository.findSalaryDatesBetween(previousMonth, startOfMonth);

        Double lastSalary = transactionRepository.findSalaryAmounts(previousSalaryDate);

        List<TransactionEntity> transactions = getTransactionsMonthly(month, year, null);

        Map<String, Double> grouped = transactions.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(TransactionEntity::getAmount)
                ));

        List<CategoryStatsDto> stats = new java.util.ArrayList<>(grouped.entrySet().stream()
                .map(e -> new CategoryStatsDto(e.getKey(), e.getValue()))
                .toList());

        double totalSpent = transactions.stream()
                .mapToDouble(TransactionEntity::getAmount)
                .sum();

        stats.add(new CategoryStatsDto("AHORRO", lastSalary + totalSpent));

        return stats;
    }

}
