package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.model.TransactionType;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private static final Double promoStartingCredit = 500d;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Account createAccount(Account request) {
        return accountRepository.save(new Account(request.getBalance()));
    }

    public Account createAccountWithPromo(Account request) {
        return accountRepository.save(new Account(request.getBalance(), promoStartingCredit));
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Long cbu, Double sum) {
        Account account = accountRepository.findAccountByCbu(cbu);

        if (account.getBalance() < sum) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        Transaction transaction = new Transaction(TransactionType.WITHDRAW, sum);
        account.setBalance(account.getBalance() - sum);
        account.addTransaction(transaction);
        accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account deposit(Long cbu, Double sum) {

        if (sum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }

        Account account = accountRepository.findAccountByCbu(cbu);
        if (account.getPromoCreditLeft() > 0) {
            Double extra = Math.min(promoStartingCredit, sum * 0.1d);
            if (account.getPromoCreditLeft() - extra >= 0) {
                account.decPromoAccumulated(extra);
                sum += extra;
            }
        }

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, sum);

        account.setBalance(account.getBalance() + sum);
        account.addTransaction(transaction);
        accountRepository.save(account);

        return account;
    }

    public List<Transaction> getTransactionsFromAccount(Long cbu) {
        return accountRepository.findAccountByCbu(cbu).getTransactionList();
    }

    public Transaction getTransaction(Long id) {
        return transactionRepository.findAccountById(id);
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
