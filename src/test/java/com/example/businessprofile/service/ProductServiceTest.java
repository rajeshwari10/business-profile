package com.example.businessprofile.service;

import com.example.businessprofile.entity.Product;
import com.example.businessprofile.entity.User;
import com.example.businessprofile.entity.UserProductSubscription;
import com.example.businessprofile.exception.model.BadRequestException;
import com.example.businessprofile.exception.model.ConflictException;
import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.repository.UserProductSubscriptionRepository;
import com.example.businessprofile.service.helper.CacheHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThrows;

@ExtendWith(SpringExtension.class)
@RunWith(JUnit4.class)
public class ProductServiceTest {
    @Mock
    CacheHandler cacheHandler;

    @Mock
    UserProductSubscriptionRepository userProductSubscriptionRepository;

    @InjectMocks
    ProductService productService;

    private List<User> userList;
    private List<Product> productList;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        userList = List.of(
                User.builder().userId(BigInteger.ONE).userName("User One").build(),
                User.builder().userId(BigInteger.TWO).userName("User Two").build());
        productList = List.of(
                Product.builder().productId(BigInteger.ONE).productName("Product One").build(),
                Product.builder().productId(BigInteger.TWO).productName("Product Two").build());
    }

    @Test
    public void subscribeToAProduct() {
        when(cacheHandler.getAllUsers(anyBoolean())).thenReturn(userList);
        when(cacheHandler.getAllProducts(anyBoolean())).thenReturn(productList);
        when(userProductSubscriptionRepository.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(Optional.empty());
        assertSame(productService.subscribeToAProduct(BigInteger.ONE, BigInteger.ONE).getStatusCode(), ResponseMaster.mapResponseMaster(HttpStatus.OK.name(), TRUE, null).getStatusCode());
    }

    @Test
    public void subscribeToProductInvalidUserID() {
        when(cacheHandler.getAllUsers(anyBoolean())).thenReturn(userList);
        assertThrows(BadRequestException.class, () -> productService.subscribeToAProduct(BigInteger.TEN, BigInteger.TEN));
    }

    @Test
    public void subscribeToProductInvalidProductID() {
        when(cacheHandler.getAllUsers(anyBoolean())).thenReturn(userList);
        when(cacheHandler.getAllProducts(anyBoolean())).thenReturn(productList);
        assertThrows(BadRequestException.class, () -> productService.subscribeToAProduct(BigInteger.ONE, BigInteger.TEN));
    }

    @Test
    public void subscribeToProductConflictException() {
        when(cacheHandler.getAllUsers(anyBoolean())).thenReturn(userList);
        when(cacheHandler.getAllProducts(anyBoolean())).thenReturn(productList);
        when(userProductSubscriptionRepository.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(Optional.of(UserProductSubscription.builder().build()));
        assertThrows(ConflictException.class, () -> productService.subscribeToAProduct(BigInteger.ONE, BigInteger.ONE));
    }
}