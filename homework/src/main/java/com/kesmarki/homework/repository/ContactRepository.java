package com.kesmarki.homework.repository;


import com.kesmarki.homework.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}