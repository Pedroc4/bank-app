package es.pedro.corpas.bank_app.infrastructure.mapper;

import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.TransactionOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "category.name", target = "category")
    TransactionOutputDto entityToDto(TransactionEntity transaction);

    List<TransactionOutputDto> entityListToDtoList(List<TransactionEntity> transactionEntities);

}
