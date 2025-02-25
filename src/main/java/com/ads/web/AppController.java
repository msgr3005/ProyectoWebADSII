package com.ads.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AppController {
    @GetMapping("/public")
    public String mostrarVistaPublica() {
        return "index";
    }
   
    
    
}
