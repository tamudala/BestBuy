package com.store.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.order.beans.PetBean;
import com.store.order.model.PetModel;
import com.store.order.repository.PetRepo;

@RestController
public class OrderController {

	@Autowired
	private PetRepo petRepo;

	@GetMapping("/petstore/{id}")
	private ResponseEntity<PetModel> getAPetInTheStore(@PathVariable("id") long id) {
		Optional<PetModel> petData = petRepo.findById(id);

		if (petData.isPresent()) {
			return new ResponseEntity<>(petData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/petstore")
	private ResponseEntity<List<PetModel>> getAllPetsInTheStore() {
		try {
			List<PetModel> petData = new ArrayList<PetModel>();

			petRepo.findAll().forEach(petData::add);

			if (petData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(petData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/petstore")
	private ResponseEntity<PetModel> saveAPetToStore(@RequestBody PetBean petBean) {
		try {
			PetModel petModel = petRepo.save(new PetModel(petBean.getName(), petBean.getType(), petBean.getPrice()));
			return new ResponseEntity<>(petModel, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/petstore/{id}")
	private ResponseEntity<PetModel> updateAPetToStore(@PathVariable("id") long id, @RequestBody PetBean petBean) {
		Optional<PetModel> petData = petRepo.findById(id);

		if (petData.isPresent()) {
			PetModel petModel = petData.get();
			petModel.setName(petBean.getName());
			petModel.setType(petBean.getType());
			petModel.setPrice(petBean.getPrice());
			return new ResponseEntity<>(petRepo.save(petModel), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/petstore/{id}")
	private ResponseEntity<HttpStatus> deleteAPetFromStore(@PathVariable("id") long id) {
		try {
			petRepo.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
