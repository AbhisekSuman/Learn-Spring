package com.kodnest.servicelayer.controller;

import com.kodnest.servicelayer.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class ClientController {
    ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @RequestMapping("/display")
    public String display(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, Model model) {

        model.addAttribute("fullname", clientService.getFullName(firstname, lastname));
        return "result";
    }
}
