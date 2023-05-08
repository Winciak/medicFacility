package com.winciak.medicFacility.controllers;

import com.winciak.medicFacility.entities.LabTest;
import com.winciak.medicFacility.entities.ResearchProject;
import com.winciak.medicFacility.entities.TestInProject;
import com.winciak.medicFacility.services.LabTestService;
import com.winciak.medicFacility.services.ResearchProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee/researchProjects")
public class ResearchProjectController {

    private final ResearchProjectService researchProjectService;

    private final LabTestService labTestService;

    @Autowired
    public ResearchProjectController(ResearchProjectService researchProjectService, LabTestService labTestService) {
        this.researchProjectService = researchProjectService;
        this.labTestService = labTestService;
    }

    @GetMapping("/list")
    public String listProjects(Model theModel){

        List<ResearchProject> projectList = researchProjectService.findAll();

        theModel.addAttribute("projectList", projectList);

        return "researchProject/list-projects";

    }

    @GetMapping("/showFormForAdd")
    public String showProjectFormForAdd(Model theModel) {

        ResearchProject researchProject = new ResearchProject();

        theModel.addAttribute("researchProject", researchProject);

        return "/researchProject/add-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showProjectFormForUpdate(@RequestParam("projectId")int id, Model theModel){

        ResearchProject researchProject = researchProjectService.findById(id);

        theModel.addAttribute("researchProject", researchProject);

        return "/researchProject/add-form";
    }


    @PostMapping("/saveResearchProject")
    public String saveResearchProject(@Valid @ModelAttribute("researchProject") ResearchProject researchProject,
                            BindingResult theBindingResult,
                            Model theModel){

        if (theBindingResult.hasErrors()){

            return "/researchProject/add-form";
        }

        ResearchProject existing = researchProjectService.findByName(researchProject.getName());
        if (existing != null ){
            theModel.addAttribute("editError", researchProject.getName() + " already exists.");

            return "/researchProject/add-form";
        }

        researchProjectService.save(researchProject);

        return "redirect:/employee/researchProjects/list";
    }

    @GetMapping("/delete")
    public String deleteResearchProject(@RequestParam("projectId") int id) {

        researchProjectService.deleteById(id);

        return "redirect:/employee/researchProjects/list";
    }

    //-----------------------------------------------------------------------------

    @GetMapping("/showProjectTests")
    public String showProjectTests(@RequestParam("projectId")int id, Model theModel){

        ResearchProject researchProject = researchProjectService.findById(id);

        theModel.addAttribute("researchProject", researchProject);

        List<TestInProject> testInProjectList = researchProjectService.findAllTestsByResearchProjectId(id);

        theModel.addAttribute("testInProjectList", testInProjectList);

        return "/researchProject/tests-list";
    }

    @GetMapping("/showTestToProjectFormForAdd")
    public String showTestToProjectFormForAdd(@RequestParam("projectId")int id, Model theModel) {

        TestInProject testInProject = new TestInProject();

        theModel.addAttribute("testInProject", testInProject);

        ResearchProject researchProject = researchProjectService.findById(id);

        theModel.addAttribute("researchProject", researchProject);

        List<LabTest> labTestList = labTestService.findAll();

        theModel.addAttribute("labTestList", labTestList);


        return "/researchProject/add-labTestToProject-form";
    }

//    @GetMapping("/showTestToProjectFormForUpdate")
//    public String showTestToProjectFormForUpdate(@RequestParam("projectId")int id, @RequestParam("labTestId")int labTestId, Model theModel) {
//
//        TestInProject testInProject = researchProjectService.findTestInProjectByResearchProjectIdAndLabTestId(id, labTestId);
//
//        theModel.addAttribute("testInProject", testInProject);
//
//        ResearchProject researchProject = researchProjectService.findById(id);
//
//        theModel.addAttribute("researchProject", researchProject);
//
//        List<LabTest> labTestList = labTestService.findAll();
//
//        theModel.addAttribute("labTestList", labTestList);
//
//
//        return "/researchProject/add-labTestToProject-form";
//    }



    @PostMapping("/saveTestToProject")
    public String saveTestToProject(@RequestParam("projectId")int id, @Valid @ModelAttribute("testInProject") TestInProject testInProject,
                                    BindingResult bindingResult, Model theModel){

        if (bindingResult.hasErrors()){

            return "/researchProject/add-labTestToProject-form";
        }

        TestInProject existing = researchProjectService.findTestInProjectByResearchProjectIdAndLabTestId(id, testInProject.getLabTest().getId());
        if (existing != null ){
            theModel.addAttribute("editError", testInProject.getLabTest().getName() + " test already added.");

            theModel.addAttribute("testInProject", testInProject);

            ResearchProject researchProject = researchProjectService.findById(id);

            theModel.addAttribute("researchProject", researchProject);

            List<LabTest> labTestList = labTestService.findAll();

            theModel.addAttribute("labTestList", labTestList);

            return "/researchProject/add-labTestToProject-form";
        }

        ResearchProject researchProject = researchProjectService.findById(id);

        testInProject.setResearchProject(researchProject);

        researchProjectService.saveTestToProject(testInProject);

        return "redirect:/employee/researchProjects/showProjectTests?projectId="+id;
    }

    @GetMapping("/deleteTestToProject")
    public String deleteTestToProject(@RequestParam("projectId") int id, @RequestParam("labTestId")int labTestId) {

        TestInProject testInProject = researchProjectService.findTestInProjectByResearchProjectIdAndLabTestId(id, labTestId);

        researchProjectService.deleteTestInProject(testInProject);

        return "redirect:/employee/researchProjects/showProjectTests?projectId="+id;

    }
}
