package org.shalash.controller;


import lombok.RequiredArgsConstructor;
import org.shalash.object.Prodcut;
import org.shalash.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public List<Prodcut> hello(@RequestBody List<String> ids) {
        return productService.getProduct(ids);
    }
}
