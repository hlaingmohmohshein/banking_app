package net.javaguides.bankingapp.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import net.javaguides.bankingapp.dto.AccountDto;
import net.javaguides.bankingapp.entity.Account;
import net.javaguides.bankingapp.mapper.AccountMapper;
import net.javaguides.bankingapp.resposity.AccountResposity;
import net.javaguides.bankingapp.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	private AccountResposity accountResposity;

	public AccountServiceImpl(AccountResposity accountResposity) {
		this.accountResposity = accountResposity;

	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountResposity.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountResposity.findById(id)
				.orElseThrow(() -> new RuntimeException("Account Does not Exists"));
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountResposity.findById(id)
				.orElseThrow(() -> new RuntimeException("Account Does not exists!"));
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountResposity.save(account);

		return AccountMapper.mapToAccountDto(savedAccount);

	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountResposity.findById(id)
				.orElseThrow(() -> new RuntimeException("Account Does not exists!"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient amount");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount= accountResposity.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		// TODO Auto-generated method stub
		List<Account> accounts = accountResposity.findAll();
		
		return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account= accountResposity
					.findById(id)
					.orElseThrow(()->new RuntimeException("Account does not exist in Database."));
		
		accountResposity.deleteById(id);
	}

}
