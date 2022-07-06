package com.example.walletservice.repositories;

import com.example.walletservice.models.PlayerAccount;
import org.springframework.data.repository.CrudRepository;

public interface PlayerAccountRepository extends CrudRepository<PlayerAccount, Long> {

}
