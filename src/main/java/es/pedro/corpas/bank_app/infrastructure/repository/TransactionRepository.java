package es.pedro.corpas.bank_app.infrastructure.repository;

import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    @Query("""
                SELECT t FROM TransactionEntity t
                WHERE t.date BETWEEN :start AND :end
                  AND t.category.name <> 'SUELDO'
                  AND (:category IS NULL OR LOWER(t.category.name) = LOWER(:category))
            """)
    List<TransactionEntity> findMonthlyTransactions(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("category") String category);

    Page<TransactionEntity> findByCategoryName(String category, Pageable pageable);

    @Query("SELECT t.date FROM TransactionEntity t " +
            "WHERE t.category.name = 'SUELDO' " +
            "AND t.date BETWEEN :start AND :end " +
            "ORDER BY t.date DESC")
    LocalDate findSalaryDatesBetween(@Param("start") LocalDate start,
                                           @Param("end") LocalDate end);


    @Query("SELECT t.date FROM TransactionEntity t " +
            "WHERE t.category.name = 'SUELDO' AND MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year ORDER BY t.date ASC")
    Optional<LocalDate> findSalaryDateForMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT t.amount FROM TransactionEntity t " +
            "WHERE t.date BETWEEN :start AND :start " +
            "AND t.category.name = 'SUELDO'")
    Double findSalaryAmounts(@Param("start") LocalDate start);

}
