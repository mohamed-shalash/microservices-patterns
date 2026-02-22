package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.service.OrcasteratorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrcasteratorContorller {

    private final OrcasteratorService orcasteratorService;


    @GetMapping("/order")
    public String order(@RequestParam("id") String orderId) {
        return orcasteratorService.order(orderId);
    }
}
