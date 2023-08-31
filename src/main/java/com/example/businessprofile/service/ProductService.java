package com.example.businessprofile.service;

import com.example.businessprofile.entity.UserProductSubscription;
import com.example.businessprofile.exception.model.BadRequestException;
import com.example.businessprofile.exception.model.ConflictException;
import com.example.businessprofile.exception.model.failure.FailureDetails;
import com.example.businessprofile.exception.model.failure.FailureErrorCodes;
import com.example.businessprofile.model.ResponseMaster;
import com.example.businessprofile.repository.UserProductSubscriptionRepository;
import com.example.businessprofile.service.helper.CacheHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static com.example.businessprofile.utils.BusinessProfileConstants.ExceptionConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final CacheHandler cacheHandler;
    private final UserProductSubscriptionRepository userProductSubscriptionRepository;

    public ResponseMaster<Void> subscribeToAProduct(BigInteger userId, BigInteger productId) {
        cacheHandler.getAllUsers(FALSE)
                .stream()
                .filter(u -> u.getUserId().equals(userId))
                .findAny()
                .ifPresentOrElse(user -> {
                            cacheHandler.getAllProducts(FALSE)
                                    .stream()
                                    .filter(p -> p.getProductId().equals(productId))
                                    .findAny()
                                    .ifPresentOrElse(product -> {
                                        userProductSubscriptionRepository.findByUserAndProduct(user, product).ifPresentOrElse(userProductSubscription -> {
                                            throw new ConflictException(FailureErrorCodes.ERR409, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_3, productId + " - " + product.getProductName())).build());
                                        }, () -> {
                                            userProductSubscriptionRepository.save(UserProductSubscription.builder()
                                                    .product(product)
                                                    .user(user)
                                                    .build());
                                            //Refresh User Product Subscription Cache
                                            cacheHandler.getProductIdsByUserId(userId, TRUE);
                                        });
                                    }, () -> {
                                        throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_2, productId)).build());
                                    });
                        },
                        () -> {
                            throw new BadRequestException(FailureErrorCodes.ERR400, FALSE, FailureDetails.builder().description(String.format(BAD_REQUEST_1, userId)).build());
                        });
        return ResponseMaster.mapResponseMaster(HttpStatus.OK.name(), TRUE, null);
    }
}
