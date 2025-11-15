package com.amexmart.repository;

import com.amexmart.model.Address;
import com.amexmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    Address findByUserAndIsDefaultTrue(User user);
}
