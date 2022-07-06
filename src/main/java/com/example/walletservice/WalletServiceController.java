package com.example.walletservice;

import com.example.walletservice.models.PlayerAccount;
import com.example.walletservice.models.Transaction;
import com.example.walletservice.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletServiceController {

    @GetMapping("/")
    public String helloWorld(){
        return "Hello World";
    }

    @Autowired
    private WalletService walletService;

    @PostMapping("/player/")
    public ResponseEntity createBuilding(@RequestBody PlayerAccount playerAccount){
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createPlayerAccount(playerAccount));
    }

    @GetMapping("/balance/player/{playerId}")
    public ResponseEntity getPlayerBalance(@PathVariable("playerId") Long playerId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(walletService.getPlayerBalance(playerId));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("/transaction/player/{playerId}")
    public ResponseEntity getTransactionsByPlayerId(@PathVariable("playerId") Long playerId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(walletService.getTransactionsByPlayerId(playerId));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @PostMapping("/transaction/player/{playerId}")
    public ResponseEntity processTransaction(@PathVariable("playerId") Long playerId, @RequestBody Transaction transaction){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(walletService.processTransaction(playerId, transaction));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }


}
