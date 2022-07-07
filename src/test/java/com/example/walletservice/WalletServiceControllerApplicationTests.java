package com.example.walletservice;

import com.example.walletservice.models.PlayerAccount;
import com.example.walletservice.models.Transaction;
import com.example.walletservice.repositories.PlayerAccountRepository;
import com.example.walletservice.service.WalletService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceControllerApplicationTests {

	@Autowired
	private WalletService walletService;
	@Autowired
	private PlayerAccountRepository playerRepository;

	private final Long testPlayerID = 10l;
	private final Double startTestPlayerBalance = 10.0;
	private final Double creditAmount = 5.0;
	private final double debitAmount = 5.0;

	@Test
	@Order(1)
	void shouldCreateAccount() {

		PlayerAccount createdPlayer = walletService.createPlayerAccount(new PlayerAccount(testPlayerID, "Test account name","Test accountsurname",
				startTestPlayerBalance));
		assertThat(createdPlayer).isNotNull();
		assertThat(createdPlayer.getBalance()).isEqualTo(10.0);
		PlayerAccount accountDb = playerRepository.findById(createdPlayer.getId()).orElse(null);
		assertThat(accountDb).isNotNull();
		assertThat(accountDb.getId()).isEqualTo(createdPlayer.getId());

	}


	@Test
	@Order(2)
	void shouldGetPlayerBalance() throws ServiceException {

		Double balance = walletService.getPlayerBalance(testPlayerID);
		assertThat(balance).isNotNull();
		assertThat(balance).isEqualTo(startTestPlayerBalance);
	}


	@Test
	@Order(3)
	void shouldProcessCreditTransaction() throws ServiceException {

		Transaction creditTransaction = walletService.processTransaction(testPlayerID,
				new Transaction(1l, null, creditAmount, new Date(), TransactionType.CREDIT.getValue()));
		assertThat(creditTransaction).isNotNull();
		Double playerBalance = walletService.getPlayerBalance(testPlayerID);
		assertThat(playerBalance).isEqualTo(creditTransaction.getAmount() + startTestPlayerBalance);

	}


	@Test
	@Order(4)
	void shouldProcessDebitTransaction() throws ServiceException {

		Transaction debitTransaction = walletService.processTransaction(testPlayerID,
				new Transaction(2l, null, debitAmount, new Date(), TransactionType.DEBIT.getValue()));
		assertThat(debitTransaction).isNotNull();
		Double playerBalance = walletService.getPlayerBalance(testPlayerID);
		assertThat(playerBalance).isEqualTo(startTestPlayerBalance + creditAmount - debitTransaction.getAmount());
	}


	@Test
	@Order(5)
	void shouldFailToProcess() throws ServiceException {

		ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> {
			walletService.processTransaction(testPlayerID,
					new Transaction(2l, null, debitAmount, new Date(), TransactionType.DEBIT.getValue()));
		})
			;
		assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
	}
}
