package com.winciak.medicFacility.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(path = {"/home","/", "/login**"})
    public String showLanding() {

        return "index";

    }

    @GetMapping("/loginPage")
    public String showMyLoginPage() {

        return "login-page";

    }


    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "access-denied";

    }

}