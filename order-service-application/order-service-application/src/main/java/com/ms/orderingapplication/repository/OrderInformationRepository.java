package com.ms.orderingapplication.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ms.orderingapplication.model.OrderInformation;

/*int id;
String apiKey;
String userToken;
String firstName;
String lastName;
String email;
LocalDateTime dateOfPurchase;
String transactionToken;
String serviceToken;
String orderStatus;*/
@Repository
public interface OrderInformationRepository extends JpaRepository<OrderInformation, Long>{
	@Query("FROM OrderInformation where id = ?1")
	OrderInformation findByOrderId(Integer id);
	
	@Transactional
	@Modifying(clearAutomatically = true)
    @Query("UPDATE OrderInformation oi SET oi.orderStatus = :orderStatus WHERE oi.id = :orderId ")
	int updateConvFactor(@Param("orderId") int orderId , @Param("orderStatus") String orderStatus);

	
	
}
