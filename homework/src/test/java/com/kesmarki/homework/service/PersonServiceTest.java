package com.kesmarki.homework.service;

import com.kesmarki.homework.controller.request.AddressRequest;
import com.kesmarki.homework.exception.PersonNotFoundException;
import com.kesmarki.homework.model.*;
import com.kesmarki.homework.repository.AddressRepository;
import com.kesmarki.homework.repository.ContactRepository;
import com.kesmarki.homework.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PersonServiceTest {

    private final String dummyAddress = "dummyAddress";

    private final String dummyName = "dummyName";

    private final AddressType dummyAddressType = AddressType.PERMANENT;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PersonService personService;


    @Test
    public void shouldAddNewAddress(){
        setup();

        var expectedSize = 1;
        var person = personRepository.findByName(dummyName);

        personService.addAddressToPerson(person.getId(),generateDummyAddressRequest());

        assertEquals(expectedSize,person.getAddresses().size());
    }
    @Test
    public void shouldThrowExceptionWhenIGiveUncorrectId(){
        setup();

        assertThrows(PersonNotFoundException.class,()->personService.addAddressToPerson(12312L,generateDummyAddressRequest()));
    }

    @Test
    public void shouldDeletePerson(){
        setup();

        var person = personRepository.findByName(dummyName);

        personService.deletePerson(person.getId());

        var deletedPerson = personRepository.findByName(dummyName);

        Assertions.assertNull(deletedPerson);
    }


    private void setup(){
        personRepository.save(generateDummyPerson());
    }
    @AfterEach
    void deleteAll(){
        contactRepository.deleteAll();
        addressRepository.deleteAll();
        personRepository.deleteAll();
    }
    private Person generateDummyPerson(){
       return Person.builder()
                .name(dummyName)
                .addresses(List.of(
                        Address.builder()
                                .type(AddressType.TEMPORARY)
                                .address("123 asd utca")
                                .contacts(List.of(
                                        Contact.builder()
                                                .type(ContactType.PHONE)
                                                .contactInfo("1234567890")
                                                .build(),
                                        Contact.builder()
                                                .type(ContactType.EMAIL)
                                                .contactInfo("test@test.com")
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
    private AddressRequest generateDummyAddressRequest(){
        return AddressRequest.builder().address(dummyAddress).type(dummyAddressType).build();
    }


}