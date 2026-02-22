package org.shalash.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order{
    private int orderId;
    private int userId;
    private int productId;
    private int amount;
    private Date date ;
}
