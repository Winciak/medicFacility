package com.winciak.medicFacility.controllers;

import com.winciak.medicFacility.entities.LabTest;
import com.winciak.medicFacility.services.LabTestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/employee/labTests")
public class LabTestController {

    private final LabTestService labTestService;

    @Autowired
    public LabTestController( LabTestService labTestService) {
        this.labTestService = labTestService;

    }

    @GetMapping("/list")
    public String listLabTests(Model theModel){

        List<LabTest> labTestList = labTestService.findAll();

        theModel.addAttribute("labTestList", labTestList);

        return "labTest/list-tests";

    }

    @GetMapping("/showFormForAdd")
    public String showLabTestFormForAdd(Model theModel) {

        LabTest labTest = new LabTest();

        theModel.addAttribute("labTest", labTest);

        return "/labTest/add-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showLabTestFormForUpdate(@RequestParam("labTestId")int id, Model theModel){

        LabTest labTest = labTestService.findById(id);

        theModel.addAttribute("labTest", labTest);

        return "/labTest/add-form";
    }


    @PostMapping("/saveLabTest")
    public String saveLabTest(@Valid @ModelAttribute("labTest") LabTest labTest,
                            BindingResult theBindingResult,
                            Model theModel){

        if (theBindingResult.hasErrors()){

            return "/labTest/add-form";
        }


        LabTest test = new LabTest();
        if (labTest.getId() != 0) {
            test = labTestService.findById(labTest.getId());
        }

        LabTest existing = labTestService.findByName(labTest.getName());

        if (existing != null) {
            if (!Objects.equals(test.getName(), existing.getName())) {
                theModel.addAttribute("editError", labTest.getName() + " Lab test already exists.");

                return "/labTest/add-form";
            }
        }

        labTestService.save(labTest);

        return "redirect:/employee/labTests/list";
    }

    @GetMapping("/delete")
    public String deleteLabTest(@RequestParam("labTestId") int id) {

        labTestService.deleteById(id);

        return "redirect:/employee/labTests/list";
    }
}
