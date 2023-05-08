package com.winciak.medicFacility.controllers;

import com.winciak.medicFacility.entities.*;
import com.winciak.medicFacility.services.LabTestService;
import com.winciak.medicFacility.services.ResearchProjectService;
import com.winciak.medicFacility.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employee/orderPatientTest")
public class OrderPatientTestController {

    private final ResearchProjectService researchProjectService;

    private final LabTestService labTestService;

    private final UserService userService;

    @Autowired
    public OrderPatientTestController(ResearchProjectService researchProjectService, LabTestService labTestService, UserService userService) {
        this.researchProjectService = researchProjectService;
        this.labTestService = labTestService;
        this.userService = userService;
    }


//    @GetMapping("/showProjectTests")
//    public String showProjectTests(@RequestParam("projectId")int id, Model theModel){
//
//        ResearchProject researchProject = researchProjectService.findById(id);
//
//        theModel.addAttribute("researchProject", researchProject);
//
//        List<TestInProject> testInProjectList = researchProjectService.findAllTestsByResearchProjectId(id);
//
//        theModel.addAttribute("testInProjectList", testInProjectList);
//
//        return "/researchProject/tests-list";
//    }

    @GetMapping("/showOrderPatientTestFormForAdd")
    public String showOrderPatientTestFormForAdd(@RequestParam("patientId")int id, Model theModel, RedirectAttributes redirectAttributes) {

        PatientTest patientTest = new PatientTest();

        populateModelPatientTestForm(id, patientTest, theModel);

        List<TestInProject> testInProjectList = (List<TestInProject>) theModel.getAttribute("testInProjectList");

        assert testInProjectList != null;
        if(testInProjectList.size()==0){

            redirectAttributes.addFlashAttribute("orderError", "Patient hasn't consented to participate in any project or project doesn't have any tests.");
            return "redirect:/employee/patients/showDetails?patientId=" + id;
        }

        return "/order/add-TestToPatient-form";
    }

    @GetMapping("/showOrderPatientTestFormForUpdate")
    public String showTestToProjectFormForUpdate(@RequestParam("patientTestId")int patientTestId,
                                                 Model theModel) {

        PatientTest patientTest = userService.findPatientTestById(patientTestId);

        theModel.addAttribute("patientTest", patientTest);

        populateModelPatientTestForm(patientTest.getPatientUser().getId(), patientTest, theModel);


        return "/order/add-TestToPatient-form";
    }



    @PostMapping("/saveTestToPatient")
    public String saveTestToPatient(@RequestParam("patientId")int id, @Valid @ModelAttribute("patientTest") PatientTest patientTest,
                                    BindingResult bindingResult, Model theModel){

        if (bindingResult.hasErrors()){

            populateModelPatientTestForm(id, patientTest, theModel);

            return "/order/add-TestToPatient-form";
        }

//        PatientTest existing = userService.findByPatientUserIdAndTestInProjectId(id, patientTest.getTestInProject().getId());
//
//        if (existing != null ){
//
//            theModel.addAttribute("editError", patientTest.getTestInProject().getLabTest().getName() + " test already ordered.");
//
//            populateModelPatientTestForm(id, patientTest, theModel);
//
//            return "/order/add-TestToPatient-form";
//        }


        PatientUser patientUser = userService.findById(id);

        patientTest.setPatientUser(patientUser);

        userService.savePatientTest(patientTest);

        return "redirect:/employee/patients/showDetails?patientId=" + id;
    }

    private void populateModelPatientTestForm(@RequestParam("patientId") int id, @ModelAttribute("patientTest") @Valid PatientTest patientTest, Model theModel) {

        theModel.addAttribute("patientTest", patientTest);

        PatientUser patientUser = userService.findById(id);

        theModel.addAttribute("patientUser", patientUser);

        List<TestInProject> testInProjectList = new ArrayList<>();

        for(PatientsProjects patientsProject : patientUser.getPatientsProjects()){
            if(patientsProject.getConsent()==1){
                testInProjectList.addAll(patientsProject.getResearchProject().getTestInProjects());
            }
        }

        theModel.addAttribute("testInProjectList", testInProjectList);
    }

    @GetMapping("/deleteTestFromPatient")
    public String deleteTestFromPatient(@RequestParam("patientTestId")int patientTestId) {

        PatientTest patientTest = userService.findPatientTestById(patientTestId);

        int id = patientTest.getPatientUser().getId();

        userService.deletePatientTest(patientTest);

        return "redirect:/employee/patients/showDetails?patientId=" + id;

    }
}
