package com.example.wallet.exception;

import java.util.List;

import com.example.wallet.constants.WalletConstants;

public class InsufficeintBalanceException extends Exception {


	private String message;
	private Integer inputMoney;
	private List<Integer> availableMoney;
	
	public InsufficeintBalanceException() {

	}
	
	public InsufficeintBalanceException(String message, Integer inputMoney, List<Integer> availableMoney) {
		this.message=message;
		this.inputMoney=inputMoney;
		this.availableMoney=availableMoney;
	}
	
	public InsufficeintBalanceException( Integer inputMoney, List<Integer> availableMoney) {
		this.inputMoney=inputMoney;
		this.availableMoney=availableMoney;
	}

	public InsufficeintBalanceException(String message) {
		super(message);
	}

	public InsufficeintBalanceException(Throwable cause) {
		super(cause);
	}

	public InsufficeintBalanceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public String toString() {
		return message+inputMoney+WalletConstants.MY_CURRENT_COIN+availableMoney;
	}


}
