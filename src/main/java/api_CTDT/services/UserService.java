package api_CTDT.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.Account;
import api_CTDT.repositories.AccountRepository;

@Service
public class UserService {
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> findAll() {
		return accountRepository.findAll();
	}
}
