package com.mybilling.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybilling.billing.entity.BillItem;

@Repository
public interface BillItemRepository extends JpaRepository<BillItem, Long> {
	
}
