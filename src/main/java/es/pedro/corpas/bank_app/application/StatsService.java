package es.pedro.corpas.bank_app.application;

import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.CategoryStatsDto;

import java.util.List;

public interface StatsService {
    List<TransactionEntity> getTransactionsMonthly(int month, int year, String category);

    List<CategoryStatsDto> getMonthlyStats(int month, int year);
}
