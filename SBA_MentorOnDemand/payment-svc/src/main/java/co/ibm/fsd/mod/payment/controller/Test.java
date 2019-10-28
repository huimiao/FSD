package co.ibm.fsd.mod.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {
    @GetMapping("/")
    public String test(){
        return "hello payment.";
    }
}
