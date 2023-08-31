package com.example.businessprofile.repository;

import com.example.businessprofile.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface AddressRepository extends JpaRepository<Address, BigInteger> {
}
