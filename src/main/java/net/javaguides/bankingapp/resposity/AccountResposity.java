package net.javaguides.bankingapp.resposity;

import org.springframework.data.jpa.repository.JpaRepository;

import net.javaguides.bankingapp.entity.Account;

public interface AccountResposity extends JpaRepository<Account, Long>{

}
