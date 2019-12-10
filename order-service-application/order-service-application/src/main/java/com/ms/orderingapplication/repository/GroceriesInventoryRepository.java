package com.ms.orderingapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms.orderingapplication.model.GroceriesInventory;
import com.ms.orderingapplication.model.OrderInformation;

@Repository
public interface GroceriesInventoryRepository extends JpaRepository<OrderInformation, Long>{
	
	/*
	 * int id; String productName; int quantity; String brandName; String
	 * modelNumber;
	 */
	@Query("FROM GroceriesInventory where productName = ?1 AND quantity >= ?2 and brandName = ?3 and modelNumber = ?4 ")
	GroceriesInventory findInventory(String productName , int quantity , String brandName , String modelNumber);
	
	
}
