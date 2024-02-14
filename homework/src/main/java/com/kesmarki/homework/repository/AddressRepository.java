package com.kesmarki.homework.repository;

import com.kesmarki.homework.model.Address;
import com.kesmarki.homework.model.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
/**
 * Address repository
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByPersonId(Long id);
    /**
     * Find address by person id and address
     * @param personId
     * @param address
     * @return  Optional<Address>
     */
    @Query("SELECT a FROM Address a WHERE a.person.id= :personId AND a.address = :address ")
    Optional<Address> findByPersonIdAndAddress(@Param("personId") String personId, @Param("address") String address);

    @Query("SELECT a FROM Address a WHERE a.person.id= :personId AND a.type = :type ")
    Optional<Address> findByPersonIdAndType(@Param("personId") Long personId,  @Param("type") AddressType type);
}
