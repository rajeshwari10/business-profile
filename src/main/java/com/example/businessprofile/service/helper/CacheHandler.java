package com.example.businessprofile.service.helper;

import com.example.businessprofile.entity.Product;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.repository.ProductRepository;
import com.example.businessprofile.repository.UserProductSubscriptionRepository;
import com.example.businessprofile.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHandler {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserProductSubscriptionRepository userProductSubscriptionRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "allUsers", condition = "!#reset")
    @CacheEvict(value = "allUsers", condition = "#reset", beforeInvocation = true, allEntries = true)
    public List<User> getAllUsers(Boolean reset) {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "allProducts", condition = "!#reset")
    @CacheEvict(value = "allProducts", condition = "#reset", beforeInvocation = true, allEntries = true)
    public List<Product> getAllProducts(Boolean reset) {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "getProductIdsByUserId", condition = "!#reset", key = "#userId")
    @CacheEvict(value = "getProductIdsByUserId", condition = "#reset", key = "#userId", beforeInvocation = true)
    public List<BigInteger> getProductIdsByUserId(BigInteger userId, Boolean reset) {
        return userProductSubscriptionRepository.findProductIdsByUserId(userId);
    }
}
