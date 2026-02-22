package org.shalash.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFailed {
    int paymentId;
    int orderId;
    int userId;
    int productId;
    int amount;
    String secret;
}
