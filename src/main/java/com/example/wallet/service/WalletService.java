package com.example.wallet.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wallet.constants.WalletConstants;
import com.example.wallet.dao.WalletDao;
import com.example.wallet.exception.InsufficeintBalanceException;
import com.example.wallet.model.Wallet;

@Service
public class WalletService {

	@Autowired
	private WalletDao walletDao;
	
	
	public WalletService() {
		
	}
	/**
	 * To Intialize the Wallet Balance
	 * @param coins -- List Of Integers
	 * @return -- Success post initializing the wallet
	 */
	public String updateWallet(List<Integer> coins) {
		Wallet wallet=new Wallet();	
		wallet.setCoins(coins);	
		Collections.sort(wallet.getCoins(), (coin1,coin2) -> coin1.compareTo(coin2));
		walletDao.save(wallet);
		return "Success";
	}
	
	
	/**
	 * method to update/save wallet
	 * @param coins - List of Integer
	 */
	public void saveOrUpdate(List<Integer> coins) {
		Wallet wallet=new Wallet();
		wallet.setCoins(coins);
		wallet.setWalletId(1L);
		Collections.sort(wallet.getCoins(), (coin1,coin2) -> coin1.compareTo(coin2));
		walletDao.save(wallet);
	}
	
	/**
	 * Get the amount in the wallet
	 * @return the list of integer in wallet
	 */
	public List<Integer> getWalletBalance(){
	
		List<Wallet> wallets= walletDao.findAll();
		return wallets.get(0).getCoins();
	}
	
	/**
	 * method to pay money from the wallet
	 * @param coin - input amount 
	 * @return - remain amount in the wallet (list of integer)
	 * @throws InsufficeintBalanceException
	 */
	public List<Integer> payMoney(Integer coin) throws InsufficeintBalanceException {
	    
		List<Integer> availableCoins=getWalletBalance();
		try {	
			Map<String, List<Integer>> coinsMap = getMatchCoins(coin,availableCoins);
				for (Integer removeIndex : coinsMap.get(WalletConstants.PAID_MONEY) ) {
					availableCoins.remove(removeIndex);
				}			
				availableCoins.addAll(coinsMap.get(WalletConstants.CHANGE_MONEY));
				saveOrUpdate(availableCoins);
		}catch(InsufficeintBalanceException ex) {
			throw ex;
		}
		
		return availableCoins;
	}
	
	/**
	 * Logic implementation
	 * @param inputCoin - amount paid
	 * @param walletCoins - amount in wallet (list of integer)
	 * @return Map of amount to removed and modified
	 * @throws InsufficeintBalanceException
	 */
	public Map<String, List<Integer>> getMatchCoins(Integer inputCoin,List<Integer> walletCoins) throws InsufficeintBalanceException {
		
		Integer matchCoins = new Integer(0);
		Map<String,List<Integer>> coinsMap= new HashMap<String,List<Integer>>();
		List<Integer> paidMoneyList=new ArrayList<Integer>();
		List<Integer> changeMoneyList=new ArrayList<Integer>();
		
		for(Integer coin  : walletCoins) {
			matchCoins += coin;
			if (matchCoins == inputCoin) {
				paidMoneyList.add(coin);
				coinsMap.put(WalletConstants.PAID_MONEY, paidMoneyList);
				coinsMap.put(WalletConstants.CHANGE_MONEY, changeMoneyList);
				return coinsMap;
			}else if (matchCoins >inputCoin) {
				paidMoneyList.add(coin);
				changeMoneyList.add(matchCoins -inputCoin);
				coinsMap.put(WalletConstants.PAID_MONEY, paidMoneyList);
				coinsMap.put(WalletConstants.CHANGE_MONEY, changeMoneyList);
				return coinsMap;
			}else  {
				paidMoneyList.add(coin);				
			}				
		}
		throw new InsufficeintBalanceException(WalletConstants.INSUFFICIENT_COINS,inputCoin,walletCoins);
	}
}
