package com.kesmarki.homework.service;


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

    public List<PersonResponse> findAllPersons(){

        var persons = personRepository.findAll();
        List<PersonResponse> personResponses = new ArrayList();

        persons.forEach(person -> personResponses.add(PersonResponse.builder().id(person.getId()).name(person.getName()).addresses(person.getAddresses()).build()));

        logger.info("All persons are listed.");

        return personResponses;
    }

}
