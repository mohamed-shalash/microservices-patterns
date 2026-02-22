package org.shalash.eventsourcing.controller;


import lombok.RequiredArgsConstructor;
import org.shalash.eventsourcing.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping("/{id}/create")
    public String createAccount(@PathVariable String id) {
        accountService.createAccount(id);
        return "Account created: " + id;
    }

    @PostMapping("/{id}/deposit")
    public String deposit(@PathVariable String id, @RequestParam double amount) {
        accountService.deposit(id, amount);
        return "Deposited " + amount + " to " + id;
    }

    @PostMapping("/{id}/withdraw")
    public String withdraw(@PathVariable String id, @RequestParam double amount) {
        accountService.withdraw(id, amount);
        return "Withdrew " + amount + " from " + id;
    }

    @GetMapping("/{id}/balance")
    public String getBalance(@PathVariable String id) {
        double balance = accountService.getBalance(id);
        return "Balance of " + id + ": " + balance;
    }
}