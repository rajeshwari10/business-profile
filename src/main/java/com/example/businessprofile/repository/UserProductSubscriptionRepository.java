package com.example.businessprofile.repository;

import com.example.businessprofile.entity.Product;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.entity.UserProductSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProductSubscriptionRepository extends JpaRepository<UserProductSubscription, UUID> {

    Optional<UserProductSubscription> findByUserAndProduct(User user, Product product);

    @Query(value = "select product_id from user_product_subscription where user_id = :userId", nativeQuery = true)
    List<BigInteger> findProductIdsByUserId(BigInteger userId);
}
