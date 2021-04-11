package com.example.wallet.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wallet.model.Wallet;

public interface WalletDao extends JpaRepository<Wallet,Long>{

	
}
