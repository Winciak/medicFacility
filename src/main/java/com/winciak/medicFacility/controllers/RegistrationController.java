package com.winciak.medicFacility.controllers;



import com.winciak.medicFacility.dto.DtoUser;
import com.winciak.medicFacility.entities.PatientUser;
import com.winciak.medicFacility.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    private final UserService userService;

	public RegistrationController(UserService userService) {
		this.userService = userService;
	}


	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationPage")
	public String showMyRegisterPage(Model theModel) {
		
		theModel.addAttribute("dtoUser", new DtoUser());
		
		return "registration-page";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("dtoUser") DtoUser dtoUser,
				BindingResult theBindingResult, 
				Model theModel) {
		
		String login = dtoUser.getLogin();

		
		 if (theBindingResult.hasErrors()){
			 return "registration-page";
	        }

        PatientUser existing = userService.findByLogin(login);
        if (existing != null){
			theModel.addAttribute("registrationError", "Login already exists.");

        	return "registration-page";
        }
        
        userService.save(dtoUser);
        

        return "registration-confirmation";		
	}
}
