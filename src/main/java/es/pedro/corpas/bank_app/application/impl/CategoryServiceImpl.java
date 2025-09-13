package es.pedro.corpas.bank_app.application.impl;

import es.pedro.corpas.bank_app.application.CategoryService;
import es.pedro.corpas.bank_app.domain.entity.CategoryEntity;
import es.pedro.corpas.bank_app.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }
}
