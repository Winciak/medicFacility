package com.winciak.medicFacility.controllers;

import com.winciak.medicFacility.dto.DtoUser;
import com.winciak.medicFacility.entities.*;
import com.winciak.medicFacility.services.ResearchProjectService;
import com.winciak.medicFacility.services.UserService;
import jakarta.validation.Valid;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Controller
@RequestMapping("/employee")
public class EmployeePanelController {

    private final UserService userService;
    private final ResearchProjectService researchProjectService;


    @Autowired
    public EmployeePanelController(UserService userService,
                                   ResearchProjectService researchProjectService) {
        this.userService = userService;
        this.researchProjectService = researchProjectService;
    }

    @GetMapping("/panel")
    public String employeePanel(){


        return "employee/employee-panel";

    }

    @GetMapping("/patients/list")
    public String listPatients(Model theModel){

        List<PatientUser> patientList = userService.findAll();

        theModel.addAttribute("patients", patientList);

        return "employee/list-patients";

    }

    @PostMapping("/patients/save")
    public String savePatient(@ModelAttribute("patient") PatientUser user){

        userService.save(user);

        return "redirect:/employee/patients/list";
    }

    @GetMapping("/patients/showFormForUpdate")
    public String showPatientFormForUpdate(@RequestParam("patientId")int theId, Model theModel){

        PatientUser patient = userService.findById(theId);

        List<Role> roleList = (List<Role>) patient.getRoles();

        DtoUser dtoUser = new DtoUser();
        dtoUser.setLogin(patient.getLogin());
        dtoUser.setFirstName(patient.getFirstName());
        dtoUser.setLastName(patient.getLastName());
        dtoUser.setEmail(patient.getEmail());
        dtoUser.setPhoneNumber(patient.getPhoneNumber());
        dtoUser.setWhatId(patient.getId());

        String password = "thisIsOnlyForPassingValidation";

        dtoUser.setPassword(password);
        dtoUser.setMatchingPassword(password);

        int before = 0;
        for(Role role : roleList){
            if(role.getId()>before){
                dtoUser.setRole(role.getName());
                before=role.getId();
            }
        }

        theModel.addAttribute("dtoUser", dtoUser);



        return "employee/update-patient";
    }

    @PostMapping("/patients/processUpdatePatient")
    public String processUpdatePatient(@Valid @ModelAttribute("dtoUser") DtoUser dtoUser,
                                       BindingResult theBindingResult) {


        if (theBindingResult.hasErrors()){
            return "employee/update-patient";
        }


        userService.update(dtoUser);


        return "redirect:/employee/patients/list";
    }

    @GetMapping("/patients/delete")
    public String deletePatient(@RequestParam("patientId") int theId){

        userService.deleteById(theId);

        return "redirect:/employee/patients/list";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/patients/showFormForAdd")
    public String showPatientFormForAdd(Model theModel) {

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();

        String password = generator.generate(10,20);

        DtoUser dtoUser = new DtoUser();
        dtoUser.setPassword(password);
        dtoUser.setMatchingPassword(password);


        theModel.addAttribute("dtoUser", dtoUser);

        return "employee/add-patient";
    }

    @PostMapping("/patients/processAddingPatient")
    public String processAddingPatient(@Valid @ModelAttribute("dtoUser") DtoUser dtoUser,
            BindingResult theBindingResult,
            Model theModel) {

        String login = dtoUser.getLogin();


        if (theBindingResult.hasErrors()){
            return "employee/add-patient";
        }

        PatientUser existing = userService.findByLogin(login);
        if (existing != null){
            theModel.addAttribute("registrationError", "Login already exists.");

            return "employee/add-patient";
        }


        userService.save(dtoUser);

        theModel.addAttribute("password", dtoUser.getPassword());

        return "employee/adding-confirmation";
    }


    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("/patients/showDetails")
    public String showDetails(@RequestParam("patientId")int theId, Model theModel){

        String orderError = (String) theModel.getAttribute("orderError");

        if(orderError!=null){
            theModel.addAttribute("orderError", orderError);
        }

        PatientUser patient = userService.findById(theId);

        List<PatientsProjects> projects = patient.getPatientsProjects();

        List<PatientTest> tests = patient.getPatientTestList();

        theModel.addAttribute("patient", patient);
        theModel.addAttribute("projects", projects);
        theModel.addAttribute("tests", tests);


        return "employee/details-patient";
    }



    @GetMapping("/patients/showConnectPatientsProjects")
    public String showConnectPatientsProjects(@RequestParam("patientId")int id, Model theModel) {

        PatientsProjects patientsProjects = new PatientsProjects();

        patientsProjects.setConsentFile(new ConsentFile());

        theModel.addAttribute("patientsProjects", patientsProjects);

        PatientUser patientUser = userService.findById(id);

        theModel.addAttribute("patientUser", patientUser);

        List<ResearchProject> researchProjectList = researchProjectService.findAll();

        theModel.addAttribute("researchProjectList", researchProjectList);


        return "/employee/add-projectToPatient-form";
    }

    @GetMapping("/patients/showConnectPatientsProjectsFormForUpdate")
    public String showConnectPatientsProjectsFormForUpdate(@RequestParam("patientId")int id,@RequestParam("projectId")int projectId, Model theModel) {

        PatientsProjects patientsProjects = userService.findPatientsProjectsByPatientUserIdAndResearchProjectId(id, projectId);

        theModel.addAttribute("patientsProjects", patientsProjects);

        PatientUser patientUser = userService.findById(id);

        theModel.addAttribute("patientUser", patientUser);

        List<ResearchProject> researchProjectList = researchProjectService.findAll();

        theModel.addAttribute("researchProjectList", researchProjectList);


        return "/employee/add-projectToPatient-form";
    }



    @PostMapping("/patients/saveProjectToPatient")
    public String saveProjectToPatient(@RequestParam("patientId")int id, @RequestParam(value = "multiFile") MultipartFile multiFile, Model model,
                                       @Valid @ModelAttribute("patientsProjects") PatientsProjects patientsProjects, BindingResult bindingResult) {

        try {
            PatientUser patientUser = userService.findById(id);

            patientsProjects.setPatientUser(patientUser);

            PatientsProjects patientsProjectsCheck = new PatientsProjects();
            if (patientsProjects.getId() != 0) {
                patientsProjectsCheck = userService.findPatientsProjectsById(patientsProjects.getId());
            }

            PatientsProjects existing = userService.findPatientsProjectsByPatientUserIdAndResearchProjectId(id, patientsProjects.getResearchProject().getId());

            if (existing != null) {
                if (patientsProjectsCheck.getResearchProject() == null) {

                    model.addAttribute("editError", patientsProjects.getResearchProject().getName() + " already added.");

                    model.addAttribute("patientsProjects", patientsProjects);

                    model.addAttribute("patientUser", patientUser);

                    List<ResearchProject> researchProjectList = researchProjectService.findAll();

                    model.addAttribute("researchProjectList", researchProjectList);

                    return "/employee/add-projectToPatient-form";

                }
            }

            final long MAX_FILE_SIZE = 16 * 1024 * 1024; // 16MB
            final List<String> ACCEPTED_CONTENT_TYPES = Arrays.asList("image/x-png", "image/png", "image/jpeg", "image/jpg", "application/pdf");

            String contentType = multiFile.getContentType();

            // Validate the MultipartFile
            if (patientsProjects.getConsent()==1) {
                if (multiFile.isEmpty()) {
                    bindingResult.rejectValue("consentFile", "error.file", "Please select a file.");
                } else if (multiFile.getSize() > MAX_FILE_SIZE) {
                    bindingResult.rejectValue("consentFile", "error.file", "File size exceeds the limit.");
                } else if (!ACCEPTED_CONTENT_TYPES.contains(contentType)) {
                    bindingResult.rejectValue("consentFile", "error.file", "File type not supported.");
                }
            }

            if (bindingResult.hasErrors()) {
                // If there are errors, return the form with the error messages
                model.addAttribute("errors", bindingResult.getAllErrors());
                model.addAttribute("patientsProjects", patientsProjects);

                model.addAttribute("patientUser", patientUser);

                List<ResearchProject> researchProjectList = researchProjectService.findAll();

                model.addAttribute("researchProjectList", researchProjectList);

                return "/employee/add-projectToPatient-form";
            } else {
                if (patientsProjects.getConsent()==1) {
                    byte[] fileBytes = null;
                    try {
                        fileBytes = multiFile.getBytes();
                    } catch (IOException e) {
                        // Handle the exception appropriately
                    }

                    // Create a new ConsentFile entity and set the fileBytes and other fields
                    ConsentFile consentFile = new ConsentFile();
                    consentFile.setFile(fileBytes);
                    consentFile.setFileName(multiFile.getOriginalFilename());
                    consentFile.setFileType(multiFile.getContentType());

                    patientsProjects.setConsentFile(consentFile);
                }

                userService.savePatientsProjects(patientsProjects);

                return "redirect:/employee/patients/showDetails?patientId=" + id;
            }
        } catch (MaxUploadSizeExceededException ex) {
            return handleFileUploadException(ex, model);
        }


    }

    @GetMapping("/patients/deleteProjectToPatient")
    public String deleteProjectToPatient(@RequestParam("patientsProjectsId") int theId){

        int id = userService.findPatientsProjectsById(theId).getPatientUser().getId();

        userService.deletePatientsProjectsById(theId);

        return "redirect:/employee/patients/showDetails?patientId=" + id;
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class, SizeLimitExceededException.class})
    public String handleFileUploadException(Exception ex, Model model) {
        model.addAttribute("message", "File too large!");
        return "/employee/add-projectToPatient-form";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable int id) throws Exception
    {
        try
        {
            ConsentFile consentFile = userService.findFileById(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(consentFile.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + consentFile.getFileName() + "\"")
                    .body(new ByteArrayResource(consentFile.getFile()));
        }
        catch(Exception e)
        {
            throw new Exception("Error downloading file");
        }
    }

    @GetMapping("/patients/deleteProjectFromPatient")
    public String deleteProjectFromPatient(@RequestParam("patientId") int id, @RequestParam("projectId")int projectId) {

        PatientsProjects patientsProjects = userService.findPatientsProjectsByPatientUserIdAndResearchProjectId(id, projectId);

        userService.deletePatientsProjectsById(patientsProjects.getId());

        return "redirect:/employee/patients/showDetails?patientId="+id;
    }

}
