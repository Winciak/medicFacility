package com.winciak.medicFacility.controllers;


import com.winciak.medicFacility.entities.PatientTest;
import com.winciak.medicFacility.entities.PatientUser;
import com.winciak.medicFacility.entities.PatientsProjects;
import com.winciak.medicFacility.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public String viewAccount(@AuthenticationPrincipal UserDetails loggedUser, Model theModel){

        String login = loggedUser.getUsername();

        PatientUser patient = userService.findByLogin(login);

        List<PatientsProjects> projects = patient.getPatientsProjects();

        List<PatientTest> tests = patient.getPatientTestList();

        theModel.addAttribute("patient", patient);
        theModel.addAttribute("projects", projects);
        theModel.addAttribute("tests", tests);


        return "users/account-page";
    }


}
