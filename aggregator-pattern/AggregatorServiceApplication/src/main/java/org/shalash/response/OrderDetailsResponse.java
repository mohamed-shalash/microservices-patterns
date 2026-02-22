package org.shalash.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDetailsResponse {
    private OrderResponse order;
    private UserResponse user;
    private List<ProductResponse> products;
}