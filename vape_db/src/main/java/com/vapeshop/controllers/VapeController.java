package com.vapeshop.controllers;

import com.vapeshop.models.VapeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/vape_db")
public class VapeController {

    @GetMapping("/add")
    public VapeModel addVape() {
        return new VapeModel();
    }

}
