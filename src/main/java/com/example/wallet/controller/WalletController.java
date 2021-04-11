package com.example.wallet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.wallet.constants.WalletConstants;
import com.example.wallet.exception.InsufficeintBalanceException;
import com.example.wallet.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	WalletService walletService;
	
	@GetMapping(value="/balance")
	public String getWalletBalance(){		
		return WalletConstants.MY_CURRENT_COIN + walletService.getWalletBalance().toString();
	}
	
	/**
	 * method to initialize the wallet
	 * @param coins - List of integers
	 * @return - "Success" if wallet updated 
	 */
	@PostMapping(value="/initalize")
	public String initalizeWallet(@RequestBody List<Integer> coins) {
		return walletService.updateWallet(coins);
	}
	
	/**
	 * method to handle payment
	 * @param coin - amount to be paid
	 * @return updated wallet post payment
	 * @throws InsufficeintBalanceException
	 */
	@PostMapping(value="/payMoney")
	public String payMoney(@RequestBody Integer coin) throws InsufficeintBalanceException {
		try {
			return WalletConstants.SUCCESSFULLY_PAID + coin +WalletConstants.MY_CURRENT_COIN+ walletService.payMoney(coin);
		} catch (InsufficeintBalanceException e) {
			return e.toString();
		}
		
	}
	
}
