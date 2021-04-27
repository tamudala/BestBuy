package com.store.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.order.model.PetModel;

public interface PetRepo extends JpaRepository<PetModel, Long> {
	List<PetModel> findByName(String name);
}
