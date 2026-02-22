package org.shalash.response;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private String id;
    private List<Integer> productId;
    private String userId;
}
