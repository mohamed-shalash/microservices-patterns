package org.shalash.service;

import org.shalash.object.Prodcut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    List<Prodcut> products = new ArrayList<>();

    ProductService() {
        products.add(new Prodcut("1", "iphone", 150, 20));
        products.add(new Prodcut("2", "shoes", 50, 10));
        products.add(new Prodcut("3", "labtop", 200, 30));
    }

    public List<Prodcut> getProduct(List<String> id) {
        return products.stream().filter(product -> id.contains(product.getId())).toList();
    }
}
