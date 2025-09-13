package es.pedro.corpas.bank_app.application;

import es.pedro.corpas.bank_app.domain.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> findAll();
}
