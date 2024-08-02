package com.arthur.adi.repository;

import com.arthur.adi.entity.AccountStock;
import com.arthur.adi.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
