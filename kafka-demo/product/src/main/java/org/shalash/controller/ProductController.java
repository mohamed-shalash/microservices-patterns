package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.config.ProducerService;
import org.shalash.event.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProducerService producer;
    int count = 0;
    @GetMapping("/send")
    public String send() {
        //producer.send(new Order((count++)+"", "Shoes", 100*Math.random()));
        producer.send(new Order("123","Shoes", 100));
        return "Sent!";
    }
}
