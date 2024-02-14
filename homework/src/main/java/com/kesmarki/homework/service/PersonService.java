package com.kesmarki.homework.service;

import com.kesmarki.homework.controller.request.AddressRequest;
import com.kesmarki.homework.controller.request.AddContactRequest;
import com.kesmarki.homework.controller.request.AddPersonRequest;
import com.kesmarki.homework.controller.response.PersonResponse;
import com.kesmarki.homework.exception.PersonNotFoundException;
import com.kesmarki.homework.model.Address;
import com.kesmarki.homework.model.Contact;
import com.kesmarki.homework.model.Person;
import com.kesmarki.homework.repository.AddressRepository;
import com.kesmarki.homework.repository.ContactRepository;
import com.kesmarki.homework.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the business logic of the person.
 */
@Service
@Transactional
@Slf4j
public class PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);
    private final PersonRepository personRepository;

    private final AddressRepository addressRepository;

    private final ContactRepository contactRepository;
    public PersonService(PersonRepository personRepository, AddressRepository addressRepository, ContactRepository contactRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.contactRepository = contactRepository;
    }

    /**
     * This method returns all persons.
     *
     * @return  List<PersonResponse>
     */
    public List<PersonResponse> findAllPersons(){

        var persons = personRepository.findAll();
        List<PersonResponse> personResponses = new ArrayList();

        persons.forEach(person -> personResponses.add(PersonResponse.builder().id(person.getId()).name(person.getName()).addresses(person.getAddresses()).build()));

        logger.info("All persons are listed.");

        return personResponses;
    }

    /**
     * This method returns a person by id.
     *
     * @param id
     * @return PersonResponse
     */
    public PersonResponse findPersonByID(Long id){

        var person = personRepository.findById(id);

        if (person.isPresent()) {
            return PersonResponse.builder().id(person.get().getId()).name(person.get().getName()).addresses(person.get().getAddresses()).build();
        } else {
            throw new PersonNotFoundException("Person not found with id: " + id);
        }
    }
    /**
     * This method adds a person.
     *
     * @param addPersonRequest
     */
    @Transactional
    public void addPerson(AddPersonRequest addPersonRequest) {

        var person = Person.builder().name(addPersonRequest.getName()).build();

        logger.info("Save person.");

        personRepository.save(person);
    }
    /**
     * This method adds an address to a person.
     *
     * @param personId the id of the person.
     * @param addressRequest
     */
    @Transactional
    public void addAddressToPerson(Long personId, AddressRequest addressRequest) {

        var person = personRepository.findById(personId);

        if (person.isEmpty()) {
            throw new PersonNotFoundException("Person not found with id: " + personId);
        }


        boolean addressTypeExists = person.get().getAddresses().stream()
                .anyMatch(address -> address.getType().equals(addressRequest.getType()));

        if (addressTypeExists) {
            throw new IllegalArgumentException("This type already has an address assigned to the person.");
        }
        var address = Address.builder().address(addressRequest.getAddress()).type(addressRequest.getType()).build();

        address.setPerson(person.get());
        addressRepository.save(address);
    }
    /**
     * This method adds a contact to a person.
     *
     * @param personId the id of the person.
     * @param addContactRequest
     * @return String
     */
    @Transactional
    public String addContactToPerson(Long personId, AddContactRequest addContactRequest) {
        var optionalAddress = addressRepository.findByPersonIdAndAddress(personId.toString(), addContactRequest.getAddress());

        if (optionalAddress.isPresent()) {
            Contact contact = Contact.builder()
                    .type(addContactRequest.getType())
                    .contactInfo(addContactRequest.getContactInfo())
                    .address(optionalAddress.get())
                    .build();
            contactRepository.save(contact);
            return "Contact added successfully.";
        } else {
            throw new PersonNotFoundException("Person with id " + personId + "and with addres " + addContactRequest.getAddress()  + " not found.");
        }
   }
   /**
     * This method deletes a person by id.
     *
     * @param id
     */
   @Transactional
    public void deletePerson(Long id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        List<Address> addresses = person.getAddresses();
        for (Address address : addresses) {
            List<Contact> contacts = address.getContacts();
            for (Contact contact : contacts) {
                contactRepository.delete(contact);
            }
            addressRepository.delete(address);
        }

        personRepository.delete(person);
    }
    /**
     * This method updates an address.
     *
     * @param personId the id of the person.
     * @param updatedAddress
     */
    @Transactional
    public void updateAddress(Long personId, AddressRequest updatedAddress) {

        var existingAddress = addressRepository.findByPersonIdAndType(personId, updatedAddress.getType());

        if (existingAddress.isPresent() == false) {
            existingAddress.get().setAddress(updatedAddress.getAddress());
            addressRepository.save(existingAddress.get());
        }
    }
    /**
     * This method deletes an address by id.
     *
     * @param id
     */
    @Transactional
    public void deleteAddress(Long id) {

        var address = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + id));
        for (Contact contact : address.getContacts()) {
            contactRepository.delete(contact);
        }
        addressRepository.delete(address);
    }
}
