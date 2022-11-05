package com.aninfo.controller;

import com.aninfo.dto.AccountCreationRequest;
import com.aninfo.dto.AccountCreationResponse;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class BankController {

    @Autowired
    private BankService bankService;


    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccount(@RequestBody AccountCreationRequest request) {
        bankService.createAccount(request.getBalance());
    }

    @PostMapping("/accounts/promo")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAccountWithPromo(@RequestBody AccountCreationRequest request) {
        bankService.createAccountWithPromo(request.getBalance());
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
        Transaction transaction = bankService.getTransaction(id);
        System.out.println(transaction.toString());
        return ResponseEntity.of(Optional.of(transaction));
    }

    @GetMapping("/accounts")
    public Collection<Account> getAccounts() {
        return bankService.getAccounts();
    }

    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        bankService.deleteTransaction(id);
    }

    @GetMapping("/accounts/{cbu}")
    public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
        Optional<Account> accountOptional = bankService.findById(cbu);
        return ResponseEntity.of(accountOptional);
    }

    @PutMapping("/accounts/{cbu}")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
        Optional<Account> accountOptional = bankService.findById(cbu);
        if (!accountOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        account.setCbu(cbu);
        bankService.save(account);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/accounts/{cbu}")
    public void deleteAccount(@PathVariable Long cbu) {
        bankService.deleteById(cbu);
    }

    @PutMapping("/accounts/{cbu}/withdraw")
    public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
        return bankService.withdraw(cbu, sum);
    }

    @PutMapping("/accounts/{cbu}/deposit")
    public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
        return bankService.deposit(cbu, sum);
    }

}
