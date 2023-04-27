package com.peslayk.controller;

import com.peslayk.model.Room;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class RoomController {
    @PostMapping(value = "/addNewRoom")
    protected void addNewRoom(@RequestBody Room room){
    }

}
