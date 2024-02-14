package com.kesmarki.homework.controller;

import com.kesmarki.homework.controller.request.AddressRequest;
import com.kesmarki.homework.controller.request.AddContactRequest;
import com.kesmarki.homework.controller.request.AddPersonRequest;
import com.kesmarki.homework.controller.response.PersonResponse;
import com.kesmarki.homework.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Person controller
 */
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping("/getAll")
    public List<PersonResponse> findAllPersons() {
        return personService.findAllPersons();
    }

    @GetMapping("/get/{personId}")
    public PersonResponse findPersonByID(@PathVariable Long personId) {
        return personService.findPersonByID(personId);
    }
    @PostMapping("/add/person")
    public ResponseEntity<String> addPerson(
            @RequestBody AddPersonRequest addPersonRequest) {
        try {
            personService.addPerson(addPersonRequest);
            return ResponseEntity.ok("Person added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/add/address/{personId}")
    public ResponseEntity<String> addAddressToPerson(
            @PathVariable Long personId,
            @RequestBody AddressRequest addressRequest) {

        try {
            personService.addAddressToPerson(personId, addressRequest);
            return ResponseEntity.ok("Address added successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/update/address/{personId}")
    public ResponseEntity<String> updateAddress(@PathVariable Long personId, @RequestBody AddressRequest updatedAddress) {
        personService.updateAddress(personId, updatedAddress);
        return ResponseEntity.ok("Address updated successfully.");
    }
    @PostMapping("/add/contact/{personId}")
    public ResponseEntity<String> addContactToPerson(@PathVariable Long personId, @RequestBody AddContactRequest addContactRequest) {
        try {
            String result = personService.addContactToPerson(personId, addContactRequest);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.ok("Person deleted succesfully.");
    }

    @DeleteMapping("/delete/address/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        personService.deleteAddress(id);
        return ResponseEntity.ok("Address deleted succesfully.");
    }

}
