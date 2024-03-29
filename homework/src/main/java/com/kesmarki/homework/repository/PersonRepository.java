package com.kesmarki.homework.repository;

import com.kesmarki.homework.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Person repository
 * */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
}