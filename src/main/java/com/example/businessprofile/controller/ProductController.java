package com.example.businessprofile.controller;


import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.service.ProductService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

import static org.springframework.http.HttpStatus.OK;

@OpenAPIDefinition(
        info = @Info(
                title = "Business Profile Service")
)
@Slf4j
@Tag(name = "Product Controller")
@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    /**
     * @param userId    maps to the user id.
     * @param productId maps to the product id.
     * @apiNote subscribeToAProduct allows the user to subscribe to a product.
     */
    @Operation(summary = "Subscribe to a Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Server error"),
            @ApiResponse(responseCode = "409", description = "Product Conflict"),
            @ApiResponse(responseCode = "200", description = "Changes Impacted"),
            @ApiResponse(responseCode = "400", description = "Invalid Product/Invalid User")})
    @PostMapping("/subscribe")
    public ResponseEntity<ResponseMaster<Void>> subscribeToAProduct(@RequestParam("userId") BigInteger userId,
                                                                    @RequestParam("productId") BigInteger productId) {
        return ResponseEntity.status(OK).body(productService.subscribeToAProduct(userId, productId));
    }
}
