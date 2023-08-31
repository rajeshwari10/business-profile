package com.example.businessprofile.service.helper;

import com.example.businessprofile.entity.Product;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.repository.ProductRepository;
import com.example.businessprofile.repository.UserProductSubscriptionRepository;
import com.example.businessprofile.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@RunWith(JUnit4.class)
public class CacheHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    UserProductSubscriptionRepository userProductSubscriptionRepository;

    @InjectMocks
    CacheHandler cacheHandler;

    private List<User> userList;
    private List<Product> productList;
    private List<BigInteger> productIds;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        userList = List.of(
                User.builder().userId(BigInteger.ONE).userName("User One").build(),
                User.builder().userId(BigInteger.TWO).userName("User Two").build());
        productList = List.of(
                Product.builder().productId(BigInteger.ONE).productName("Product One").build(),
                Product.builder().productId(BigInteger.TWO).productName("Product Two").build());
        productIds = List.of(BigInteger.ONE, BigInteger.TWO);
    }

    @Test
    public void getAllUsers() {
        when(userRepository.findAll()).thenReturn(userList);
        assertThat(cacheHandler.getAllUsers(Boolean.FALSE)).hasSize(2);
    }

    @Test
    public void getAllProducts() {
        when(productRepository.findAll()).thenReturn(productList);
        assertThat(cacheHandler.getAllProducts(Boolean.FALSE)).hasSize(2);
    }

    @Test
    public void getProductIdsByUserId() {
        when(userProductSubscriptionRepository.findProductIdsByUserId(any(BigInteger.class))).thenReturn(productIds);
        assertThat(cacheHandler.getProductIdsByUserId(BigInteger.ONE, Boolean.FALSE)).hasSize(2);
    }
}