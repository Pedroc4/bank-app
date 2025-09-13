package es.pedro.corpas.bank_app.infrastructure.controller;

import es.pedro.corpas.bank_app.application.CategoryService;
import es.pedro.corpas.bank_app.domain.entity.CategoryEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.CategoryOutputDto;
import es.pedro.corpas.bank_app.infrastructure.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/")
    public ResponseEntity<List<CategoryOutputDto>> findAll() {
        List<CategoryEntity> categoryEntities = categoryService.findAll();
        return new ResponseEntity<>(categoryMapper.entityListToDtoList(categoryEntities), HttpStatus.OK);
    }
}
