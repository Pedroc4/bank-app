package es.pedro.corpas.bank_app.application;

import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    TransactionEntity save(TransactionEntity transaction);

    TransactionEntity findById(Integer id);

    Page<TransactionEntity> findAll(Pageable pageable, String category);
}
