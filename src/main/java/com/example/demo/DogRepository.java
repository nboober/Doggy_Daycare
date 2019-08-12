package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface DogRepository extends CrudRepository<Dog, Long> {
}
