package com.example.businessprofile.controller;

import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.model.request.BusinessProfileRequest;
import com.example.businessprofile.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void subscribeToAProduct() throws Exception {
        Mockito.when(productService.subscribeToAProduct(any(BigInteger.class), any(BigInteger.class))).thenReturn(ResponseMaster.mapResponseMaster(HttpStatus.OK.toString(), Boolean.TRUE, null));
        mockMvc.perform(post("/product/subscribe").contentType(MediaType.APPLICATION_JSON).param("userId", BigInteger.ONE.toString()).param("productId", BigInteger.ONE.toString()))
                .andDo(print())
                .andExpect(status().isOk());
        verify(productService).subscribeToAProduct(BigInteger.ONE, BigInteger.ONE);
        verify(productService, times(1)).subscribeToAProduct(BigInteger.ONE, BigInteger.ONE);
    }
}