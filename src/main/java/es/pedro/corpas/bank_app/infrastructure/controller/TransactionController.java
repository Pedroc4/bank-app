package es.pedro.corpas.bank_app.infrastructure.controller;

import es.pedro.corpas.bank_app.application.TransactionService;
import es.pedro.corpas.bank_app.application.UtilsService;
import es.pedro.corpas.bank_app.domain.entity.TransactionEntity;
import es.pedro.corpas.bank_app.infrastructure.controller.dto.TransactionOutputDto;
import es.pedro.corpas.bank_app.infrastructure.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {
    private final UtilsService utilsService;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping("/")
    public ResponseEntity<String> create() {
        utilsService.readExcel();
        String ok = "OK";
        return new ResponseEntity<>(ok, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<Page<TransactionOutputDto>> findAll(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TransactionEntity> transactionPage = transactionService.findAll(pageable, category);
        Page<TransactionOutputDto> dtoPage = transactionPage.map(transactionMapper::entityToDto);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionOutputDto> findById(@PathVariable Integer id) {
        TransactionOutputDto transactionOutputDto = transactionMapper.entityToDto(transactionService.findById(id));
        return new ResponseEntity<>(transactionOutputDto, HttpStatus.OK);
    }
}
