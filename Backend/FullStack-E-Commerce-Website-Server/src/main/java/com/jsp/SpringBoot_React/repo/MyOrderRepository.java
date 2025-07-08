package com.jsp.SpringBoot_React.repo;



import com.jsp.SpringBoot_React.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {
    // Custom query to find orders by user username
//    List<MyOrder> findOrdersByUserUsername(String username);
    List<MyOrder> findOrdersByUserId(Long userId);
}

