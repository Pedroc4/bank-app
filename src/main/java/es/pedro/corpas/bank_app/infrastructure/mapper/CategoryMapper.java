package es.pedro.corpas.bank_app.infrastructure.mapper;

import es.pedro.corpas.bank_app.domain.entity.CategoryEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.CategoryOutputDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryOutputDto entityToDto(CategoryEntity category);

    List<CategoryOutputDto> entityListToDtoList(List<CategoryEntity> categoryEntities);

}
