package com.kesmarki.homework;

import com.kesmarki.homework.model.*;
import com.kesmarki.homework.repository.AddressRepository;
import com.kesmarki.homework.repository.ContactRepository;
import com.kesmarki.homework.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class HomeworkApplication  implements CommandLineRunner {

	@Autowired
	PersonRepository personRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ContactRepository contactRepository;

	public static void main(String[] args) {
		SpringApplication.run(HomeworkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(personRepository.findAll().isEmpty()){

			System.out.println("No data found, creating some dummy data");

			var person = Person.builder()
					.name("Test Person")
					.build();


			var address = Address.builder()
					.type(AddressType.PERMANENT)
					.address("123 asd utca")
					.build();


			var contact = Contact.builder()
					.type(ContactType.PHONE)
					.contactInfo("1234567890")
					.build();


			person.setAddresses(List.of(address));
			address.setContacts(List.of(contact));

			personRepository.save(person);
			address.setPerson(personRepository.findByName(person.getName()));
			addressRepository.save(address);
			contact.setAddress(addressRepository.findByPersonIdAndAddress(person.getId().toString(),address.getAddress()).get());
			contactRepository.save(contact);
		}
	}
}
