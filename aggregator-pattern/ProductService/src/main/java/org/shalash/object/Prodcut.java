package org.shalash.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Prodcut {
    private String id;
    private String name;
    private int price;
    private int quantity;
}
