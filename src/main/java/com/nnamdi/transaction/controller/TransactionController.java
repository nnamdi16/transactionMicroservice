package com.nnamdi.transaction.controller;

import com.nnamdi.transaction.exception.TransactionNotFoundException;
import com.nnamdi.transaction.model.Transaction;
import com.nnamdi.transaction.model.TransactionModelAssembler;
import com.nnamdi.transaction.payload.request.TransactionRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final TransactionModelAssembler transactionModelAssembler;


    public TransactionController(TransactionRepository transactionRepository, TransactionModelAssembler transactionModelAssembler) {
        this.transactionRepository = transactionRepository;
        this.transactionModelAssembler = transactionModelAssembler;
    }


    @GetMapping("/all")
    public CollectionModel<EntityModel<Transaction>> all() {
        List<EntityModel<Transaction>> accounts = transactionRepository.findAll().stream().map(transactionModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(accounts, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TransactionController.class).all()).withSelfRel());
    }



    @PostMapping("/all")
    public ResponseEntity<?> registerTransaction( @Valid @RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction(transactionRequest.getTransactionType(),transactionRequest.getAmount(),transactionRequest.getTransactionDescription(),transactionRequest.getAccountId());
        EntityModel<Transaction> entityModel = transactionModelAssembler.toModel(transactionRepository.save(transaction));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    //Single item

    @GetMapping("/transaction/{id}")
    public EntityModel<Transaction> getTransaction(@PathVariable Long id){
        Transaction account = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionModelAssembler.toModel(account);
    }




}
