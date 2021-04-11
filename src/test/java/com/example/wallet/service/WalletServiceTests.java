package com.example.wallet.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.wallet.dao.WalletDao;
import com.example.wallet.exception.InsufficeintBalanceException;
import com.example.wallet.model.Wallet;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class WalletServiceTests {
	
	@Mock
	WalletService mockWallet;
	
	@Mock
	WalletDao walletDao;
	
	  @Test
	  public void testPayMoneyWithoutChange() throws InsufficeintBalanceException {
		  
		  Wallet wallet = new Wallet();
		  wallet.setWalletId(1L);
		  wallet.setCoins(Arrays.asList(new Integer[] {1,1,2,2,3}));
		  
		  Map<String, List<Integer>> coinsMap= new HashMap<String, List<Integer>>();
		  coinsMap.put("paidMoney", Arrays.asList(new Integer[] {1}));
		  coinsMap.put("changeMoney", Arrays.asList(new Integer[] {}));
		  
		  Integer inputCoin=new Integer(1);
		  lenient().when(mockWallet.getMatchCoins(inputCoin, wallet.getCoins())).thenReturn(coinsMap);
		  lenient().when(mockWallet.payMoney(inputCoin)).thenReturn(Arrays.asList(new Integer[] {1,2,2,3}));

		  assertEquals(Arrays.asList(new Integer[] {1}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("paidMoney"));
		  assertEquals(Arrays.asList(new Integer[] {}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("changeMoney"));
		  assertEquals(Arrays.asList(new Integer[] {1,2,2,3}), mockWallet.payMoney(inputCoin));

	  }
	  
	  @Test
	  public void testPayMoneyWithoutChange2() throws InsufficeintBalanceException {
		  
		  Wallet wallet = new Wallet();
		  wallet.setWalletId(1L);
		  wallet.setCoins(Arrays.asList(new Integer[] {1,2,2,3}));
		  
		  Map<String, List<Integer>> coinsMap= new HashMap<String, List<Integer>>();
		  coinsMap.put("paidMoney", Arrays.asList(new Integer[] {1,2}));
		  coinsMap.put("changeMoney", Arrays.asList(new Integer[] {}));
		  
		  Integer inputCoin=new Integer(3);
		  lenient().when(mockWallet.getMatchCoins(inputCoin, wallet.getCoins())).thenReturn(coinsMap);
		  lenient().when(mockWallet.payMoney(inputCoin)).thenReturn(Arrays.asList(new Integer[] {2,3}));

		  assertEquals(Arrays.asList(new Integer[] {1,2}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("paidMoney"));
		  assertEquals(Arrays.asList(new Integer[] {}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("changeMoney"));
		  assertEquals(Arrays.asList(new Integer[] {2,3}), mockWallet.payMoney(inputCoin));

	  }
	  
	  @Test
	  public void testPayMoneyWithChange() throws InsufficeintBalanceException {
		  
		  Wallet wallet = new Wallet();
		  wallet.setWalletId(1L);
		  wallet.setCoins(Arrays.asList(new Integer[] {2,3}));
		  
		  Map<String, List<Integer>> coinsMap= new HashMap<String, List<Integer>>();
		  coinsMap.put("paidMoney", Arrays.asList(new Integer[] {2}));
		  coinsMap.put("changeMoney", Arrays.asList(new Integer[] {1}));
		  
		  Integer inputCoin=new Integer(1);
		  lenient().when(mockWallet.getMatchCoins(inputCoin, wallet.getCoins())).thenReturn(coinsMap);
		  lenient().when(mockWallet.payMoney(inputCoin)).thenReturn(Arrays.asList(new Integer[] {1,3}));

		  assertEquals(Arrays.asList(new Integer[] {2}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("paidMoney"));
		  assertEquals(Arrays.asList(new Integer[] {1}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("changeMoney"));
		  assertEquals(Arrays.asList(new Integer[] {1,3}), mockWallet.payMoney(inputCoin));

	  }
	  
	  @Test
	  public void testPayMoneyWithChange2() throws InsufficeintBalanceException {
		  
		  Wallet wallet = new Wallet();
		  wallet.setWalletId(1L);
		  wallet.setCoins(Arrays.asList(new Integer[] {1,3}));
		  
		  Map<String, List<Integer>> coinsMap= new HashMap<String, List<Integer>>();
		  coinsMap.put("paidMoney", Arrays.asList(new Integer[] {1,3}));
		  coinsMap.put("changeMoney", Arrays.asList(new Integer[] {2}));
		  
		  Integer inputCoin=new Integer(2);
		  lenient().when(mockWallet.getMatchCoins(inputCoin, wallet.getCoins())).thenReturn(coinsMap);
		  lenient().when(mockWallet.payMoney(inputCoin)).thenReturn(Arrays.asList(new Integer[] {2}));

		  assertEquals(Arrays.asList(new Integer[] {1,3}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("paidMoney"));
		  assertEquals(Arrays.asList(new Integer[] {2}), mockWallet.getMatchCoins(inputCoin, wallet.getCoins()).get("changeMoney"));
		  assertEquals(Arrays.asList(new Integer[] {2}), mockWallet.payMoney(inputCoin));

	  }
	  
	 
	

}
