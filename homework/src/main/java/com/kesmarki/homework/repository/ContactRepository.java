package com.kesmarki.homework.repository;


import com.kesmarki.homework.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Contact repository
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
}