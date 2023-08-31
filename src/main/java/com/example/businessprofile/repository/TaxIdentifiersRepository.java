package com.example.businessprofile.repository;

import com.example.businessprofile.entity.TaxIdentifiers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TaxIdentifiersRepository extends JpaRepository<TaxIdentifiers, BigInteger> {
}
