package com.hagag.shopsphere_ecommerce.repository;

import com.hagag.shopsphere_ecommerce.entity.Address;
import com.hagag.shopsphere_ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

    List<Address> findByUser_Id(Long userId);

    List<Address> findByUser_IdAndDefaultAddressTrue (Long userId);
}
