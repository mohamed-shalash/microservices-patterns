package org.shalash.service;

import lombok.RequiredArgsConstructor;
import org.shalash.response.OrderDetailsResponse;
import org.shalash.response.OrderResponse;
import org.shalash.response.ProductResponse;
import org.shalash.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderAggregatorService {

    private final WebClient webClient;

    private final String ORDER_URL = "http://localhost:8083/order/";
    private final String USER_URL = "http://localhost:8081/user/";
    private final String PRODUCT_URL = "http://localhost:8082/product";

    public Mono<OrderDetailsResponse> getOrderDetails(Long userId) {
        Mono<UserResponse> userMono = webClient.get()
                .uri(USER_URL + userId)
                .retrieve()
                .bodyToMono(UserResponse.class);

        Mono<OrderResponse> orderMono = webClient.get()
                .uri(ORDER_URL + userId)
                .retrieve()
                .bodyToMono(OrderResponse.class);


        return Mono.zip(userMono, orderMono)
                .flatMap(tuple -> {

                    UserResponse user = tuple.getT1();
                    OrderResponse order = tuple.getT2();

                    Mono<List<ProductResponse>> productMono =
                            webClient.post()
                                    .uri(PRODUCT_URL)
                                    .bodyValue(order.getProductId())
                                    .retrieve()
                                    .bodyToFlux(ProductResponse.class)
                                    .collectList();

                    return productMono.map(products ->
                            new OrderDetailsResponse(order, user, products)
                    );
                });

    }
}