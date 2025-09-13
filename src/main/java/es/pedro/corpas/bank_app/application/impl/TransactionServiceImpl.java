package es.pedro.corpas.bank_app.application.impl;

import es.pedro.corpas.bank_app.application.TransactionService;
import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionEntity save(TransactionEntity transaction) {
        return transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public TransactionEntity findById(Integer id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<TransactionEntity> findAll(Pageable pageable, String category) {
        if (category != null && !category.isEmpty()) {
            return transactionRepository.findByCategoryName(category, pageable);
        } else {
            return transactionRepository.findAll(pageable);
        }
    }
}
