package com.example.businessprofile.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceFallbackFactory implements FallbackFactory<ProductServiceFeignClientFallback> {
    @Override
    public ProductServiceFeignClientFallback create(Throwable cause) {
        return new ProductServiceFeignClientFallback();
    }
}
